$(document).ready(function() {
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