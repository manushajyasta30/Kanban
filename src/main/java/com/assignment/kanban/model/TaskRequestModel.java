package com.assignment.kanban.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TaskRequestModel {

	@NotBlank(message = "title is mandatory")
	private String title;

	//@NotBlank(message = "description is mandatory")
	private String description;

	@Min(value = 1, message = "assigneeId should be greater than zero")
	private long assigneeId;

	/** The story points. */
	@Min(value = 1, message = "storyPoints should be greater than zero")
	private int storyPoints;

	@Min(value = 1, message = "stageId should be greater than zero")
	private long stageId;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the storyPoints
	 */
	public int getStoryPoints() {
		return storyPoints;
	}

	/**
	 * @param storyPoints the storyPoints to set
	 */
	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}

	/**
	 * @return the assigneeId
	 */
	public long getAssigneeId() {
		return assigneeId;
	}

	/**
	 * @param assigneeId the assigneeId to set
	 */
	public void setAssigneeId(long assigneeId) {
		this.assigneeId = assigneeId;
	}

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

}
