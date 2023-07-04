package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {

	@Autowired
	CurvePointRepository curvePointRepository;

	public List<CurvePoint> getAllCurves() {
		return curvePointRepository.findAll();
	}

	public CurvePoint addCurvePoint(@Valid CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	public CurvePoint findCurvePointById(Integer id) {
		Optional<CurvePoint> curvePointFound = curvePointRepository.findById(id);
		if (curvePointFound.isPresent()) {
			return curvePointFound.get();
		} else
			return null;
	}

	public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	public void deleteCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
	}

}
