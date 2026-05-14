package io.example.Wellness360.entity;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

//Dto to avoid getting input for id ,if not leading to overriding of unnecessary data by repo.save()
public class PostTaskDto {

	@NotBlank
	private String title;
	private String description;
	@FutureOrPresent
	private LocalDate dueDate;
	private Status status = Status.PENDING;

	public enum Status {
		PENDING, IN_PROGRESS, COMPLETED;
	}

	@Override
	public String toString() {
		return "PostTaskDto [title=" + title + ", description=" + description + ", dueDate=" + dueDate + ", status="
				+ status + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
