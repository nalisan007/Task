package io.example.Wellness360.exception;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(TaskException.class)
	public ResponseEntity<?> handleTaskException(TaskException ex) {
		String traceId = UUID.randomUUID().toString();
		log.error("Task Exception [Trace id : {} ] ,{}", traceId, ex.getMessage(), ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problem.setTitle("Invalid Task");
		problem.setProperty("Timestamp", Instant.now());
		problem.setProperty("traceId", traceId);
		return ResponseEntity.status(problem.getStatus()).body(problem);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<?> handleUserException(UserException ex) {
		String traceId = UUID.randomUUID().toString();
		log.error(ex.getMessage());
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
		problem.setTitle("UnAuthenticated");
		problem.setProperty("Timestamp", Instant.now());
		problem.setProperty("TraceId", traceId);
		return ResponseEntity.status(problem.getStatus()).body(problem);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
		String traceId = UUID.randomUUID().toString();
		log.warn("{} Constraint Violated [Trace id : {}] , {}", ex.getConstraintName(), traceId, ex.getMessage(), ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
				"{} Constraint Violated".formatted(ex.getConstraintName()) + ex.getMessage());
		problem.setTitle("Constraint violation for input data");
		problem.setProperty(traceId, traceId);
		problem.setProperty("Timestamp", Instant.now());
		return ResponseEntity.status(problem.getStatus()).body(problem);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String traceId = UUID.randomUUID().toString();
		StringBuilder errors = new StringBuilder();
		errors.append("TraceId : " + traceId + "\n");
		ex.getFieldErrors().forEach(e -> {
			errors.append("{} has invalid value {}.\n".formatted(e.getField(), e.getRejectedValue()));
			log.warn("{} has Invalid value {}  [TraceId : {} ]", e.getField(), e.getRejectedValue(), traceId);
		});
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors.toString());

		problem.setTitle("Constraint violation for input data");
		problem.setProperty("traceId", traceId);
		problem.setProperty("Timestamp", Instant.now());
		return ResponseEntity.status(problem.getStatus()).body(problem);

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String traceId = UUID.randomUUID().toString();

		log.warn("Data Integrity Violation [TraceId : {} ] : {} , ", traceId, ex.getMostSpecificCause().getMessage(),
				ex);
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
				ex.getMostSpecificCause().getMessage());
		problem.setTitle("Data Integrity Violation");
		problem.setProperty("Timestamp", Instant.now());
		problem.setProperty("traceId", traceId);
		return ResponseEntity.status(problem.getStatus()).body(problem);
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<?> handleOptimisticLockingFailureException(OptimisticLockingFailureException ex) {
		String traceId = UUID.randomUUID().toString();
		log.warn("Optimistic Lock Failure [TraceId : {} ] : {} : ",traceId ,ex.getMostSpecificCause().getMessage() , ex.getMostSpecificCause());
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
				ex.getMostSpecificCause().getMessage());
		problem.setTitle("Optimistic Locking Failure");
		problem.setProperty("TimeStamp", Instant.now());
		problem.setProperty("traceId", traceId);
		return ResponseEntity.status(problem.getStatus()).body(problem);
	}

}
