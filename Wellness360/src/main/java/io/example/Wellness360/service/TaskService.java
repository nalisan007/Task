package io.example.Wellness360.service;

import java.time.Instant;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.example.Wellness360.entity.PostTaskDto;
import io.example.Wellness360.entity.Task;
import io.example.Wellness360.exception.TaskException;
import io.example.Wellness360.repository.TaskRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskService {
	private static final Logger log = Logger.getLogger(TaskService.class);
	private TaskRepository repo;
	private ModelMapper mapper;
	
	public TaskService(TaskRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public Page<Task> getAll(Pageable pageable) {
		log.infof("Returning All Tasks of page {} of size {}", pageable.getPageNumber(), pageable.getPageSize());
		return repo.findAll(pageable);
	}

	public Task getById(long id) {
		return repo.findById(id).orElseThrow(() -> new TaskException("Task with id doesn't exist!"));
	}

	@Transactional
	public Task save(PostTaskDto taskdto) {
		Task task = new Task();
		mapper.map(taskdto, task);
		task.setCreatedAt(Instant.now());
		Task result = repo.save(task);
		log.debugf("Saving Task with id : {}", result.getId());
		return result;
	}

	@Transactional
	public  Task update(long id , Task task) {
		Optional<Task> tasko = repo.findById(id);
		// Throw Exception if Task with given id doesn't exist which get handled by
		// Global Exception Handler
		Task existing = tasko.orElseThrow(() -> new TaskException("Task doesn't exist with id : %d".formatted(id)));
		existing.setId(id);
		existing.setTitle(task.getTitle());
		existing.setDescription(task.getDescription());
		existing.setStatus(task.getStatus());
		existing.setDueDate(task.getDueDate());
		existing.setUpdatedAt(Instant.now());
		return repo.saveAndFlush(existing);
	}

	@Transactional
	public void deleteById( long id) {
		if (!repo.existsById(id)) // Throw Exception if Task with given id doesn't exist which get handled by
									// Global Exception Handler
			throw new TaskException("Task doesn't exist with id : %d".formatted(id));
		log.infof("Deleting Task with id : {}", id);
		repo.deleteById(id);
		
	}

	@Transactional
	public void markComplete(long id) {

		Optional<Task> tasko = repo.findById(id);
		// Throw Exception if Task with given id doesn't exist which get handled by
		// Global Exception Handler
		Task task = tasko.orElseThrow(() -> new TaskException("Task doesn't exist with id : %d".formatted(id)));
		task.setStatus(Task.Status.COMPLETED);
		task.setUpdatedAt(Instant.now()); // Update updatedAt Field
		repo.save(task);

	}

}
