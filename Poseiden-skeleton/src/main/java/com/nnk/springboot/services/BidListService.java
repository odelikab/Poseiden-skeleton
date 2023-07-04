package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	public List<BidList> getAllBid() {
		return bidListRepository.findAll();
	}

	public BidList addBid(BidList bid) {
		return bidListRepository.save(bid);
	}

	public BidList findById(Integer id) {
		Optional<BidList> bidFound = bidListRepository.findById(id);
		if (bidFound.isPresent()) {
			return bidFound.get();
		} else
			return null;
	}

	public BidList updateBid(BidList bid) {
		bidListRepository.save(bid);
		return null;
	}

	public void deleteBid(Integer id) {
		bidListRepository.deleteById(id);
		return;
	}
}
