package com.study.objects._07_salaries;

import java.math.BigDecimal;

public class HourlyEmployee extends Employee {

  public HourlyEmployee(String name, BigDecimal basePay, BigDecimal workingHours) {
    super(name, basePay, true, workingHours);
  }

  @Override
  public BigDecimal getTaxRate() {
    return BigDecimal.ZERO;
  }

  @Override
  public BigDecimal getMonthlyPay() {
    BigDecimal pay = getBasePay().multiply(getWorkingHours());

    return pay.multiply(BigDecimal.ONE.subtract(getTaxRate()));
  }
}