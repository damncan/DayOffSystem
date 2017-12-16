package tw.damncan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tw.damncan.model.LoginUser;
import tw.damncan.service.AuthorityService;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginUser> login(HttpServletRequest request) throws ServletException, IOException {
		AuthorityService authorityService = new AuthorityService();
		LoginUser loginUser = authorityService.login(request.getParameter("account"), request.getParameter("pswd"));
		
		if(loginUser != null){
			request.getSession().setAttribute("loginUser", loginUser);
			return new ResponseEntity<LoginUser>(loginUser, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
    }
    
    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
    	LoginUser loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
    	
    	if(loginUser != null){
    		request.getSession().invalidate();
    		return new ResponseEntity<String>("OK", HttpStatus.OK);
    	}else{
    		return new ResponseEntity<String>("YOU SUCK", HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping("/getUserId")
    public ResponseEntity getUserId(HttpServletRequest request, HttpServletResponse response){
    	LoginUser loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
    	
    	if(loginUser != null){
    		return new ResponseEntity(loginUser.getId(), HttpStatus.OK);
    	}else{
    		return new ResponseEntity("Please login", HttpStatus.BAD_REQUEST);
    	}
    }
}