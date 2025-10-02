package com.study.objects._07_salaries;

import java.math.BigDecimal;

public class HourlyEmployee extends Employee {

  private final BigDecimal workingHours;

  public HourlyEmployee(String name, BigDecimal basePay, BigDecimal workingHours) {
    super(name, basePay);
    this.workingHours = workingHours;
  }

  @Override
  public BigDecimal getTaxRate() {
    return BigDecimal.ZERO;
  }

  @Override
  public BigDecimal getBasePay() {
    return BigDecimal.ZERO;
  }

  @Override
  public BigDecimal getMonthlyPay() {
    BigDecimal pay = getBasePay().multiply(workingHours);

    return pay.multiply(BigDecimal.ONE.subtract(getTaxRate()));
  }
}