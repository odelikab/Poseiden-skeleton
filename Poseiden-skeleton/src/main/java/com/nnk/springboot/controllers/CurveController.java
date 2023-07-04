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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurveController {

	@Autowired
	CurvePointService curvePointService;

	@GetMapping("/curvePoint/list")
	public String home(Model model) {
		List<CurvePoint> curvePoints = curvePointService.getAllCurves();
		model.addAttribute("curvePoints", curvePoints);
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid, Model model) {
		model.addAttribute("curvePoint", new CurvePoint());
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Curve list
		if (!result.hasErrors()) {
			curvePointService.addCurvePoint(curvePoint);
		}
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		CurvePoint curvePointFound = curvePointService.findCurvePointById(id);
		model.addAttribute("curvePoint", curvePointFound);
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Curve and return
		// Curve list
		if (!result.hasErrors()) {
			CurvePoint curvePointFound = curvePointService.findCurvePointById(id);
			if (curvePointFound != null) {
				curvePointFound.setTerm(curvePoint.getTerm());
				curvePointFound.setValue(curvePoint.getValue());
				curvePointService.updateCurvePoint(curvePointFound);
			}
		}
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Curve by Id and delete the Curve, return to Curve list
		CurvePoint curvePointFound = curvePointService.findCurvePointById(id);
		if (curvePointFound != null) {
			curvePointService.deleteCurvePoint(curvePointFound);
		}
		return "redirect:/curvePoint/list";
	}
}
