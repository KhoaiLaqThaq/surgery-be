package com.hangnv.surgery.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.ServletConfigAware;

import io.jsonwebtoken.Jwts;

public class BaseController implements ServletConfigAware {
	
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected ServletContext getServletContext() {
        return servletContext;
    }

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		// TODO Auto-generated method stub
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts
				.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public String getToken(HttpServletRequest request) {
        String authenticationHeader = getAuthHeaderFromHeader( request );
        if ( authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            return authenticationHeader.substring(7);
        }
        return null;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
	
}
