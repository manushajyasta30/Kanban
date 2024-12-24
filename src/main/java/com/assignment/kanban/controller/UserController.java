package com.assignment.kanban.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.kanban.model.UserRequestModel;
import com.assignment.kanban.model.UserResponseModel;
import com.assignment.kanban.service.UserServiceImpl;
import com.assignment.kanban.util.ErrorMessageUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*",maxAge = 3600)
public class UserController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping
	public ResponseEntity<Long> createUser(@Valid @RequestBody UserRequestModel userRequestModel, Errors errors) {

		ErrorMessageUtil.getErrorMessage(errors);

		return userServiceImpl.createUser(userRequestModel);
	}

	@GetMapping(path = "/list")
	public ResponseEntity<List<UserResponseModel>> getUsers() {

		return userServiceImpl.listUsers();

	}

}
