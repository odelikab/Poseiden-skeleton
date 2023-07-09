package com.nnk.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

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

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/ruleName/list")
	public String home(Model model) {
		// TODO: find all RuleName, add to model
		List<RuleName> ruleNames = ruleNameService.getAllRuleNames();
		model.addAttribute("ruleNames", ruleNames);
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName, Model model) {
		model.addAttribute("ruleName", new RuleName());
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return RuleName list
		if (!result.hasErrors()) {
			ruleNameService.addRuleName(ruleName);
		}
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get RuleName by Id and to model then show to the form
		RuleName ruleNameFound = ruleNameService.findRuleNameById(id);
		model.addAttribute("ruleName", ruleNameFound);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update RuleName and
		// return RuleName list
		if (!result.hasErrors()) {
//			RuleName ruleNameFound = ruleNameService.findRuleNameById(id);
//			if (ruleNameFound != null) {
//				ruleNameFound.setDescription(ruleName.getDescription());
//				ruleNameFound.setName(ruleName.getName());
//				ruleNameFound.setJson(ruleName.getJson());
//				ruleNameFound.setSqlPart(ruleName.getSqlPart());
//				ruleNameFound.setSqlStr(ruleName.getSqlStr());
//				ruleNameFound.setTemplate(ruleName.getTemplate());
			ruleNameService.updateRuleName(ruleName);
//			}
		}
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
		RuleName ruleNameFound = ruleNameService.findRuleNameById(id);
		if (ruleNameFound != null) {
			ruleNameService.deleteRuleName(ruleNameFound);
		}
		return "redirect:/ruleName/list";
	}
}
