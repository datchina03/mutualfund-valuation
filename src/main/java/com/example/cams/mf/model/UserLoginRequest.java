package com.example.cams.mf.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name="enrollment request")
public record UserLoginRequest(
		@Schema(name="unique username")
		@NotNull
		String username,
		@Schema(name="enter the password")
		String password,
		@Schema(name="roletype of users",example="ADMIN/USER")
		String RoleType) {

}
