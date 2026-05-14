package io.example.Wellness360;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import io.example.Wellness360.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

	@Autowired
	MockMvc mockmvc;

	@Autowired
	UserService userService;
	static String token;

	@BeforeEach
	public void registerUser_ObtainToken() throws Exception {

		String userpass = """
				{
					"email" : "dummyuser@gmail.com",
					"password" : "Dummy@123"
				}
				""";
		mockmvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(userpass))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created with email dummyuser@gmail.com"));
		MvcResult result = mockmvc
				.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(userpass))
				.andExpect(status().isOk()).andReturn();
		token = result.getResponse().getContentAsString();

	}

	@AfterEach
	public void logout_DeleteUser() throws Exception {

		mockmvc.perform(post("/auth/logout").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("Logged out"));
		userService.deleteById(userService.findUserByEmail("dummyuser@gmail.com"));

	}

	@Test
	public void addTask() throws Exception {

		String task1 = """
					{
						"title":"Contact Client",
						"description":"Ask client for more details.",
						"dueDate":"2026-06-20",
						"status": "IN_PROGRESS"
					}
				""";
		String task2 = """
					{
						"title":"Implement Task3",
						"description":"Finish the Task3 within the given due date",
						"dueDate":"2026-06-17"
					}
				""";
		mockmvc.perform(post("/api/v1/tasks").contentType(MediaType.APPLICATION_JSON).content(task1)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isCreated());
		mockmvc.perform(post("/api/v1/tasks").contentType(MediaType.APPLICATION_JSON).content(task2)
				.header("Authorization", "Bearer " + token)).andExpect(status().isCreated());
	}

	@Test
	public void getTasks() throws Exception {
		mockmvc.perform(
				get("/api/v1/tasks").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
		;
	}


}
