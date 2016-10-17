package com.chat.websocket;

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

import com.chat.util.DataUtil;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


@ServerEndpoint("/chatServerEndpoint")
public class ChatServerEndpoint {
	
	
	@OnOpen
	public void open(Session userSession){
		UserSessionHandler.addSession(userSession);
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException{
		String userName = (String) userSession.getUserProperties().get("username");
		if(userName==null){
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(DataUtil.buildJsonData("System","you've joined as "+message));
		}else{
			UserSessionHandler.propagateMessage(userName, message);
		}
	}
	
	@OnClose
	public void close(Session userSession){
		UserSessionHandler.removeSession(userSession);
	}
	

}
