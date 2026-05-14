package io.example.Wellness360.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//For changing password
public class PasswordDto {

		@Positive
		long userId;
		@Size(min = 8, message = "Password must be atleast 8 character length")
		@NotBlank(message = "Password must not be blank")
		@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_]).+$", message = "Password must contain atleast 1 Upper case character, 1 number, 1 special character")
		String oldPassword;
		@Size(min = 8, message = "Password must be atleast 8 character length")
		@NotBlank(message = "Password must not be blank")
		@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_]).+$", message = "Password must contain atleast 1 Upper case character, 1 number, 1 special character")
		String newPassword;

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}



}
