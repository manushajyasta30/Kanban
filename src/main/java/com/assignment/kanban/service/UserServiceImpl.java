package com.assignment.kanban.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.kanban.entity.User;
import com.assignment.kanban.model.UserRequestModel;
import com.assignment.kanban.model.UserResponseModel;
import com.assignment.kanban.repository.UserRepository;

@Service
public class UserServiceImpl {

	@Autowired
	UserRepository userRepository;

	public ResponseEntity<Long> createUser(UserRequestModel userRequestModel) {

		if (userRepository.findByUserName(userRequestModel.getUserName()).isPresent()) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"user with userName " + userRequestModel.getUserName() + " already exists");
		}

		User user = new User();

		user.setEmail(userRequestModel.getEmail());
		user.setFirstName(userRequestModel.getFirstName().trim());
		user.setLastName(userRequestModel.getLastName().trim());
		user.setPassword(userRequestModel.getPassword());
		user.setUserName(userRequestModel.getUserName().trim());

		user = userRepository.saveAndFlush(user);

		return ResponseEntity.created(null).body(user.getId());

	}

	public ResponseEntity<List<UserResponseModel>> listUsers() {

		List<User> users = userRepository.findAll();

		List<UserResponseModel> userResponseModels = users.stream().map(user -> {
			UserResponseModel userResponseModel = new UserResponseModel();
			userResponseModel.setUserId(user.getId());
			userResponseModel.setUserName(user.getUserName());
			return userResponseModel;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(userResponseModels);

	}
}
