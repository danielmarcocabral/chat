package com.chat;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.server.ServerEndpoint;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


@ServerEndpoint("/chatServerEndpoint")
public class ChatServerEndpoint {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void open(Session userSession){
		users.add(userSession);
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException{
		String username = (String) userSession.getUserProperties().get("username");
		if(username==null){
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System","you've joined as "+message));
		}else{
			Iterator<Session> iterator = users.iterator();
			while(iterator.hasNext()){
				iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
			}
		}
	}
	
	@OnClose
	public void close(Session userSession){
		users.remove(userSession);
	}
	
	private String buildJsonData(String username, String message){
		JsonObject jsonObject  = Json.createObjectBuilder().add("message",username+" : "+message).build();
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}
}
