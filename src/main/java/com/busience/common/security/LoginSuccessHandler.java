package com.busience.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	protected String defermineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		
		Object dest = request.getSession().getAttribute("dest");
		
		String nextURL = null;
		
		if(dest != null) {
			request.getSession().removeAttribute("dest");
			
			nextURL = (String) dest;
		} else {
			nextURL = super.determineTargetUrl(request, response);
		}
		System.out.println("=================="+nextURL+"=================");
		return nextURL;
	}
}
