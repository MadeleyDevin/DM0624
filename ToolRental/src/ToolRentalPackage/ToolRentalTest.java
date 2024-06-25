package ToolRentalPackage;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.junit.Before;

public class ToolRentalTest {
	
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckout1() {
	    ToolRental.checkout("JAKR", "9/3/15", 5, 101);
	}
	
	@Test
	public void testCheckout2() {
	    ToolRental.checkout("LADW", "7/2/20", 3, 10);
	    assertEquals("Rental Agreement:\n"
	    		+ "Tool Code: LADW\n"
	    		+ "Tool Type: Ladder\n"
	    		+ "Tool Brand: Werner\n"
	    		+ "Number of Rental Days: 3\n"
	    		+ "Checkout Date: 7/2/20\n"
	    		+ "Due Date: 7/5/20\n"
	    		+ "Daily Rental Charge: $1.99\n"
	    		+ "Charge Days: 3\n"
	    		+ "Pre-Discount Charge: $5.97\n"
	    		+ "Discount Percent: 10%\n"
	    		+ "Discount Amount: $0.60\n"
	    		+ "Final Charge: $5.37", 		    		
	    		outputStreamCaptor.toString().trim());
	}
	
	@Test
	public void testCheckout3() {
	    ToolRental.checkout("CHNS", "7/2/15", 5, 25);
	    assertEquals("Rental Agreement:\n"
	    		+ "Tool Code: CHNS\n"
	    		+ "Tool Type: Chainsaw\n"
	    		+ "Tool Brand: Stihl\n"
	    		+ "Number of Rental Days: 5\n"
	    		+ "Checkout Date: 7/2/15\n"
	    		+ "Due Date: 7/7/15\n"
	    		+ "Daily Rental Charge: $1.49\n"
	    		+ "Charge Days: 3\n"
	    		+ "Pre-Discount Charge: $4.47\n"
	    		+ "Discount Percent: 25%\n"
	    		+ "Discount Amount: $1.12\n"
	    		+ "Final Charge: $3.35", 		    		
	    		outputStreamCaptor.toString().trim());
	}
	
	@Test
	public void testCheckout4() {
	    ToolRental.checkout("JAKD", "9/3/14", 6, 0);
	    assertEquals("Rental Agreement:\n"
	    		+ "Tool Code: JAKD\n"
	    		+ "Tool Type: Jackhammer\n"
	    		+ "Tool Brand: DeWalt\n"
	    		+ "Number of Rental Days: 6\n"
	    		+ "Checkout Date: 9/3/14\n"
	    		+ "Due Date: 9/9/14\n"
	    		+ "Daily Rental Charge: $2.99\n"
	    		+ "Charge Days: 4\n"
	    		+ "Pre-Discount Charge: $11.96\n"
	    		+ "Discount Percent: 0%\n"
	    		+ "Discount Amount: $0.00\n"
	    		+ "Final Charge: $11.96", 		    		
	    		outputStreamCaptor.toString().trim());
	}
	
	@Test
	public void testCheckout5() {
	    ToolRental.checkout("JAKR", "7/2/15", 9, 0);
	    assertEquals("Rental Agreement:\n"
	    		+ "Tool Code: JAKR\n"
	    		+ "Tool Type: Jackhammer\n"
	    		+ "Tool Brand: Ridgid\n"
	    		+ "Number of Rental Days: 9\n"
	    		+ "Checkout Date: 7/2/15\n"
	    		+ "Due Date: 7/11/15\n"
	    		+ "Daily Rental Charge: $2.99\n"
	    		+ "Charge Days: 6\n"
	    		+ "Pre-Discount Charge: $17.94\n"
	    		+ "Discount Percent: 0%\n"
	    		+ "Discount Amount: $0.00\n"
	    		+ "Final Charge: $17.94", 		    		
	    		outputStreamCaptor.toString().trim());
	}
	
	@Test
	public void testCheckout6() {
	    ToolRental.checkout("JAKR", "7/2/20", 4, 50);
	    assertEquals("Rental Agreement:\n"
	    		+ "Tool Code: JAKR\n"
	    		+ "Tool Type: Jackhammer\n"
	    		+ "Tool Brand: Ridgid\n"
	    		+ "Number of Rental Days: 4\n"
	    		+ "Checkout Date: 7/2/20\n"
	    		+ "Due Date: 7/6/20\n"
	    		+ "Daily Rental Charge: $2.99\n"
	    		+ "Charge Days: 2\n"
	    		+ "Pre-Discount Charge: $5.98\n"
	    		+ "Discount Percent: 50%\n"
	    		+ "Discount Amount: $2.99\n"
	    		+ "Final Charge: $2.99", 		    		
	    		outputStreamCaptor.toString().trim());
	}

}
