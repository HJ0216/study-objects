package com.study.objects._07_salaries;

import java.math.BigDecimal;

public class SalariedEmployee extends Employee {

  public SalariedEmployee(String name, BigDecimal basePay) {
    super(name, basePay, false, BigDecimal.ZERO);
  }

  @Override
  public BigDecimal getTaxRate() {
    return BigDecimal.TEN;
  }

  @Override
  public BigDecimal getMonthlyPay() {
    return getBasePay().multiply(BigDecimal.ONE.subtract(getTaxRate()));
  }
}
