<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<div class="slide-container">
  
  <div class="wrapper">
    <div class="clash-card barbarian">
      
      <div class="clash-card__image clash-card__image--barbarian">
        
      </div>
      
      <div id="avatar"></div>
      
      <div class="centerDiv">
        <div class="clash-card__level clash-card__level--barbarian">
        ${loginUser.depName}
        </div>
        <div class="clash-card__unit-name">
    	<% if(request.getSession().getAttribute("loginUser") != null){ %>
		${loginUser.name}
		<% } %>
        </div>
        <div class="clash-card__unit-description">
        ${loginUser.email}
        </div>
      </div>

      <div class="clash-card__unit-stats clash-card__unit-stats--barbarian clearfix">
        <div class="one-third">
          <div class="stat"><span id="leaveAmount">${loginUser.leaveAmount}</span><sup>時</sup></div>
          <div class="stat-value">可請時數</div>
        </div>

        <div class="one-third">
          <div class="stat">${loginUser.depNumber}<sup>人</sup></div>
          <div class="stat-value">科別人數</div>
        </div>

        <div class="one-third no-border">
          <div class="stat">${maxEventsInInterval}<sup>人</sup></div>
          <div class="stat-value">上限人數</div>
        </div>
      </div>
      
    </div> <!-- end clash-card barbarian-->
  </div> <!-- end wrapper -->
</div> <!-- end container -->