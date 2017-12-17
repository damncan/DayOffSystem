package tw.damncan.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginUser> login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException, InstantiationException, IllegalAccessException {
		AuthorityService authorityService = new AuthorityService(context);
		LoginUser loginUser = (LoginUser) authorityService.login(request.getParameter("account"), request.getParameter("pswd"));
		
		if(loginUser != null){
			request.getSession().setAttribute("loginUser", loginUser);
        	return new ResponseEntity<LoginUser>(loginUser, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
    }
}