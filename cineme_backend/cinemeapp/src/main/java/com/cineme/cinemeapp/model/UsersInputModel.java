package com.cineme.cinemeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersInputModel {
	private String userName;
	private String email;
	private String password;
	private String gender;
	private String dateOfBirth;
}
