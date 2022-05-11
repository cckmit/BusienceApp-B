package com.busience.common.security;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener{
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

		httpSessionEvent.getSession().setMaxInactiveInterval(60*60); //1시간
	}

	@Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
