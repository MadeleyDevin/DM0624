package ToolRentalPackage;
import java.util.Scanner;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ToolRental {
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
        
        //Take in input from the customer
        System.out.println("What tool would you like to rent?\n" +
        "Enter CHNS for a Chainsaw\n" + 
        "Enter LADW for a Ladder\n" +
        "Enter JAKD for a DeWalt Jackhammer\n" +
        "Enter JAKR for a Ridgid Jackhammer\n");
		String tool = scanner.nextLine();

		System.out.println("What day would you like to rent the " + tool + 
				"? (Please enter MM/DD/YY format)");
		String rentalDayStart = scanner.nextLine();
		System.out.println("How many days would you like to rent the " + tool + " for?");
		int rentalDaysNum = scanner.nextInt();
		
        
        checkout(tool, rentalDayStart, rentalDaysNum, 25);
    }
    
	//Function to determine the number of days to charge for the tool
    public static int rentalDayCount(int index, LocalDate checkoutDate, int numOfDays)
    {
        boolean weekendCharge = false;
        boolean holidayCharge = false;
        int chargedRentalDayCount = 0;
        int weekDay = checkoutDate.getDayOfWeek().getValue();
        LocalDate dateBeingParsed = checkoutDate;
        LocalDate fourthOfJuly = LocalDate.of(checkoutDate.getYear(), Month.JULY, 4);
        LocalDate laborDay = null;
 
        if(checkoutDate.getMonth() == Month.SEPTEMBER)
        {
            laborDay = checkoutDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        }
    
        //Holiday charge for chainsaw rentals
        if(index == 0)
        {
            holidayCharge = true;
        }
        //Weekend charge for ladder rentals
        else if(index == 1)
        {
            weekendCharge = true;
        }
        
        for(int i = 1; i <= numOfDays; i++)
        {
            dateBeingParsed = dateBeingParsed.plusDays(1);
            //Once we reach Sunday, wrap back around to Monday
            if(weekDay == 7)
            {
                weekDay = 1;
            }
            else
            {
                weekDay++;
            }
            
            if(dateBeingParsed.equals(LocalDate.of(checkoutDate.getYear(), Month.JULY, 3)) && weekDay == 5)
            {
                //If fourth of July falls on Saturday, count Friday as the holiday
                fourthOfJuly = LocalDate.of(checkoutDate.getYear(), Month.JULY, 3);
            }
            else if(dateBeingParsed.equals(fourthOfJuly) && weekDay == 7)
            {
                //If fourth of July falls on Saturday, count Friday as the holiday
                fourthOfJuly = LocalDate.of(checkoutDate.getYear(), Month.JULY, 5);
            }
            if(weekDay < 6 || weekendCharge == true ||
                    (holidayCharge == true && dateBeingParsed.equals(fourthOfJuly)) ||
                    (holidayCharge == true && dateBeingParsed.equals(laborDay)))
            {
                chargedRentalDayCount++;
            }
        }
        
        return chargedRentalDayCount;
    }
    public static void checkout(String tool, String checkoutDate, int numOfDays, int discount)
    {
        int index;
        /*
        List of tools available to rent and their and corresponding information
        tools[x][0] - Tool code
        tools[x][1] - Tool type
        tools[x][2] - Tool brand
        tools[x][3] - Daily rental charge
        */
        String[][] tools = new String[5][4];
        tools[0][0] = ("CHNS");
        tools[0][1] = ("Chainsaw"); 
        tools[0][2] = ("Stihl");
        tools[0][3] = ("1.49");
        tools[1][0] = ("LADW");
        tools[1][1] = ("Ladder"); 
        tools[1][2] = ("Werner");
        tools[1][3] = ("1.99");
        tools[2][0] = ("JAKD");
        tools[2][1] = ("Jackhammer"); 
        tools[2][2] = ("DeWalt");
        tools[2][3] = ("2.99");
        tools[3][0] = ("JAKR");
        tools[3][1] = ("Jackhammer"); 
        tools[3][2] = ("Ridgid");
        tools[3][3] = ("2.99");
        
        //Make the discount into a percentage
        double discountDec = discount * .01;
        
        //Date of rental put into the MM/DD/YY format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDate parsedDate = LocalDate.parse(checkoutDate, formatter);
        
        //Date tool should be returned
        LocalDate returnDate = parsedDate.plusDays(numOfDays);
        String parsedReturnDate = returnDate.format(formatter);
        
        //Exception thrown when rental day count is not 1 or greater
        if(numOfDays < 1)
        {
            throw new  IllegalArgumentException("The number of days for " +
                "rental is less than one. Cannot process rental. ");
        }
        
        //Exception thrown when discount percent is not in the range 0-100
        if(discount < 0 || discount > 100)
        {
            throw new IllegalArgumentException("The discount is not in the " +
                "form of a percentage. Cannot process rental.");
        }
        //Determine the index based on the tool the customer chose to rent
        switch(tool)
        {
            case "CHNS":
                index = 0;
                break;
            case "LADW":
                index = 1;
                break;
            case "JAKD":
                index = 2;
                break;
            case "JAKR":
                index = 3;
                break;
            default:
                throw new IllegalArgumentException("Invalid tool code: " + tool);
        }
        
        //Determine the number of days to charge for the tool
        int chargedRentalDayCount = rentalDayCount(index, parsedDate, numOfDays);
        
        //Calculate prices
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();       
        double hourlyToolPrice = Double.parseDouble(tools[index][3]);
        double preDiscountCharge = hourlyToolPrice * chargedRentalDayCount;
        String preDiscountChargeString = currencyFormatter.format(preDiscountCharge);
        double discountedAmount = preDiscountCharge * discountDec;
        String discountedAmountString = currencyFormatter.format(discountedAmount);
        double finalCharge = preDiscountCharge - discountedAmount;
        String finalChargeString = currencyFormatter.format(finalCharge);
        
        //Create the rental agreement.
        System.out.println("Rental Agreement:");
        System.out.println("Tool Code: " + tool);
        System.out.println("Tool Type: " + tools[index][1]);
        System.out.println("Tool Brand: " + tools[index][2]);
        System.out.println("Number of Rental Days: " + numOfDays);
        System.out.println("Checkout Date: " + checkoutDate);
        System.out.println("Due Date: " + parsedReturnDate);
        System.out.println("Daily Rental Charge: $" + tools[index][3]);
        System.out.println("Charge Days: " + chargedRentalDayCount);
        System.out.println("Pre-Discount Charge: " + preDiscountChargeString);
        System.out.println("Discount Percent: " + discount + "%");
        System.out.println("Discount Amount: " + discountedAmountString);
        System.out.println("Final Charge: " + finalChargeString);       
        
    }
}
