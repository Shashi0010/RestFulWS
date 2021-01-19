package com.durgasoft.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.durgasoft.ws.dao.UserDao;
import com.durgasoft.ws.model.request.UserDetailsRequest;
import com.durgasoft.ws.model.response.UserRest;
import com.durgasoft.ws.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path = "/{id}")
	public UserRest getUsers(@PathVariable String id) {

	    UserRest user = new UserRest();
        UserDao userDao = userService.getUserById(id);
        BeanUtils.copyProperties(userDao, user);
		return user;
	}
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequest userDetails) {
		UserRest userRest = new UserRest();
		UserDao userDao = new UserDao();
		BeanUtils.copyProperties(userDetails, userDao);
		
		UserDao createdUser = userService.createUser(userDao);
		BeanUtils.copyProperties(createdUser, userRest);
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
