var timeSlot; // use an array [48] to memorize occupation status of time for one day
var originalEvent; // temporarily hold information of before-event when starting dragging & resizing

// dynamically add some properties into events (make own event editable)
function initEventsData(eventsData, cb) {
    $.each(eventsData, function(index, event) {
        if (typeof(loginUserId)!="undefined" && typeof(event.user)!="undefined" && event.user.id == loginUserId) {
            eventsData[index].editable = true;
        } else {
            eventsData[index].editable = false;
        }
        eventsData[index] = renameKey(eventsData[index]);
    });
    cb(eventsData);
}

function renameKey(object){
	object.start = object.startTime;
	object.end = object.endTime;
	delete object.startTime;
	delete object.endTime;
	return object;
}

// initialize a array to record idle status for each time slot
function initTimeSlot(eventsData) {
    timeSlot = Array.apply(null, new Array(48)).map(Number.prototype.valueOf, 0);

    $.each(eventsData, function(index, event) {
    	if(typeof(loginUserId)!="undefined" && event.user.id == loginUserId){
    		convertEventToSlot(event.start, event.end, "+", maxEventsInInterval); // own events: step max steps
    	}else{
    		convertEventToSlot(event.start, event.end, "+", 1);
    	}
    });
}

// give start, end time, operator (+ or -) and step (if event.user = myself then step maxEventsInInterval steps), adjust the timeSlot array
function convertEventToSlot(start, stop, operator, step) {
    var startTime = new Date(start);
    var simplifyStartTime = startTime.getHours() + startTime.getMinutes() / 60;
    var endTime = new Date(stop);
    var simplifyEndTime = endTime.getHours() + endTime.getMinutes() / 60;

    switch (operator) {
        case "+":
            var count = 0;
            for (var i = 0; i < (simplifyEndTime - simplifyStartTime) / 0.5; i++) {
                timeSlot[(simplifyStartTime) * 2 + count] += step;
                count++;
            }
            break;
        case "-":
            var count = 0;
            for (var i = 0; i < (simplifyEndTime - simplifyStartTime) / 0.5; i++) {
                timeSlot[(simplifyStartTime) * 2 + count] -= step;
                count++;
            }
            break;
    }
}

// given startTime and stopTime, check if this event can be inserted into timeSlot or not
function checkIdle(start, stop, maxEventsInInterval, callFrom) {
    var startTime = new Date(start.format());
    var simplifyStartTime = startTime.getHours() + startTime.getMinutes() / 60;
    var endTime = new Date(stop.format());
    var simplifyEndTime = endTime.getHours() + endTime.getMinutes() / 60;
    
    var oDiff = 0;
    if(callFrom == "update"){
    	var oStartTime = new Date(originalEvent.start.format());
        var oSimplifyStartTime = oStartTime.getHours() + oStartTime.getMinutes() / 60;
        var oEndTime = new Date(originalEvent.end.format());
        var oSimplifyEndTime = oEndTime.getHours() + oEndTime.getMinutes() / 60;
    	oDiff = oSimplifyEndTime - oSimplifyStartTime;
    }
    
    if(simplifyEndTime - simplifyStartTime - oDiff > parseFloat($("#leaveAmount").text())){ // exceed quota
    	return false;
    }else{
    	var count = 0;
        for (var i = 0; i < (simplifyEndTime - simplifyStartTime) / 0.5; i++) {
            if (timeSlot[(simplifyStartTime) * 2 + count] >= maxEventsInInterval) {
                return false;
            }
            count++;
        }
        return true;
    }
}

function updateEvent(event, delta, revertFunc) {
    var view = $('#calendar').fullCalendar('getView').name;
    var today = new Date();
    if (event.start < today || view == "month" || view == "agendaWeek") {
        revertFunc();
    } else {
        convertEventToSlot(originalEvent.start, originalEvent.end, "-", maxEventsInInterval); // first, remove event from timeSlot before updating
        var isIdle = checkIdle(event.start, event.end, maxEventsInInterval, "update");	// second, check if the event can be inserted into timeSlot or not
        if (isIdle) { // third, update event
            swal({
                title: '確認操作',
                text: "確認執行此步驟",
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '確認',
                cancelButtonText: '取消'
            }).then(function(result) {
                if (result.value) {
                    $.ajax({
                    	type: 'POST',
                    	url: "../rest/event/updateEvent",
                    	data: {
                    		id: event.id,
                    		title: event.title,
                    		start: "" + event.start,
                    		end: "" + event.end
                    	}
                    }).done(function(resultData) {
                    	resultData = renameKey(resultData);
                    	convertEventToSlot(resultData.start, resultData.end, "+", maxEventsInInterval);
                    	swal('操作成功', '你已修改成功', 'success');
                    	
                    	// update leaveAmount text
                    	var oDiff = (originalEvent.end - originalEvent.start)/1000/60/60;
                    	var diff = (resultData.end - resultData.start)/1000/60/60;
                    	$("#leaveAmount").text(parseFloat($("#leaveAmount").text()) + oDiff - diff);
                    }).fail(function() {
                    	swal('操作失敗', '伺服器無回應', 'error');
                    });
                } else {
                    convertEventToSlot(originalEvent.start, originalEvent.end, "+", maxEventsInInterval);
                    revertFunc();
                }
            });
        } else {
        	convertEventToSlot(originalEvent.start, originalEvent.end, "+", maxEventsInInterval);
            swal('操作失敗', '已超過可申請人/時數！', 'error');
            revertFunc();
        }
    }
}

$(document).ready(function() {
    $.get("../rest/event/getAllEvents", function(eventsDataObj) {
        initEventsData(eventsDataObj, function(eventsData) {
    	eventsData = eventsDataObj;
            $('#calendar').fullCalendar({
                events: eventsData,
                themeSystem: 'bootstrap3',
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'
                },
                buttonText: {
                    today: '今日',
                    month: '月',
                    week: '週',
                    day: '日',
                    list: 'list'
                },
                views: {
                    month: {
                        titleFormat: 'YYYY 年 M 月'
                    },
                    week: {
                        titleFormat: 'YYYY 年 M 月 D 日',
                        titleRangeSeparator: ' ~ ',
                    },
                    day: {
                        titleFormat: 'YYYY 年 M 月 D 日'
                    },
                },
                timezone: 'local',
                scrollTime: "07:00:00",
                allDaySlot: false,
                slotEventOverlap: false,
                navLinks: true, // allow click
                eventLimit: true, // allow "more" link
                
                businessHours: [{
                    dow: [ 1, 2, 3, 4, 5 ],
                    start: '08:30',
                    end: '12:00',
                },{
                	dow: [ 1, 2, 3, 4, 5 ],
                    start: '13:00',
                    end: '17:30',
                }],
                
                selectable: true,
                selectHelper: true,
                select: function(start, end) {
                    var view = $('#calendar').fullCalendar('getView').name;
                    var today = new Date();
                    if (typeof(loginUserId)=="undefined" || start < today || view == "month" || view == "agendaWeek") {} else {
                        var isIdle = checkIdle(start, end, maxEventsInInterval, "insert");
                        if (isIdle) {
                            swal({
                                title: '請輸入事件名稱',
                                input: 'text',
                                inputPlaceholder: '事件名稱',
                                confirmButtonText: '確定',
                                cancelButtonText: '取消',
                                showCancelButton: true,
                                inputValidator: function(value) {
                                    return !value && '不能為空！';
                                }
                            }).then(function(data) {
                            	var title = data.value;
                            	if (title) {
                            		$.ajax({
                            			type: 'POST',
                            			url: "../rest/event/addEvent",
                            			data: {
                            				title: title,
                            				start: "" + start,
                            				end: "" + end
                            			}
                            		}).done(function(resultData) {
                            			resultData.editable = true;
                            			resultData = renameKey(resultData);
                            			$('#calendar').fullCalendar('renderEvent', resultData, true); // stick?
                            			convertEventToSlot(start, end, "+", maxEventsInInterval);
                            			
                            			// update leaveAmount span
                            			var diff = (end - start)/1000/60/60;
                            			$("#leaveAmount").text(parseFloat($("#leaveAmount").text()) - diff);
                            		}).fail(function() {
                            			swal('操作失敗', '伺服器無回應', 'error');
                            		});
                            	}
                            });
                        } else {
                            swal('操作失敗', '已超過可申請人/時數！', 'error');
                        }
                    }
                    $('#calendar').fullCalendar('unselect');
                },

                eventDragStart: function(event, jsEvent, ui, view) {
                    originalEvent = event;
                },
                eventDrop: function(event, delta, revertFunc) {
                    updateEvent(event, delta, revertFunc);
                },
                eventResizeStart: function(event, jsEvent, ui, view) {
                    originalEvent = event;
                },
                eventResize: function(event, delta, revertFunc) {
                    updateEvent(event, delta, revertFunc);
                },

                // click date on month page to redirect to date page
                dayClick: function(date, jsEvent, view) {
                    $('#calendar').fullCalendar('gotoDate', date);
                    $('#calendar').fullCalendar('changeView', 'agendaDay');
                },
                eventClick: function(event) {
                    if (event.editable) {
                    	var today = new Date();
                        if (event.start < today) {} else {
	                        swal({
	                            title: "事件名稱：" + event.title,
	                            confirmButtonText: '刪除此事件  <i class="fa fa-arrow-right></i>',
	                            html: "開始時間：" + moment(event.start).format('YYYY年M月D日 h:mm') + "<br>結束時間：" + moment(event.end).format('YYYY年M月D日 h:mm'),
	                            type: 'info'
	                        }).then(function(result) {
	                            if (result.value) {
	                                $.ajax({
	                                	type: 'POST',
	                                	url: "../rest/event/deleteEvent",
	                                	data: {
	                                		id: event.id
	                                	}
	                                }).done(function(resultData) {
	                                	resultData = renameKey(resultData);
	                                	$('#calendar').fullCalendar('removeEvents', resultData, true); // just event id
	                                	if($('#calendar').fullCalendar('getView').name == "agendaDay"){
	                                		convertEventToSlot(event.start, event.end, "-", maxEventsInInterval);
	                                	}
	                                	swal('已刪除', '您的操作已成功', 'success');
	                                	
	                                	// update leaveAmount span
	                                	var diff = (event.end - event.start)/1000/60/60;
	                                	$("#leaveAmount").text(parseFloat($("#leaveAmount").text()) + diff);
	                                }).fail(function() {
	                                	swal('操作失敗', '伺服器無回應', 'error');
	                                });
	                            }
	                        });
                        }
                    }
                },

                viewRender: function(view, element) {
                    if (view.name == "agendaDay") {
                        // get all events by date
                    	var date = $('#calendar').fullCalendar('getDate').toDate();
                        var startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 0, 0, 0);
                        var endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 23, 59, 59);
                        var dayEvents = $('#calendar').fullCalendar('clientEvents', function(event) {
                            return (
                                event.start.toDate() >= startDate && event.start.toDate() <= endDate ||
                                event.end.toDate() >= startDate && event.end.toDate() <= endDate
                            );
                        });
                        initTimeSlot(dayEvents); // initialize the time slot
                    }
                },
                eventRender: function(event, element, view) { // trigger cases: (1) show page; (2) insert (select); (3) update (drag or resize)
                	if(event._id == null){ // case 2
                	}else{ // case 1 and 3
                		if(typeof(loginUserId)!="undefined" && typeof(event.user)!="undefined" && event.user.id == loginUserId ){ // login
                			element.addClass('myevent');
                		}
                		element.find('.fc-title').append("<br/>" + event.user.name);
                		element.qtip({
                            content: event.user.name,
                            style: {
                                classes: 'qtip-dark qtip-tipsy'
                            }
                        });
                	}
                },
            });
        });
    });
});;$(document).ready(function() {
	$('#login-nav').submit(false);
    $("#login-nav").submit(function(event) {
        $.ajax({
        	type: 'POST',
        	url: "../rest/authority/login",
        	data: {
        		account: $("#account").val(),
        		pswd: $("#pswd").val()
        	},
        	success: function(resultData){
        		window.location.reload();
        	}
        }).fail(function() {
        	swal('操作失敗', '帳號或密碼錯誤！', 'error');
        });
    });
    $("#logout").on("click", function() {
        $.ajax({
        	type: 'GET',
        	url: "../rest/authority/logout"
        }).done(function(resultData) {
        	window.location.reload();
        }).fail(function() {
        	swal('操作失敗', '登出異常！', 'error');
        });
    });
});