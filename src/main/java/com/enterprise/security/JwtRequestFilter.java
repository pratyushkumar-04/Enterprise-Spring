package com.enterprise.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.enterprise.serviceimpl.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtRequestFilter {

	// validates requests and extract roles so to allow resources as per role
	@Autowired
	private JwtService Jwtservice;

	@Autowired
	private JwtService jwtservice;

	@Autowired
	private UserServiceImpl userService;

	protected void doFilterInternal (HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		final String authHeader = request.getHeader("Authorization");
		
		String username=null;
		String jwt= null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			jwt=authHeader.substring(7);
			
			try {
				username=jwtservice.extractusername(jwt);
				
			}
			catch(Exception e) {}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails= this.userService.findByusername(username);
			
			if(Jwtservice.validateToken(jwt,userdetails)){
				UsernamePasswordAuthenticationToken authtoken= new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
			else {
			}
				
			}
		
		chain.doFilter(request, response);
		}

}
