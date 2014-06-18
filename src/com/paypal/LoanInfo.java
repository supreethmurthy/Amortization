package com.paypal;

import java.math.BigDecimal;

public class LoanInfo {
	BigDecimal principal;
    BigDecimal annualInterestRate;
    BigDecimal downPayment;
    BigDecimal monthlyPayment;
    BigDecimal downPaymentPercent;
    BigDecimal additionalPrincipalPerMonth;
    BigDecimal borrowedAmount;
    int paymentDurationInYears;
    int paymentDurationInMonths;
    
    public BigDecimal getPrincipal() {
  		return principal;
  	}
  	public void setPrincipal(BigDecimal principal){
  		this.principal = principal;
  	}
    	public BigDecimal getAnnualInterestRate() {
    		return annualInterestRate;
    	}
    	public void setAnnualInterestRate(BigDecimal annualInterestRate){
    		this.annualInterestRate = annualInterestRate;
    	}
		public BigDecimal getDownPayment() {
			return downPayment;
		}
		public void setDownPayment(BigDecimal downPayment) {
			this.downPayment = downPayment;
		}
		public BigDecimal getMonthlyPayment() {
			return monthlyPayment;
		}
		public void setMonthlyPayment(BigDecimal monthlyPayment) {
			this.monthlyPayment = monthlyPayment;
		}
		public BigDecimal getDownPaymentPercent() {
			return downPaymentPercent;
		}
		public void setDownPaymentPercent(BigDecimal downPaymentPercent) {
			this.downPaymentPercent = downPaymentPercent;
		}
		public BigDecimal getAdditionalPrincipalPerMonth() {
			return additionalPrincipalPerMonth;
		}
		public void setAdditionalPrincipalPerMonth(
				BigDecimal additionalPrincipalPerMonth) {
			this.additionalPrincipalPerMonth = additionalPrincipalPerMonth;
		}
		public BigDecimal getBorrowedAmount() {
			return borrowedAmount;
		}
		public void setBorrowedAmount(BigDecimal borrowedAmount) {
			this.borrowedAmount = borrowedAmount;
		}
		public int getPaymentDurationInYears() {
			return paymentDurationInYears;
		}
		public void setPaymentDurationInYears(int paymentDurationInYears) {
			this.paymentDurationInYears = paymentDurationInYears;
			this.paymentDurationInMonths = paymentDurationInYears*12;
		}
		public int getPaymentDurationInMonths() {
			return paymentDurationInMonths;
		}

}
