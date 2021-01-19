package com.durgasoft.ws.service;

import com.durgasoft.ws.Util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.durgasoft.ws.dao.UserDao;
import com.durgasoft.ws.entity.UserEntity;
import com.durgasoft.ws.repo.UserRepo;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDao createUser(UserDao userDao) {

		if(userRepo.findByEmail(userDao.getEmail()) != null) throw new RuntimeException("Record already exist");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDao, userEntity);

		String publicUserId = utils.generateUserId(30);
		
		userEntity.setEncryptedPassword(passwordEncoder.encode(userDao.getPassword()));
		userEntity.setUserId(publicUserId);
		
		UserEntity storedUser = userRepo.save(userEntity);
		
		UserDao returnValue = new UserDao();
		BeanUtils.copyProperties(storedUser, returnValue);
		
		return returnValue;
	}

    @Override
    public UserDao getUser(String email) {

        UserEntity userEntity = userRepo.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        UserDao returnValue = new UserDao();
        BeanUtils.copyProperties(userEntity, returnValue);

	    return returnValue;
    }

	@Override
	public UserDao getUserById(String userId) {

		UserDao returnValue = new UserDao();
		UserEntity userEntity = userRepo.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepo.findByEmail(email);

		if(userEntity == null) throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}
}
