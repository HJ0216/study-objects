package com.study.objects._07_salaries;

import java.math.BigDecimal;

public class SalariedEmployee extends Employee {

  public SalariedEmployee(String name, BigDecimal basePay) {
    super(name, basePay);
  }

  @Override
  public BigDecimal getTaxRate() {
    return new BigDecimal("0.10");
  }

  @Override
  public BigDecimal getBasePay() {
    return getBasePay();
  }

  @Override
  public BigDecimal getMonthlyPay() {
    return getBasePay().multiply(BigDecimal.ONE.subtract(getTaxRate()));
  }
}
