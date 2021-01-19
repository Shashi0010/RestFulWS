package com.durgasoft.ws.service;

import com.durgasoft.ws.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	UserDao createUser(UserDao userDao);
	UserDao getUser(String email);

    UserDao getUserById(String userId);
}
