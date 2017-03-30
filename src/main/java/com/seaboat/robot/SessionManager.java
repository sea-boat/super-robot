package com.seaboat.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.seaboat.robot.util.RandomGUID;

public class SessionManager {

	private static SessionManager instance = null;

	private Map sessionMap = Collections.synchronizedMap(new HashMap());

	private Map sessionActiveMap = Collections.synchronizedMap(new HashMap());

	private static int timeout = 20;

	private SessionManager() {
		super();
		Timer sessionClearTimer = new Timer(true);
		int oneMin = 60000;
		sessionClearTimer.schedule(new SessionCleanerTimerTask(), oneMin,
				oneMin);

	}

	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	/**
	 * get context by sessionId
	 * 
	 * @param sessionId
	 * @return
	 */
	public SuperContext getContext(String sessionId) {
		return (SuperContext) sessionMap.get(sessionId);
	}

	/**
	 * get all sessions
	 * 
	 * @return
	 */
	public Set getSessionIdSet() {
		return Collections.unmodifiableSet(sessionMap.entrySet());
	}

	/**
	 * whether session is valid or not
	 * 
	 * @param sessionId
	 * @return
	 */
	public boolean isValid(String sessionId) {
		return sessionMap.containsKey(sessionId);
	}

	/**
	 * remove session by sessionId
	 * 
	 * @param sessionId
	 */
	public void removeSession(String sessionId) {
		sessionMap.remove(sessionId);
	}

	/**
	 * remove all sessions
	 * 
	 */
	public void removeAll() {
		sessionMap.clear();
	}

	/**
	 *  request a  session
	 * 
	 * @param acName
	 * @return
	 */
	public String requestSessionId() {
		String sessionId = new RandomGUID().toString();
		SuperContext ctx = new SuperContext();
		ctx.setSessionId(sessionId);
		sessionMap.put(sessionId, ctx);
		return sessionId;
	}

	public void sessionVisit(String sessionId) {
		if (!sessionMap.containsKey(sessionId)) {
			return;
		}
		sessionActiveMap.put(sessionId, new Integer(0));
	}

	protected class SessionCleanerTimerTask extends TimerTask {

		public void run() {
			Set idSet = sessionActiveMap.keySet();
			Iterator idIt = idSet.iterator();
			List invalidIdList = new ArrayList();
			while (idIt.hasNext()) {
				String id = (String) idIt.next();
				Integer lastSpan = (Integer) sessionActiveMap.get(id);
				if (lastSpan.intValue() > timeout) {
					invalidIdList.add(id);
				}
				sessionActiveMap.put(id, new Integer(lastSpan.intValue() + 1));
			}

			for (int i = 0, n = invalidIdList.size(); i < n; i++) {
				String id = (String) invalidIdList.get(i);
				removeSession(id);
				sessionActiveMap.remove(id);
			}
		}
	}

}
