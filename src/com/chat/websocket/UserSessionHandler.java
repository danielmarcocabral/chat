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
import javax.websocket.Session;
import com.chat.util.DataUtil;

public class UserSessionHandler {
	private static final Set<Session> usersSessions = Collections.synchronizedSet(new HashSet<Session>());
	
    public static void addSession(Session session) {
    	usersSessions.add(session);
    }
    public static void removeSession(Session session) {
    	usersSessions.remove(session);
    }
    public static void propagateMessage(String userName, String message) throws IOException{
    	Iterator<Session> iterator = usersSessions.iterator();
		while(iterator.hasNext()){
			iterator.next().getBasicRemote().sendText(DataUtil.buildJsonData(userName,message));
		}
    }

}
