package com.assignment.kanban.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.kanban.entity.Stage;
import com.assignment.kanban.entity.Task;
import com.assignment.kanban.entity.User;
import com.assignment.kanban.model.TaskRequestModel;
import com.assignment.kanban.repository.StageRepository;
import com.assignment.kanban.repository.TaskRepository;
import com.assignment.kanban.repository.UserRepository;

@Service
public class TaskServiceImpl {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	StageRepository stageRepository;

	public ResponseEntity<Long> createTask(TaskRequestModel taskRequestModel) {

		Optional<Stage> stage = Optional.ofNullable(stageRepository.findAll().get(0));//stageRepository.findById(taskRequestModel.getStageId());

		if (stage.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400),
					"stage with id " + taskRequestModel.getStageId() + " not found");
		}

		Optional<User> user = userRepository.findById(taskRequestModel.getAssigneeId());

		if (user.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400),
					"assignee with id " + taskRequestModel.getAssigneeId() + " not found");
		}

		Task task = new Task();
		task.setTitle(taskRequestModel.getTitle());
		task.setDescription(taskRequestModel.getDescription());
		task.setStoryPoints(taskRequestModel.getStoryPoints());
		task.setAssignee(user.get());
		task.setStage(stage.get());
		task.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		taskRepository.save(task);
		return ResponseEntity.created(null).body(task.getId());

	}

	public ResponseEntity<Long> updateTask(long taskId, TaskRequestModel taskRequestModel) {

		Optional<Stage> stage = stageRepository.findById(taskRequestModel.getStageId());

		if (stage.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400),
					"stage with id " + taskRequestModel.getStageId() + " not found");
		}

		Optional<Task> taskOptional = taskRepository.findById(taskId);

		if (taskOptional.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400), "No Task found with id " + taskId);
		}

		Optional<User> user = userRepository.findById(taskRequestModel.getAssigneeId());

		if (user.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400),
					"No assignee found with id " + taskRequestModel.getAssigneeId());
		}

		Task task = taskOptional.get();
		task.setTitle(taskRequestModel.getTitle());
		task.setDescription(taskRequestModel.getDescription());
		task.setStoryPoints(taskRequestModel.getStoryPoints());
		task.setAssignee(user.get());
		task.setStage(stage.get());
		task.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		taskRepository.saveAndFlush(task);
		return ResponseEntity.created(null).body(task.getId());

	}

	/**
	 * Delete stage.
	 *
	 * @param stageId the stage id
	 * @return the response entity
	 */
	public ResponseEntity<Long> deleteTask(long taskId) {

		Optional<Task> taskOptional = taskRepository.findById(taskId);

		if (taskOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Task found with id " + taskId);
		}
		taskRepository.delete(taskOptional.get());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
