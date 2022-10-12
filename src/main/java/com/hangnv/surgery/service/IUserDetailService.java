package com.hangnv.surgery.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailService {
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
