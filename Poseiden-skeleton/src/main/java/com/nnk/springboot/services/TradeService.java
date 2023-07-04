package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {

	@Autowired
	TradeRepository tradeRepository;

	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	public Trade addTrade(@Valid Trade trade) {
		return tradeRepository.save(trade);
	}

	public Trade findTradeById(Integer id) {
		Optional<Trade> tradeFound = tradeRepository.findById(id);
		if (tradeFound.isPresent()) {
			return tradeFound.get();
		} else
			return null;
	}

	public Trade updateTrade(Trade trade) {
		return tradeRepository.save(trade);
	}

	public void deleteTrade(Trade trade) {
		tradeRepository.delete(trade);
	}

}
