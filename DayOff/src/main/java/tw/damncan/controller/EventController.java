package tw.damncan.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tw.damncan.model.Event;
import tw.damncan.model.LoginUser;
import tw.damncan.service.EventService;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/event")
public class EventController {
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Event> addEvent(HttpServletRequest request, @RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws ParseException, ClientProtocolException, IOException {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null){
			if(loginUser.getLeaveAmount() > 0){
				EventService eventService = new EventService();
				Event event = eventService.addEvent(title, start, end, loginUser.getId());
				
				event.getUser().setName(loginUser.getName());
				
				// update session
				loginUser.setLeaveAmount(event.getUser().getLeaveAmount());
				request.getSession().setAttribute("loginUser", loginUser);
				
				return new ResponseEntity<Event>(event, HttpStatus.OK);
			}else{
				return new ResponseEntity("NO MORE QUOTA", HttpStatus.BAD_REQUEST);
			}
		}else{
			return new ResponseEntity("YOU SUCK", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> deleteEvent(HttpServletRequest request, @RequestParam("id") int id) throws ParseException, ClientProtocolException, IOException {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null){
			EventService eventService = new EventService();
			Event event = eventService.deleteEvent(id, loginUser.getId());
			
			// update session
			loginUser.setLeaveAmount(event.getUser().getLeaveAmount());
			request.getSession().setAttribute("loginUser", loginUser);
			
			return new ResponseEntity<Object>(event.getId(), HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("YOU SUCK", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity updateEvent(HttpServletRequest request, @RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("start") String start, @RequestParam("end") String end) throws ParseException, ClientProtocolException, IOException {
		LoginUser loginUser = (LoginUser)request.getSession().getAttribute("loginUser");
		
		if(loginUser != null){
			EventService eventService = new EventService();
			Event event = eventService.updateEvent(id, title, start, end, loginUser.getId());
			
			// update session
			loginUser.setLeaveAmount(event.getUser().getLeaveAmount());
			request.getSession().setAttribute("loginUser", loginUser);
			
			return new ResponseEntity<Event>(event, HttpStatus.OK);
		}else{
			return new ResponseEntity("YOU SUCK", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/getEvent/{id}", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id") int id) throws ClientProtocolException, IOException {
		EventService eventService = new EventService();
		Event event = eventService.getEvent(id);
		
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllEvents", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getAllEvents(HttpServletRequest request) throws ClientProtocolException, IOException {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
		int depID = 0;
		if(loginUser != null){
			depID = loginUser.getDepID();
		}
		
		EventService eventService = new EventService();
		List<Event> events = eventService.getAllEvents(depID);
		
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}
}