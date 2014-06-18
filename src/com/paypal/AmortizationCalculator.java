package com.paypal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import com.paypal.LoanInfo;

/**
* Source http://en.wikipedia.org/wiki/Amortization_calculator.
* <pre>
* 
*        i(1+i)<sup>n</sup>        P * i                   i
* A = P ---------- = ----------- = P * (i + ---------)
*       (1+i)<sup>n</sup> - 1    1 -(1+i)<sup>-n</sup>            (1+i)<sup>n</sup> - 1
* 
* </pre>
* where
* <ul>
* <li>A = periodic payment amount or Mortgage</li>
* <li>P = amount of principal, net of initial payments, meaning "subtract any down-payments"</li>
* <li>i = periodic interest rate</li>
* <li>n = total number of payments</li>
* </ul>
*/
public class AmortizationCalculator
{
  //This calculator can process a maximum amount of a Billion dollar
  private  BigDecimal[] amountRange = new BigDecimal[] { new BigDecimal("0.00"), new BigDecimal("1000000000") };
  private  BigDecimal[] percentageRange = new BigDecimal[] {new BigDecimal("0.01"), new BigDecimal("100")};
  //The loan term has to be between 1 to 30
  private  int[] termRange = new int[] { 1, 30 };
  
  private boolean isValidAmount(String input)
  {
	  try
	  {
		  BigDecimal amount = new BigDecimal(input);
		  if (amount.compareTo(amountRange[0]) >= 0 && amount.compareTo(amountRange[1]) <= 0)
			  return true;
		  return false;
	  }
	  catch(NumberFormatException ex)
	  {
		  return false;
	  }
  }
  
  private boolean isValidPercentage(String input)
  {
	  try
	  {
		  BigDecimal amount = new BigDecimal(input);
		  if (amount.compareTo(percentageRange[0]) >= 0 && amount.compareTo(percentageRange[1]) <= 0)
			  return true;
		  return false;
	  }
	  catch(NumberFormatException ex)
	  {
		  return false;
	  }
  }
  
  private boolean isValidRange(String input)
  {
	  try
	  {
		  Integer range = Integer.parseInt(input);
		  if (range.compareTo(termRange[0]) >= 0 && range.compareTo(termRange[1]) <= 0)
			  return true;
		  return false;
	  }
	  catch(NumberFormatException ex)
	  {
		  return false;
	  }
  }
  
  private String readLine(String userPrompt,boolean closeHandler)  
  {
		String line = "";
		BufferedReader bufferedReader = null;
		try
		{
			System.out.print(userPrompt);
			bufferedReader =new BufferedReader(new InputStreamReader(System.in));
			line = bufferedReader.readLine().trim();
			return line;
		}catch (Exception ex)
		{
			ex.printStackTrace();
			return line;
		}finally
		{
			try
			{
				if (closeHandler)
					bufferedReader.close();
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}
  
  public static void main(String[] args)
  {
	  AmortizationCalculator ac = new AmortizationCalculator();
	  String[] userPrompt = new String[]{
				  "Please Enter the value of the Property\n",
				  "Please Enter Down Payment Percentage\n",
				  "Please Enter Loan Repayment Term in Years\n",
				  "Please Enter Annual Percentage Rate\n",
				  "Please Enter any Additional Amount you wish to pay every month. Enter 0 if you don't wish to pay any additional Amount\n"
	  };
	  BigDecimal principal = BigDecimal.ZERO;
	  BigDecimal downPaymentPercent = BigDecimal.ZERO;
      BigDecimal annualInterestRate = BigDecimal.ZERO;
      BigDecimal additionalPrincipal = BigDecimal.ZERO;
      int paymentDurationYears = 0;
      
	  for (int i=0;i<userPrompt.length;i++)
	  {
		  String line = "";
		  if (i==4)
			  line = ac.readLine(userPrompt[i], true);
		  else
			  line = ac.readLine(userPrompt[i],false);
		
		  boolean isValidInput = false;
		  switch(i)
		  {
			  case 0:
				  isValidInput = ac.isValidAmount(line);
				  if (isValidInput)
					  principal = new BigDecimal(line);
				  else
				  {
					  System.err.println("Property Value entered is not correct. It should be a numeric value between $0.01 to $1000000000");
					  System.exit(0);
				  }
				  break;
			  case 1:
				  isValidInput = ac.isValidPercentage(line);
				  if (isValidInput)
				  {
					  downPaymentPercent = new BigDecimal(line);
					  downPaymentPercent = downPaymentPercent.divide(new BigDecimal("100"), MathContext.DECIMAL64);
				  }
				  else
				  {
					  System.err.println("Down Payment Percentage Value entered is not correct. It should be a numeric value between 0.01 to 100");
					  System.exit(0);
				  }
				  break;
			  case 2:
				  isValidInput = ac.isValidRange(line);
				  if (isValidInput)
					  paymentDurationYears = Integer.parseInt(line);
				  else
				  {
					  System.err.println("Loan Repayment Term Value entered is not correct. It should be a numeric value between 1 to 30");
					  System.exit(0);
				  }
				  break;
			  case 3:
				  isValidInput = ac.isValidPercentage(line);
				  if (isValidInput)
				  {
					  annualInterestRate = new BigDecimal(line);
					  annualInterestRate = annualInterestRate.divide(new BigDecimal("100"), MathContext.DECIMAL64);
				  }
				  else
				  {
					  System.err.println("Annual Interest Rate Value entered is not correct. It should be a numeric value between 0.01 to 100");
					  System.exit(0);
				  }
				  break;
			  case 4:
				  isValidInput = ac.isValidAmount(line);
				  if (isValidInput)
					  additionalPrincipal = new BigDecimal(line);
				  else
				  {
					  System.err.println("Additional Amount Value entered is not correct. It should be a numeric value between $0.00 to $1000000000");
					  System.exit(0);
				  }
				  break;
				  
				  
		  }
				  
	  }
	  LoanInfo loanInfo = new LoanInfo();
      loanInfo.setPrincipal(principal);
      loanInfo.setDownPaymentPercent(downPaymentPercent);
      loanInfo.setAnnualInterestRate(annualInterestRate);
      loanInfo.setPaymentDurationInYears(paymentDurationYears);
      loanInfo.setAdditionalPrincipalPerMonth(additionalPrincipal);
      
      ac.calculatePayments(loanInfo);
      
      ac.showAmortizationSchedule(loanInfo);
  }

  /**
   * Calculate the monthly payment for the given loan amount, term, and interest rate.
   *
   */
  public void calculatePayments(LoanInfo loanInfo)
  {
	  
      BigDecimal monthlyRate = loanInfo.getAnnualInterestRate().divide(
              new BigDecimal("12"), MathContext.DECIMAL64);
      //Calculate Down Payment
      BigDecimal downPayment = loanInfo.getPrincipal().multiply(loanInfo.getDownPaymentPercent());
      downPayment.setScale(2, RoundingMode.UP);
      loanInfo.setDownPayment(downPayment);
      
      BigDecimal financeAmount = loanInfo.getPrincipal().subtract(downPayment);
      loanInfo.setBorrowedAmount(financeAmount);
      //Use the 3rd formula mentioned and calculate the divisor
      // (1+i)^n-1 where i = monthly rate
      BigDecimal divisor = (BigDecimal.ONE.add(monthlyRate).pow(
              loanInfo.getPaymentDurationInMonths(), MathContext.DECIMAL64)).subtract(BigDecimal.ONE);
      //((i/(1+i)^n-1)+i)*P
      BigDecimal payment = monthlyRate.divide(divisor, MathContext.DECIMAL64).add(
              monthlyRate).multiply(financeAmount);
      
      // Always round up
      payment = payment.setScale(2, RoundingMode.UP);
      
      payment = payment.add(loanInfo.getAdditionalPrincipalPerMonth());
      //Set Monthly Payment = Payment + Additional Principal Per Month
      loanInfo.setMonthlyPayment(payment);
      System.out.format("Principal = $%1$,.2f, down payment at %2$.2f %% = $%3$,.2f\r\n" +
      		"$%7$,.2f additional principal each month\r\n" +
              "%6$d Monthly payments of $%5$,.2f at %4$.2f %% annual interest rate\r\n",
      		loanInfo.getPrincipal(), loanInfo.getDownPaymentPercent().multiply(new BigDecimal("100")),
      		downPayment, loanInfo.getAnnualInterestRate().multiply(new BigDecimal("100")), payment, 
      		loanInfo.getPaymentDurationInMonths(), loanInfo.getAdditionalPrincipalPerMonth());
      
      
  }
  
  /**
   * Calculate and print out the amortization schedule.
   * The Interest portion of the payment is calculated as the rate (r) times the previous balance, 
   * and is usually rounded to the nearest cent. The Principal portion of the payment is calculated 
   * as Amount - Interest. The new Balance is calculated by subtracting the Principal from the 
   * previous balance. 
   */
  public void showAmortizationSchedule(LoanInfo loanInfo)
  {
      BigDecimal balance = loanInfo.getBorrowedAmount();
      BigDecimal totalInterest = BigDecimal.ZERO;
      BigDecimal monthlyRate = loanInfo.getAnnualInterestRate().divide(
              new BigDecimal("12"), MathContext.DECIMAL64);
      BigDecimal totalPrincipal = BigDecimal.ZERO;
      BigDecimal monthlyPayment = loanInfo.getMonthlyPayment();
      System.out.format("Original amount financed: $%1$,10.2f\r\n", balance);
      System.out.format("Monthly Mortgage Payment amount: $%1$,10.2f\r\n", monthlyPayment);
      System.out.println("\r\nPayment\t\tDuration\t\tPrincipal\tInterest\tTotal Pricipal\tTotal Interest\tOutstanding Balance");
      
      
      for (int n = 1; n <= loanInfo.getPaymentDurationInMonths() && balance.compareTo(BigDecimal.ZERO) > 0; n++)
      {
      	  int year = n/12;
      	  int month = n%12;
      	  String duration = year + " Year(s) and " + month + " Months";         		
          BigDecimal interest = balance.multiply(monthlyRate, MathContext.DECIMAL64);
          interest = interest.setScale(2, RoundingMode.HALF_EVEN);
          totalInterest = totalInterest.add(interest);
          BigDecimal principal = monthlyPayment.subtract(interest);
          balance = balance.subtract(principal);
          totalPrincipal = totalPrincipal.add(principal);
          // If this is the last payment adjust principal amount so the balance is 0,
          //otherwise its negative
          if (balance.compareTo(BigDecimal.ZERO) < 0)
          {
              principal = principal.add(balance);
              monthlyPayment = principal.add(interest);
              balance = BigDecimal.ZERO;
          }
          System.out.format("%1$d\t%2$25s\t$%3$,8.2f\t$%4$,8.2f\t$%5$,10.2f\t$%6$,10.2f\t$%7$,10.2f\r\n",
                 n,duration, principal, interest, totalPrincipal,totalInterest, balance);
      }
  }
}
