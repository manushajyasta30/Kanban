package com.assignment.kanban.model;

import java.util.ArrayList;
import java.util.List;

public class StageResponseModel {

	private long stageId;

	private String name;

	private List<TaskResponseModel> tasks = new ArrayList<>();

	/**
	 * @return the stageId
	 */
	public long getStageId() {
		return stageId;
	}

	/**
	 * @param stageId the stageId to set
	 */
	public void setStageId(long stageId) {
		this.stageId = stageId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tasks
	 */
	public List<TaskResponseModel> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(List<TaskResponseModel> tasks) {
		this.tasks = tasks;
	}

}
