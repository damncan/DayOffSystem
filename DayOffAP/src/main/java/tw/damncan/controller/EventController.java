package tw.damncan.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.damncan.model.Event;
import tw.damncan.service.EventService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/event")
public class EventController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public ResponseEntity<Event> addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException, ParseException {
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date startTime = sdf.parse(request.getParameter("start"));
//		Date endTime = sdf.parse(request.getParameter("end"));
		
		Date startTime = new Date(Long.parseLong(request.getParameter("start")));
		Date endTime = new Date(Long.parseLong(request.getParameter("end")));
		String title = request.getParameter("title");
		int user_ID = Integer.parseInt(request.getParameter("user_ID"));
		
		EventService eventService = new EventService(context);
		Event event = (Event) eventService.addEvent(startTime, endTime, title, user_ID);

		return new ResponseEntity<Event>(event, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public ResponseEntity<Event> deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException, ParseException {
		int id = Integer.parseInt(request.getParameter("id"));
		int user_ID = Integer.parseInt(request.getParameter("user_ID"));

		EventService eventService = new EventService(context);
		Event event = (Event) eventService.deleteEvent(id, user_ID);
		
		return new ResponseEntity<Event>(event, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public ResponseEntity<Event> updateEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException, ParseException {
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date startTime = sdf.parse(request.getParameter("start"));
//		Date endTime = sdf.parse(request.getParameter("end"));
		
		int id = Integer.parseInt(request.getParameter("id"));
		Date startTime = new Date(Long.parseLong(request.getParameter("start")));
		Date endTime = new Date(Long.parseLong(request.getParameter("end")));
		String title = request.getParameter("title");
		int user_ID = Integer.parseInt(request.getParameter("user_ID")); // useless?

		EventService eventService = new EventService(context);
		Event event = (Event) eventService.updateEvent(id, startTime, endTime, title, user_ID);
		
		return new ResponseEntity<Event>(event, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getAllEvents/{depID}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> getAllEvents(@PathVariable("depID") int depID) throws ServletException, IOException, NoSuchAlgorithmException {
		EventService eventService = new EventService(context);
		List<?> eventList = (List<?>) eventService.getAllEvents(depID);
		
		if(eventList != null){
			return new ResponseEntity<List<?>>(eventList, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
    }
	
	@RequestMapping(value = "/getEventsBetween/{start}/{end}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> getEventsBetween(@PathVariable("start") String start, @PathVariable("end") String end) throws ParseException {
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date sTime = sdf.parse("2017-11-23 00:00:00");
//		Date eTime = sdf.parse("2017-11-28 00:00:00");
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sTime = sdf.parse(start);
		Date eTime = sdf.parse(end);
		
		EventService eventService = new EventService(context);
		List<?> eventList = (List<?>) eventService.getEventsBetween(sTime, eTime, null);
		
		if(eventList != null){
			return new ResponseEntity<List<?>>(eventList, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
    }
	
	@RequestMapping(value = "/getEvent/{id}", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
		EventService eventService = new EventService(context);
		Event event = (Event) eventService.getEvent(id);
		
		if (event == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}
}