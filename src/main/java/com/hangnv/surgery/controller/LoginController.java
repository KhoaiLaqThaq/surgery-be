package com.hangnv.surgery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangnv.surgery.dto.JwtResponse;
import com.hangnv.surgery.dto.LoginRequest;
import com.hangnv.surgery.repository.RoleRepository;
import com.hangnv.surgery.repository.UserRepository;
import com.hangnv.surgery.security.JwtUtils;
import com.hangnv.surgery.service.impl.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/signIn")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest data) {
		log.info("----------->entering: signIn");
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generationJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
			
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
		} catch (Exception e) {
//			e.printStackTrace();
			log.info("Exception: {}", e.getMessage());
			if (e.getMessage() != null) {
				if (StringUtils.equals(e.getMessage(), "Bad credentials")) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad credentials!");
				}
			}
		}
		return ResponseEntity.ok("Error");
	}
	
}
