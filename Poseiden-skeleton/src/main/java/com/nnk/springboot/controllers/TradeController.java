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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@Controller
public class TradeController {

	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model) {
		logger.info("display rule name list");
		List<Trade> trades = tradeService.getAllTrades();
		model.addAttribute("trades", trades);
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTrade(Trade trade, Model model) {
		logger.info("add rule name page");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {

		if (!result.hasErrors()) {
			logger.info("adding rule name");
			tradeService.addTrade(trade);
			return "redirect:/trade/list";
		} else {
			logger.error("add rule name fail");
			return "trade/add";
		}
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		logger.info("update rule name");
		Trade tradeFound = tradeService.findTradeById(id);
		model.addAttribute("trade", tradeFound);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			logger.info("rule name updated");
			tradeService.updateTrade(trade);
			return "redirect:/trade/list";
		} else {
			logger.error("update rule name fail");
			return "trade/update";
		}

	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		logger.info("deleting rule name");
		Trade tradeFound = tradeService.findTradeById(id);
		tradeService.deleteTrade(tradeFound);
		return "redirect:/trade/list";
	}
}
