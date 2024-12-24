package com.assignment.kanban.model;

import jakarta.validation.constraints.NotBlank;

public class StageRequestModel {

	@NotBlank(message = "name is mandatory")
	private String name;

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

}
