package com.assignment.kanban.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.kanban.model.StageRequestModel;
import com.assignment.kanban.model.StageResponseModel;
import com.assignment.kanban.service.StageServiceImpl;
import com.assignment.kanban.util.ErrorMessageUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stage")
@CrossOrigin(origins="*",maxAge = 3600)
public class StageController {

	@Autowired
	StageServiceImpl stageServiceImpl;

	@PostMapping
	public ResponseEntity<Long> createStage(@Valid @RequestBody StageRequestModel stageRequestModel, Errors errors) {

		ErrorMessageUtil.getErrorMessage(errors);

		return stageServiceImpl.createStage(stageRequestModel);

	}

	@PutMapping("/{id}")
	
	public ResponseEntity<Long> updateStage(@PathVariable(name = "id") long stageId,
			@Valid @RequestBody StageRequestModel stageRequestModel, Errors errors) {
		ErrorMessageUtil.getErrorMessage(errors);
		return stageServiceImpl.updateStage(stageId, stageRequestModel);
	}

	@GetMapping
	public ResponseEntity<List<StageResponseModel>> listStages() {
		return stageServiceImpl.listStageWithTasks();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteStage(@PathVariable(name = "id") long stageId) {
		return stageServiceImpl.deleteStage(stageId);
	}
}
