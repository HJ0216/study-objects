package com.study.objects._07_salaries;

import java.math.BigDecimal;

public record Employee(
  String name,
  BigDecimal basePay,
  boolean hourly,
  BigDecimal workingHours
) {

}
