package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListTests {

	private BidList testBidList;

	@Autowired
	private BidListRepository bidListRepository;

	@BeforeEach
	public void setUp() {
		bidListRepository.deleteAll();
		testBidList = new BidList("Account Test", "Type Test", 10d);

	}

	@Test
	public void bidListTestSave() throws Exception {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		testBidList = bidListRepository.save(testBidList);
		assertNotNull(testBidList.getBidListId());
		assertEquals(testBidList.getBidQuantity(), 10d, 10d);
	}

	@Test
	public void bidListTestUpdate() {

		// Update
		testBidList.setBidQuantity(20d);
		testBidList = bidListRepository.save(testBidList);
		assertEquals(testBidList.getBidQuantity(), 20d, 20d);
	}

	@Test
	public void bidListTestFind() {

		// Find
		testBidList = bidListRepository.save(testBidList);
		List<BidList> listResult = bidListRepository.findAll();
		assertTrue(listResult.size() > 0);
	}

	@Test
	public void bidListTestDelete() {

		// Delete
		testBidList = bidListRepository.save(testBidList);
		Integer id = testBidList.getBidListId();
		bidListRepository.delete(testBidList);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
