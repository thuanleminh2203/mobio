package com.venesa.utils;

import org.springframework.validation.Errors;

public class ValidatorUtils {

	public static void checkLength(String data, Errors error, int max, String nameField) {
		if (data.length() > max) {
			error.rejectValue(nameField, null, nameField + " must smaller than " + max);
		}

	}

	public static void checkNullOrEmpty(String data, Errors error, String nameField) {
		if (data == null || data.isEmpty()) {
			error.rejectValue(nameField, null, nameField + " is not null");
		}
	}

	public static final void checkPhoneNumber(String data, Errors error, String nameField) {

		if (!data.matches(ConstantsUtil.REGEX_NUMBER_PHONE)) {
			error.rejectValue(nameField, null, nameField + " is not format");
		}
	}

	public static final void checkGender(int data, Errors error, String nameField) {
		if (data > 3 || data <= 0)
			error.rejectValue(nameField, null, nameField + " is not format");
	}

	public static final void checkRegex(String data, Errors error, String nameField, String regex) {
		if (!data.matches(regex)) {
			error.rejectValue(nameField, null, nameField + " is not format");
		}
	}

}
