<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

    <html>
    <head>
        <script src='../resources/lib/jquery/jquery.min.js'></script>

        <link rel='stylesheet' href='../resources/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css' />
        <link rel='stylesheet' href='../resources/lib/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css' />
        <script src="../resources/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
        <link href="../resources/lib/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
        
        <script src="../resources/lib/sweetalert/sweetalert2.all.min.js"></script>
		<!-- Include a polyfill for ES6 Promises (optional) for IE11 and Android browser -->
		<script src="../resources/lib/sweetalert/core.js"></script>
         
        <link rel='stylesheet' href='../resources/lib/fullcalendar/css/fullcalendar.css' />
        <link rel='stylesheet' media='print' href='../resources/lib/fullcalendar/css/fullcalendar.print.css' />
        <script src="../resources/lib/fullcalendar/js/moment.min.js"></script>
        <script src="../resources/lib/fullcalendar/js/fullcalendar.js"></script>
        
        <link rel='stylesheet' href='../resources/lib/qtip/jquery.qtip.min.css' />
        <script src="../resources/lib/qtip/jquery.qtip.min.js"></script>
        
        <script>
			<% if(request.getSession().getAttribute("loginUser") != null){ %>
			var loginUserId = ${loginUser.id};
			var maxEventsInInterval = ${maxEventsInInterval};
			<%}%>
    	</script>
        <!--<script src="../resources/release/js/dayoff.min.js"></script>
        <link rel='stylesheet' href='../resources/release/css/dayoff.css' />-->
        
        <script src="../resources/js/navbar.js"></script>
        <script src="../resources/js/calendar.js"></script>
        <link rel='stylesheet/less' type="text/css"  href='../resources/less/total.less' />
        <script src="../resources/lib/less/less.min.js"></script>
        
        <title>喬喬</title>
    </head>

    <body>
      <jsp:include page="navbar.jsp" />

      <div class="row">
        <div class="col-md-8">
        	<div class="well">
        		<% if(request.getSession().getAttribute("loginUser") != null){ %>
	        	<h1>Hi，${loginUser.name}。來喬個假吧！</h1>
	        	<%}else{%>
	        	<h1>尚未登入</h1>
	        	<%}%>
	            <div id='calendar'></div>
            </div>
        </div>
        <div class="col-md-4">
        	<% if(request.getSession().getAttribute("loginUser") != null){ %>
        	<jsp:include page="profile.jsp" />
        	<%}%>
        </div>
      </div>
    </body>
    </html>