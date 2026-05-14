package io.example.Wellness360.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.example.Wellness360.entity.Task;
import io.example.Wellness360.entity.Task.Status;
import io.example.Wellness360.entity.Users;
import io.example.Wellness360.exception.TaskException;
import io.example.Wellness360.exception.UserException;
import io.example.Wellness360.repository.TaskRepository;
import io.example.Wellness360.repository.UserRepository;
import io.example.Wellness360.service.TaskService;
import io.example.Wellness360.service.UserService;

@ExtendWith(MockitoExtension.class)
class UnitTest {

	@Mock
	UserRepository userRepo;
	@InjectMocks
	UserService userService;
	@Mock
	TaskRepository taskRepo;
	@InjectMocks
	TaskService taskService;

	@Test
	public void registerUser_ThrowException_WhenUserAlreadyExist() {
		when(userRepo.existsByEmail("abc@gmail.com")).thenReturn(true);
		Users duplicateUser = new Users();
		duplicateUser.setEmail("abc@gmail.com");
		duplicateUser.setPassword("Dummy@123");
		assertThrows(UserException.class, () -> {
			userService.registerUser(duplicateUser);
		}, "Duplicate User Created!");
	}

	@Test
	public void getById_ThrowException_WhenTaskDontExist() {


		when(taskRepo.findById(3L)).thenReturn(Optional.empty());
		assertThrows(TaskException.class, () -> {
			taskService.getById(3L);
		}, "Exception Not thrown for retrieving Non existent task");
	}

	@Test
	public void getById_ReturnTask_WhenExist() {
		Task foundTask = new Task();
		foundTask.setId(1L);
		foundTask.setDescription("Task Description");
		foundTask.setTitle("Task Title");
		foundTask.setDueDate(LocalDate.now());
		foundTask.setCreatedAt(Instant.now());
		foundTask.setUpdatedAt(Instant.now());
		foundTask.setStatus(Status.COMPLETED);

		when(taskRepo.findById(1L)).thenReturn(Optional.of(foundTask));
		assertEquals(taskService.getById(1L), foundTask);
	}

}
