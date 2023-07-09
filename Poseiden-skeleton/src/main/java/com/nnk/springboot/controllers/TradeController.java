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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@Controller
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model) {
		// TODO: find all Trade, add to model
		List<Trade> trades = tradeService.getAllTrades();
		model.addAttribute("trades", trades);
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTrade(Trade trade, Model model) {
//		model.addAttribute("trade", new Trade());
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Trade list
		if (!result.hasErrors()) {
			tradeService.addTrade(trade);
			return "redirect:/trade/list";
		} else
			return "/trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Trade by Id and to model then show to the form
		Trade tradeFound = tradeService.findTradeById(id);
		model.addAttribute("trade", tradeFound);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Trade and return
		// Trade list
		if (!result.hasErrors()) {
			tradeService.updateTrade(trade);
			return "redirect:/trade/list";
		} else
			return "/trade/update";

	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Trade by Id and delete the Trade, return to Trade list
		Trade tradeFound = tradeService.findTradeById(id);
		tradeService.deleteTrade(tradeFound);
		return "redirect:/trade/list";
	}
}
