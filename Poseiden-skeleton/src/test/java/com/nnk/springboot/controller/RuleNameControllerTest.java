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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

	private User testUser;
	private RuleName testRuleName;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private RuleNameRepository ruleNameRepository;

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
		ruleNameRepository.deleteAll();
		testRuleName = new RuleName();
		testRuleName.setName("name");
		testRuleName.setDescription("desc");
		testRuleName.setJson("json");
		testRuleName.setSqlPart("sqlpart");
		testRuleName.setSqlStr("sqlstr");
		testRuleName.setTemplate("template");
		testRuleName = ruleNameRepository.save(testRuleName);
	}

	@Test
	public void testAddRuleNameGetPage() throws Exception {
		mockMvc.perform(get("/ruleName/add").with(user("testUser").password("testUser123?"))).andDo(print())
				.andExpect(view().name("ruleName/add")).andExpect(status().isOk());
	}

	@Test
	public void testAddRuleNameWithMissingParams() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
				.with(user("testUser").password("testUser123?")).param("template", "template")).andDo(print())
				.andExpect(model().attributeHasErrors("ruleName")).andExpect(view().name("ruleName/add"))
				.andExpect(status().isOk());
	}

	@Test
	public void testAddRuleNameSuccess() throws Exception {
		mockMvc.perform(post("/ruleName/validate").with(user("testUser").password("testUser123?"))
				.param("template", "template").param("json", "json").param("sqlStr", "sqlstr")
				.param("sqlPart", "sqlpart").param("name", "name").param("description", "desc")).andDo(print())
				.andExpect(view().name("redirect:/ruleName/list")).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testDeleteSuccess() throws Exception {
		mockMvc.perform(
				get("/ruleName/delete/{id}", testRuleName.getId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("redirect:/ruleName/list"));
	}

	@Test
	public void testUpdatePage() throws Exception {
		mockMvc.perform(
				get("/ruleName/update/{id}", testRuleName.getId()).with(user("testUser").password("testUser123?")))
				.andDo(print()).andExpect(view().name("ruleName/update")).andExpect(status().isOk());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
		mockMvc.perform(
				post("/ruleName/update/{id}", testRuleName.getId()).with(user("testUser").password("testUser123?"))
						.param("template", "template").param("json", "json").param("sqlStr", "sqlstr")
						.param("sqlPart", "sqlpart").param("name", "name").param("description", "desc"))
				.andDo(print()).andExpect(view().name("redirect:/ruleName/list"));
	}

	@Test
	public void testUpdateFail() throws Exception {
		mockMvc.perform(
				post("/ruleName/update/{id}", testRuleName.getId()).with(user("testUser").password("testUser123?"))
						.param("template", "template").param("name", "name").param("buyQuantity", "0"))
				.andDo(print()).andExpect(view().name("ruleName/update"));

	}

	@Test
	public void testDisplayListSuccess() throws Exception {
		mockMvc.perform(get("/ruleName/list").with(user("testUser").password("testUser123?"))
				.param("template", "template").param("json", "json").param("sqlStr", "sqlstr")
				.param("sqlPart", "sqlpart").param("name", "name").param("description", "desc")).andDo(print())
				.andExpect(view().name("ruleName/list"));
	}
}
