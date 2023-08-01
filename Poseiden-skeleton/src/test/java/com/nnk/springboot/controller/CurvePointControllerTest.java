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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private User testUser;
	private CurvePoint testCurvePoint;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurvePointRepository curvePointRepository;

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
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		curvePointRepository.deleteAll();
		testCurvePoint = new CurvePoint();
		testCurvePoint.setCurveId(1);
		testCurvePoint.setTerm(2.0);
		testCurvePoint.setValue(3.0);
		testCurvePoint = curvePointRepository.save(testCurvePoint);
	}

	@Test
	public void testAddCurvePointGetPage() throws Exception {
		mockMvc.perform(get("/curvePoint/add").with(user("testUser").password("testUser123?")).param("curveId", "1")
				.param("term", "1")).andDo(print()).andExpect(view().name("curvePoint/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddCurvePointWithMissingParams() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").with(user("testUser").password("testUser123?")).param("term", "1"))
				.andDo(print()).andExpect(model().attributeHasErrors("curvePoint"))
				.andExpect(view().name("curvePoint/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddCurvePointSuccess() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").with(user("testUser").password("testUser123?"))
				.param("curveId", "1").param("term", "1.0").param("value", "3.0")).andDo(print())
				.andExpect(view().name("redirect:/curvePoint/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteCurvePointSuccess() throws Exception {
		mockMvc.perform(
				get("/curvePoint/delete/{id}", testCurvePoint.getId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("redirect:/curvePoint/list"));
	}

	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(
				get("/curvePoint/update/{id}", testCurvePoint.getId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("curvePoint/update")).andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
//		testCurvePoint.setBidQuantity(4.0);
//		testCurvePoint = curvePointRepository.save(testCurvePoint);
		mockMvc.perform(
				post("/curvePoint/update/{id}", testCurvePoint.getId()).with(user("testUser").password("testUser123?"))
						.param("curveId", "2").param("term", "2.0").param("value", "4.0"))
				.andDo(print()).andExpect(view().name("redirect:/curvePoint/list"));
	}

	@Test
	public void testUpdateCurvePointFail() throws Exception {

		mockMvc.perform(post("/curvePoint/update/{id}", testCurvePoint.getId())
				.with(user("testUser").password("testUser123?")).param("term", "-4.0")).andDo(print())
				.andExpect(model().attributeHasErrors("curvePoint")).andExpect(view().name("curvePoint/update"));
//			fail("must fail when missing params");

	}

	@Test
	public void testDisplayListSuccess() throws Exception {
		mockMvc.perform(get("/curvePoint/list").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("curvePoint/list"));
	}

}
