package io.example.Wellness360.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.example.Wellness360.entity.PostTaskDto;
import io.example.Wellness360.entity.Task;
import io.example.Wellness360.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = { "/api/", "/api/v{version}" }, version = "1.0+")
@Validated
public class TaskController {

	private TaskService service;

	public TaskController(TaskService service) {
		super();
		this.service = service;
	}


	@GetMapping("/tasks")
	public Page<Task> getAll(
			@PageableDefault(sort = { "dueDate",
					"id" }, direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		return service.getAll(pageable);
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<Task> getById(
			@Min(message = "Id must be greater than 0", value = 1) @PathVariable(name = "id") long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@PostMapping("/tasks")
	public ResponseEntity<Task> saveTask(@Valid @RequestBody PostTaskDto taskdto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(taskdto));
	}

	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(
			@Min(message = "Id must be greater than 0", value = 1) @PathVariable(name = "id") long id,
			@Valid @RequestBody Task task) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(id, task));
	}

	@DeleteMapping("/tasks/{id}")
	public ResponseEntity<?> deleteTask(@Min(message = "Id must be greater than 0", value = 1) @PathVariable(name="id") long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/tasks/{id}/complete")
	public ResponseEntity<?> markComplete(
			@Min(message = "Id must be greater than 0", value = 1) @PathVariable(name = "id") long id) {
		service.markComplete(id);
		return ResponseEntity.noContent().build();

	}
}
