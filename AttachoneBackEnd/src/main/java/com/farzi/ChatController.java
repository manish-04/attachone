package com.farzi;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatController {

	@MessageMapping("/chat")
	@SendTo("/topic/msgs")
	public String processQuestion( String data )
	{
		System.out.println(data);
		
		JSONParser jpar = new JSONParser();
		
		try {
		
			JSONObject jobj = (JSONObject )jpar.parse(data);
			
			jobj.put("date", new Date().toString());
			
			data = jobj.toJSONString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
