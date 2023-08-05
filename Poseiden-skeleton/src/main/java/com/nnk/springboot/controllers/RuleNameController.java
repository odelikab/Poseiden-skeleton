package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@Controller
public class RuleNameController {

	private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/ruleName/list")
	public String home(Model model) {
		logger.info("display rule name");
		List<RuleName> ruleNames = ruleNameService.getAllRuleNames();
		model.addAttribute("ruleNames", ruleNames);
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName, Model model) {
		logger.info("display add rule name page");
		model.addAttribute("ruleName", new RuleName());
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			logger.info("add rule name");
			ruleNameService.addRuleName(ruleName);
			return "redirect:/ruleName/list";
		} else {
			logger.error("invalid rule name");
			return "ruleName/add";
		}
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("update rule name");
		RuleName ruleNameFound = ruleNameService.findRuleNameById(id);
		model.addAttribute("ruleName", ruleNameFound);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			logger.info("update rule name ok");
			ruleNameService.updateRuleName(ruleName);
			return "redirect:/ruleName/list";
//			}
		}
		logger.error("rule name invalid");
		return "ruleName/update";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		logger.info("delete rule name");
		RuleName ruleNameFound = ruleNameService.findRuleNameById(id);
		if (ruleNameFound != null) {
			ruleNameService.deleteRuleName(ruleNameFound);
		}
		return "redirect:/ruleName/list";
	}
}
