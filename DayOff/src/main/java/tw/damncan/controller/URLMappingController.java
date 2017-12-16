package tw.damncan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tw.damncan.model.LoginUser;

@Controller
public class URLMappingController {
	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String show(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
		if(loginUser != null){
			Double maxNum = ((double)loginUser.getDepNumber()/10);
			model.put("loginUser", loginUser);
			model.addAttribute("maxEventsInInterval", (int) Math.ceil(maxNum));
		}
		return "home";
	}
}