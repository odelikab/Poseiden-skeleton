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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurvePointController {

	@Autowired
	private CurvePointService curvePointService;

	private static final Logger logger = LoggerFactory.getLogger(CurvePointController.class);

	@GetMapping("/curvePoint/list")
	public String home(Model model) {
		List<CurvePoint> curvePoints = curvePointService.getAllCurves();
		model.addAttribute("curvePoints", curvePoints);
		logger.info("CurvePoint list");
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint, Model model) {
		logger.info("Add curvePoint page");
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
		// TODO: check data valid and save to db, after saving return Curve list
		if (!result.hasErrors()) {
			curvePointService.addCurvePoint(curvePoint);
			logger.info("curvePoint {} added", curvePoint.getCurveId());
			return "redirect:/curvePoint/list";
		} else {
			logger.error("curvePoint not valid");
			return "curvePoint/add";
		}
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		CurvePoint curvePointFound = curvePointService.findCurvePointById(id);
		model.addAttribute("curvePoint", curvePointFound);
		logger.info("curvePoint {} update");
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
				curvePointService.updateCurvePoint(curvePoint);
				logger.info("curvePoint {} updated", curvePointFound.getCurveId());
			}
			return "redirect:/curvePoint/list";
		}
		logger.error("curvePoint not valid");
		return "curvePoint/update";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Curve by Id and delete the Curve, return to Curve list
		CurvePoint curvePointFound = curvePointService.findCurvePointById(id);
		if (curvePointFound != null) {
			curvePointService.deleteCurvePoint(curvePointFound);
			logger.info("curvePoint {} deleted");
		}
		return "redirect:/curvePoint/list";
	}
}
