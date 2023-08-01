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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

	private User testUser;
	private Trade testTrade;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TradeRepository tradeRepository;

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
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		tradeRepository.deleteAll();
		testTrade = new Trade();
		testTrade.setAccount("account");
		testTrade.setType("type");
		testTrade.setBuyQuantity(3.0);
		testTrade = tradeRepository.save(testTrade);
	}

	@Test
	public void testAddTradeGetPage() throws Exception {
		mockMvc.perform(get("/trade/add").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("trade/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddTradeWithMissingParams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate").with(user("testUser").password("testUser123?"))
				.param("buyQuantity", "-3")).andDo(print()).andExpect(model().attributeHasErrors("trade"))
				.andExpect(view().name("trade/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddTradeSuccess() throws Exception {
		mockMvc.perform(post("/trade/validate").with(user("testUser").password("testUser123?"))
				.param("account", "account").param("type", "type").param("buyQuantity", "4.0")).andDo(print())
				.andExpect(view().name("redirect:/trade/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteSuccess() throws Exception {
		mockMvc.perform(
				get("/trade/delete/{id}", testTrade.getTradeId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("redirect:/trade/list"));
	}

	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(
				get("/trade/update/{id}", testTrade.getTradeId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("trade/update")).andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
//		testTrade.setBidQuantity(4.0);
//		testTrade = tradeRepository.save(testTrade);
		mockMvc.perform(
				post("/trade/update/{id}", testTrade.getTradeId()).with(user("testUser").password("testUser123?"))
						.param("account", "account").param("type", "type").param("buyQuantity", "2.0"))
				.andDo(print()).andExpect(view().name("redirect:/trade/list"));
	}

	@Test
	public void testUpdateFail() throws Exception {

		mockMvc.perform(
				post("/trade/update/{id}", testTrade.getTradeId()).with(user("testUser").password("testUser123?"))
						.param("account", "account").param("type", "type").param("buyQuantity", "0"))
				.andDo(print()).andExpect(view().name("trade/update"));
	}

	@Test
	public void testDisplayListSuccess() throws Exception {
		mockMvc.perform(get("/trade/list").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("trade/list"));
	}

}
