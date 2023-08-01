package com.nnk.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private User testUser;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
		testUser = new User();
		testUser.setFullname("testUser");
		testUser.setPassword(passwordEncoder.encode("testUser123?"));
		testUser.setUsername("testUser");
		testUser.setRole("ROLE_USER");
		testUser = userRepository.save(testUser);
		userRepository.saveAuthority(testUser.getId(), testUser.getUsername(), testUser.getRole());
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void testLoginWithGoodPassword() throws Exception {
		mockMvc.perform(formLogin().user("testUser").password("testUser123?")).andDo(print())
				.andExpect(authenticated());
	}

	@Test
	public void testLoginWithWrongPassword() throws Exception {
		mockMvc.perform(formLogin("/login").user("testUser").password("testpassword")).andDo(print())
				.andExpect(unauthenticated());
	}

	@Test
	public void testUserListFailWhenNotAdmin() throws Exception {
		mockMvc.perform(get("/user/list").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	public void testDisplayListSuccessWhenAdmin() throws Exception {
		mockMvc.perform(get("/user/list").with(user("testUser").password("testUser123?").roles("ADMIN"))).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void testAddUserGetPage() throws Exception {
		mockMvc.perform(get("/user/add").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("user/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddUserWithMissingParams() throws Exception {
		mockMvc.perform(post("/user/validate").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(model().attributeHasErrors("user")).andExpect(view().name("user/add"))
				.andExpect(status().isOk());
	}

	@Test
	public void testAddUserSuccess() throws Exception {
		mockMvc.perform(post("/user/validate").with(user("testUser").password("testUser123?").roles("ADMIN"))
				.param("password", "1234Test?").param("username", "username").param("fullname", "fullname")
				.param("role", "ADMIN").param("fullname", "fullname")).andDo(print())
				.andExpect(view().name("redirect:/")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteUserSuccess() throws Exception {
		mockMvc.perform(get("/user/delete/{id}", testUser.getId())
				.with(user("testUser").password("testUser123?").roles("ADMIN"))).andDo(print())
				.andExpect(view().name("redirect:/user/list"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		mockMvc.perform(get("/user/update/{id}", testUser.getId())
				.with(user("testUser").password("testUser123?").roles("ADMIN"))).andDo(print())
				.andExpect(view().name("user/update")).andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
		mockMvc.perform(post("/user/update/{id}", testUser.getId())
				.with(user("testUser").password("testUser123?").roles("ADMIN")).param("password", "Test1234?")
				.param("username", "username").param("role", "ADMIN").param("fullname", "fullname")).andDo(print())
				.andExpect(view().name("redirect:/user/list"));
	}

	@Test
	public void testUpdateUserFailWithMissingParams() throws Exception {

		mockMvc.perform(post("/user/update/{id}", testUser.getId())
				.with(user("testUser").password("testUser123?").roles("ADMIN"))).andDo(print())
				.andExpect(model().attributeHasErrors("user")).andExpect(view().name("user/update"));
	}

	@Test
	public void testGetUpdateUserFailWhenInvalidId() throws Exception {
		mockMvc.perform(
				get("/user/update/{id}", 0).with(user("testUser").password("testUser123?").roles("role", "ADMIN")))
				.andDo(print())

				.andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
	}

}
