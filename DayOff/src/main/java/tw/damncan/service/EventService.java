package tw.damncan.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import tw.damncan.model.Event;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

public class EventService {
	public Event addEvent(String title, String start, String end, int user_ID) throws ParseException, ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>(4);
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("start", start));
		params.add(new BasicNameValuePair("end", end));
		params.add(new BasicNameValuePair("user_ID", Integer.toString(user_ID)));
		
		ApiHitService apiHitService = new ApiHitService();
		Event event = (Event) apiHitService.Post("http://localhost:8080/DayOffAP/rest/event/addEvent", params, Event.class);
    	return event;
	}

	public Event deleteEvent(int id, int user_ID) throws ClientProtocolException, IOException{
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("id", Integer.toString(id)));
		params.add(new BasicNameValuePair("user_ID", Integer.toString(user_ID)));
		
		ApiHitService apiHitService = new ApiHitService();
		Event event = (Event) apiHitService.Post("http://localhost:8080/DayOffAP/rest/event/deleteEvent", params, Event.class);
		return event;
	}
	
	public Event updateEvent(int id, String title, String start, String end, int user_ID) throws ParseException, ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>(5);
		params.add(new BasicNameValuePair("id", Integer.toString(id)));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("start", start));
		params.add(new BasicNameValuePair("end", end));
		params.add(new BasicNameValuePair("user_ID", Integer.toString(user_ID)));
		
		ApiHitService apiHitService = new ApiHitService();
		Event event = (Event) apiHitService.Post("http://localhost:8080/DayOffAP/rest/event/updateEvent", params, Event.class);
    	return event;
	}
	
	public Event getEvent(int id) throws ClientProtocolException {
		ApiHitService apiHitService = new ApiHitService();
		Event event = (Event) apiHitService.Get("http://localhost:8080/DayOffAP/rest/event/getEvent/"+id, Event.class);
    	return event;
	}
	
	public List<Event> getAllEvents(int depID) throws ClientProtocolException {
		ApiHitService apiHitService = new ApiHitService();
		List<Event> events = (List<Event>) apiHitService.Get("http://localhost:8080/DayOffAP/rest/event/getAllEvents/"+depID, List.class);
    	return events;
	}
}