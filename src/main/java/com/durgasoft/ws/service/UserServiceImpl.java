package com.durgasoft.ws.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.durgasoft.ws.dao.UserDao;
import com.durgasoft.ws.entity.UserEntity;
import com.durgasoft.ws.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDao createUser(UserDao userDao) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDao, userEntity);
		
		userEntity.setEncryptedPassword("testPass123");
		userEntity.setUserId("Test123");
		
		UserEntity storedUser = userRepo.save(userEntity);
		
		UserDao returnValue = new UserDao();
		BeanUtils.copyProperties(storedUser, returnValue);
		
		return returnValue;
	}

}
