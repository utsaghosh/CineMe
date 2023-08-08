package com.cineme.cinemeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersOutputModel {
	private Integer userId;
	private String userName;
	private String email;
	private String password;
	private String gender;
	private Long age;
}
