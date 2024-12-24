package com.assignment.kanban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.kanban.model.LoginRequestModel;
import com.assignment.kanban.service.LoginServiceImpl;

@RequestMapping(path = "/login")
@RestController
@CrossOrigin(origins="*",maxAge = 3600)
public class LoginController {

	@Autowired
	LoginServiceImpl loginServiceImpl;

	@PostMapping
	public ResponseEntity<String> authenticate(@RequestBody LoginRequestModel loginRequestModel) {

		return loginServiceImpl.authenticateUser(loginRequestModel);
	}
}
