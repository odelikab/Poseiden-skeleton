package com.nnk.springboot.controller;

import static org.junit.jupiter.api.Assertions.fail;
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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

	private User testUser;
	private BidList testBidList;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private BidListRepository bidListRepository;

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
		bidListRepository.deleteAll();
		testBidList = new BidList();
		testBidList.setAccount("account");
		testBidList.setType("type");
		testBidList.setBidQuantity(3.0);
		testBidList = bidListRepository.save(testBidList);

	}

	@Test
	public void testAddBidListGetPage() throws Exception {
		mockMvc.perform(get("/bidList/add").with(user("testUser").password("testUser123?")).param("account", "account"))
				.andDo(print()).andExpect(view().name("bidList/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddBidListWithMissingParams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").with(user("testUser").password("testUser123?"))
				.param("account", "account")).andDo(print()).andExpect(model().attributeHasErrors("bidList"))
				.andExpect(view().name("bidList/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddBidListSuccess() throws Exception {
		mockMvc.perform(post("/bidList/validate").with(user("testUser").password("testUser123?"))
				.param("account", "account").param("type", "type").param("bidQuantity", "3.0")).andDo(print())
				.andExpect(view().name("redirect:/bidList/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteSuccess() throws Exception {
		mockMvc.perform(
				get("/bidList/delete/{id}", testBidList.getBidListId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("redirect:/bidList/list"));
	}

	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(get("/bidList/update/{id}", testBidList.getBidListId())
				.with(user("testUser").password("testUser123?")).param("account", "account")).andDo(print())
				.andExpect(view().name("bidList/update")).andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
//		testBidList.setBidQuantity(4.0);
//		testBidList = bidListRepository.save(testBidList);
		mockMvc.perform(
				post("/bidList/update/{id}", testBidList.getBidListId()).with(user("testUser").password("testUser123?"))
						.param("account", "account").param("type", "type").param("bidQuantity", "4.0"))
				.andDo(print()).andExpect(view().name("redirect:/bidList/list"));
	}

	@Test
	public void testUpdateFail() throws Exception {
		try {
			mockMvc.perform(post("/bidList/update/{id}", testBidList.getBidListId())
					.with(user("testUser").password("testUser123?")).param("type", "type").param("bidQuantity", "4.0"))
					.andDo(print()).andExpect(view().name("/bidList/update"));
			fail("must fail when missing params");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDisplayListSuccess() throws Exception {
		mockMvc.perform(get("/bidList/list").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("bidList/list"));
	}

}
