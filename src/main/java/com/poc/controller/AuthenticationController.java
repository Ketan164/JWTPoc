package com.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.models.AuthenticationRequest;
import com.poc.models.AuthenticationResponse;
import com.poc.utils.JWTUtils;

@RestController
public class AuthenticationController 
{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtils jWTUtils;
	
	@PostMapping("authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest ar) throws Exception
	{
		try
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ar.getUserName(), ar.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect credentials", e);
		}
		
		final UserDetails userdetails = userDetailsService.loadUserByUsername(ar.getUserName());
		
		final String jwt = jWTUtils.generateToken(userdetails);
		
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
