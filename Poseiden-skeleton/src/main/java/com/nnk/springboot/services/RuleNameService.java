package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {

	@Autowired
	RuleNameRepository ruleNameRepository;

	public List<RuleName> getAllRuleNames() {
		return ruleNameRepository.findAll();
	}

	public RuleName addRuleName(@Valid RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	public RuleName findRuleNameById(Integer id) {
		Optional<RuleName> ruleNameFound = ruleNameRepository.findById(id);
		if (ruleNameFound.isPresent()) {
			return ruleNameFound.get();
		} else
			return null;
	}

	public RuleName updateRuleName(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	public void deleteRuleName(RuleName ruleName) {
		ruleNameRepository.delete(ruleName);
	}
}
