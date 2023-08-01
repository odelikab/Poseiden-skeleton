package com.nnk.springboot.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

	private User testUser;
	private Rating testRating;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private RatingRepository ratingRepository;

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
//		assertEquals(-1, testUser.getUserId());
		testUser = userRepository.save(testUser);
//		assertNotEquals(-1, testUser.getUserId());
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		ratingRepository.deleteAll();
		testRating = new Rating();
		testRating.setMoodysRating("MoodysRating");
		testRating.setOrderNumber(1);
		testRating.setFitchRating("FitchRating");
		testRating.setSandPRating("sandPRating");
		testRating = ratingRepository.save(testRating);
	}

	@Test
	public void testAddRatingGetPage() throws Exception {
		mockMvc.perform(get("/rating/add").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("rating/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddRatingWithMissingParams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").with(user("testUser").password("testUser123?"))
				.param("orderNumber", "1.2")).andDo(print()).andExpect(model().attributeHasErrors("rating"))
				.andExpect(view().name("rating/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddRatingSuccess() throws Exception {
		mockMvc.perform(post("/rating/validate").with(user("testUser").password("testUser123?"))
				.param("sandPRating", "sandPRating").param("FitchRating", "FitchRating")
				.param("moodysRating", "moodysRating").param("orderNumber", "3")).andDo(print())
				.andExpect(view().name("redirect:/rating/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteSuccess() throws Exception {
		mockMvc.perform(get("/rating/delete/{id}", testRating.getId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("redirect:/rating/list"));
	}

	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(get("/rating/update/{id}", testRating.getId()).with(user("testUser").password("testUser123?"))
				.param("fitchRating", "FitchRating")).andDo(print()).andExpect(view().name("rating/update"))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
//		testRating.setBidQuantity(4.0);
//		testRating = ratingRepository.save(testRating);
		mockMvc.perform(post("/rating/update/{id}", testRating.getId()).with(user("testUser").password("testUser123?"))
				.param("fitchRating", "fitchRating").param("sandPRating", "sandPRating")
				.param("moodysRating", "MoodysRating").param("orderNumber", "4")).andDo(print())
				.andExpect(view().name("redirect:/rating/list"));
	}

	@Test
	public void testUpdateFail() throws Exception {

		mockMvc.perform(post("/rating/update/{id}", testRating.getId()).with(user("testUser").password("testUser123?"))
				.param("fitchRating", "fitchRating").param("orderNumber", "4.0")).andDo(print())
				.andExpect(view().name("rating/update"));
	}

	@Test
	public void testDisplayListSuccess() throws Exception {
		mockMvc.perform(get("/rating/list").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("rating/list"));
	}

}
