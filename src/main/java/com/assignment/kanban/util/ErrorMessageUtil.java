package com.assignment.kanban.util;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

public class ErrorMessageUtil {

	public static void getErrorMessage(Errors errors) {

		if (errors.hasFieldErrors()) {
			FieldError fieldError = errors.getFieldError();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldError.getDefaultMessage());
		}
	}
}
