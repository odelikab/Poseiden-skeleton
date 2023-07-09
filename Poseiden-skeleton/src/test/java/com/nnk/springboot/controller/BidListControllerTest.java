package com.nnk.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testAddBidListWithMissingParams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").param("account", "account")).andDo(print())
				.andExpect(view().name("/bidList/add")).andExpect(status().is2xxSuccessful());

	}
}
