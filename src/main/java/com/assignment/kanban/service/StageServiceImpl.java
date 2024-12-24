package com.assignment.kanban.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.kanban.entity.Stage;
import com.assignment.kanban.model.StageRequestModel;
import com.assignment.kanban.model.StageResponseModel;
import com.assignment.kanban.model.TaskResponseModel;
import com.assignment.kanban.repository.StageRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class StageServiceImpl.
 */
@Service
public class StageServiceImpl {

	/** The stage repository. */
	@Autowired
	StageRepository stageRepository;

	/**
	 * Creates the stage.
	 *
	 * @param stageRequestModel the stage request model
	 * @return the response entity
	 */
	public ResponseEntity<Long> createStage(StageRequestModel stageRequestModel) {

		Stage stage = new Stage();
		stage.setName(stageRequestModel.getName());
		stage = stageRepository.save(stage);
		return ResponseEntity.created(null).body(stage.getId());
	}

	/**
	 * Update stage.
	 *
	 * @param stageId           the stage id
	 * @param stageRequestModel the stage request model
	 * @return the response entity
	 */
	public ResponseEntity<Long> updateStage(long stageId, StageRequestModel stageRequestModel) {

		Optional<Stage> stageopOptional = stageRepository.findById(stageId);

		if (stageopOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stage found with id " + stageId);
		}

		Stage stage = stageopOptional.get();
		stage.setName(stageRequestModel.getName());
		stage = stageRepository.save(stage);

		return ResponseEntity.created(null).body(null);
	}

	/**
	 * List stage with tasks.
	 *
	 * @return the response entity
	 */
	public ResponseEntity<List<StageResponseModel>> listStageWithTasks() {

		List<Stage> stages = stageRepository.findAll();

		List<StageResponseModel> stageResponseModels = stages.stream().map(stage -> {

			StageResponseModel stageResponseModel = new StageResponseModel();
			stageResponseModel.setName(stage.getName());
			stageResponseModel.setStageId(stage.getId());

			List<TaskResponseModel> taskResponseModels = stage.getTasks().stream().map(task -> {

				TaskResponseModel taskResponseModel = new TaskResponseModel();
				taskResponseModel.setTitle(task.getTitle());
				taskResponseModel.setDescription(task.getDescription());
				taskResponseModel.setStoryPoints(task.getStoryPoints());
				taskResponseModel.setAssignee(task.getAssignee().getUserName());
				taskResponseModel.setAssigneeId(task.getAssignee().getId());
				taskResponseModel.setTaskId(task.getId());
				taskResponseModel.setCreatedBy(task.getCreatedBy());
				return taskResponseModel;
			}).collect(Collectors.toList());

			stageResponseModel.setTasks(taskResponseModels);
			return stageResponseModel;
		}).collect(Collectors.toList());

		return ResponseEntity.created(null).body(stageResponseModels);

	}

	/**
	 * Delete stage.
	 *
	 * @param stageId the stage id
	 * @return the response entity
	 */
	public ResponseEntity<Long> deleteStage(long stageId) {

		Optional<Stage> stageopOptional = stageRepository.findById(stageId);

		if (stageopOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stage found with id " + stageId);
		}
		stageRepository.delete(stageopOptional.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
