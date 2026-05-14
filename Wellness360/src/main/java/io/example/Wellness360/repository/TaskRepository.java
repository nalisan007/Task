package io.example.Wellness360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.example.Wellness360.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {



}
