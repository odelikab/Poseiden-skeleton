package com.nnk.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

//@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class BidListTests {

	private BidList testBidList;

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private MockMvc mockMvc;

//	@BeforeEach
//	public void setUp() {
//		testBidList = new BidList("Account Test", "Type Test", 10d);
//
//	}

	@Test
	public void bidListTestSave() throws Exception {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		testBidList = bidListRepository.save(testBidList);
		assertNotNull(testBidList.getBidListId());
		assertEquals(testBidList.getBidQuantity(), 10d, 10d);
	}

//	@Test
//	public void bidListTestUpdate() {
//
//		// Update
//		testBidList.setBidQuantity(20d);
//		testBidList = bidListRepository.save(testBidList);
//		assertEquals(testBidList.getBidQuantity(), 20d, 20d);
//	}
//
//	@Test
//	public void bidListTestFind() {
//
//		// Find
//		List<BidList> listResult = bidListRepository.findAll();
//		assertTrue(listResult.size() > 0);
//	}
//
//	@Test
//	public void bidListTestDelete() {
//
//		// Delete
//		Integer id = testBidList.getBidListId();
//		bidListRepository.delete(testBidList);
//		Optional<BidList> bidList = bidListRepository.findById(id);
//		assertFalse(bidList.isPresent());
//	}
}
