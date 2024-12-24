package com.assignment.kanban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.kanban.model.LoginRequestModel;
import com.assignment.kanban.util.KanbanJwtUtil;

@Service
public class LoginServiceImpl {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	KanbanJwtUtil kanbanJwtUtil;

	public ResponseEntity<String> authenticateUser(LoginRequestModel loginRequestModel) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequestModel.getUserName(), loginRequestModel.getPassword()));

		if (authentication.isAuthenticated()) {
			return ResponseEntity.ok().body(kanbanJwtUtil.generateToken(loginRequestModel.getUserName()));
		}

		else {
			throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Bad credentials");
		}

	}
}
