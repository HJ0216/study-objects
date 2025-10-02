package com.study.objects._07_salaries;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Employee {
  private final String name;
  private final BigDecimal basePay;
  private final boolean hourly;
  private final BigDecimal workingHours;

  public abstract BigDecimal getTaxRate();
  public abstract BigDecimal getMonthlyPay();

}
