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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@Controller
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String home(Model model) {
		List<BidList> bidLists = bidListService.getAllBid();
		model.addAttribute("bidLists", bidLists);
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid, Model model) {
		model.addAttribute("bidList", new BidList());
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result) {
		if (!result.hasErrors()) {
			bidListService.addBid(bid);
			return "redirect:/bidList/list";
		} else
			return "/bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get Bid by Id and to model then show to the form
		BidList bidList = bidListService.findById(id);
		model.addAttribute("bidList", bidList);
		model.addAttribute("bidListupdate", new BidList());
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		// TODO: check required fields, if valid call service to update Bid and return
		if (!result.hasErrors()) {
			BidList bid = bidListService.findById(id);
			if (bid != null) {
				bid.setAccount(bidList.getAccount());
				bid.setType(bidList.getType());
				bid.setBidQuantity(bidList.getBidQuantity());
				bidListService.updateBid(bid);
			}
			return "redirect:/bidList/list";
		}
		return "bidList/update";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Bid by Id and delete the bid, return to Bid list
		bidListService.deleteBid(id);
		return "redirect:/bidList/list";
	}
}
