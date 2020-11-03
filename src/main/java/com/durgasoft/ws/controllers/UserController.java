package com.durgasoft.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.durgasoft.ws.dao.UserDao;
import com.durgasoft.ws.model.request.UserDetailsRequest;
import com.durgasoft.ws.model.response.UserRest;
import com.durgasoft.ws.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUsers() {
		return "Get Users Method was called";
	}
	@PostMapping
	public UserRest addUsers(@RequestBody UserDetailsRequest userDetails) {
		UserRest userRest = new UserRest();
		UserDao userDao = new UserDao();
		BeanUtils.copyProperties(userDetails, userDao);
		
		UserDao createdUser = userService.createUser(userDao);
		BeanUtils.copyProperties(createdUser, userDao);
		
		return userRest;		
	}
	@PutMapping
	public String updateUsers() {
		return "Update Users Method was called";
	}
	@DeleteMapping
	public String deleteUsers() {
		return "Delete Users Method was called";
	}
}
