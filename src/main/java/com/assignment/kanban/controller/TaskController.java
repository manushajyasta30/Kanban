package com.assignment.kanban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.kanban.model.TaskRequestModel;
import com.assignment.kanban.service.TaskServiceImpl;
import com.assignment.kanban.util.ErrorMessageUtil;

import jakarta.validation.Valid;

@RequestMapping("/task")
@RestController
@CrossOrigin(origins="*",maxAge = 3600)
public class TaskController {

	@Autowired
	TaskServiceImpl taskServiceImpl;

	@PostMapping
	public ResponseEntity<Long> createTask(@Valid @RequestBody TaskRequestModel taskRequestModel, Errors errors) {
		ErrorMessageUtil.getErrorMessage(errors);
		
		return taskServiceImpl.createTask(taskRequestModel);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Long> updateTask(@PathVariable(name = "id") long taskId,
			@Valid @RequestBody TaskRequestModel taskRequestModel, Errors errors) {

		ErrorMessageUtil.getErrorMessage(errors);

		return taskServiceImpl.updateTask(taskId, taskRequestModel);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Long> updateTask(@PathVariable(name = "id") long taskId) {
		return taskServiceImpl.deleteTask(taskId);
	}
}
