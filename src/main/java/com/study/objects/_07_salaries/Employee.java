package com.study.objects._07_salaries;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Employee {
  private final String name;
  private final BigDecimal basePay;

  public abstract BigDecimal getTaxRate();
  public abstract BigDecimal getBasePay();
  public abstract BigDecimal getMonthlyPay();

}
