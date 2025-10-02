package com.study.objects._07_salaries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Salary {

  private List<String> employees = new ArrayList<>(
      Arrays.asList("Employee1", "Employee2", "Employee3", "PartTime1", "PartTime2", "PartTime3"));
  private List<BigDecimal> basePays =
      new ArrayList<>(
          Arrays.asList(BigDecimal.valueOf(400), BigDecimal.valueOf(300), BigDecimal.valueOf(250),
              BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1.5)));
  private List<Boolean> hourlys = new ArrayList<>(
      Arrays.asList(false, false, false, true, true, true));
  private List<BigDecimal> timeCards =
      new ArrayList<>(Arrays.asList(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
          BigDecimal.valueOf(120), BigDecimal.valueOf(120), BigDecimal.valueOf(100)));

  public SalaryResponse calculatePay(String name) {
    BigDecimal taxRate = getTaxRate(name);
    BigDecimal pay = calculatePay(name, taxRate);
    return toResponse(name, pay);
  }

  private BigDecimal calculatePay(String name, BigDecimal taxRate) {
    if (isHourly(name)) {
      return calculatePartTimePay(name, taxRate);
    }

    return calculateEmployeePay(name, taxRate);
  }

  private boolean isHourly(String name) {
    return hourlys.get(employees.indexOf(name));
  }

  public SalaryResponse totalBasePay() {
    BigDecimal sum = sumBasePay();
    return toResponse("TOTAL", sum);
  }

  private BigDecimal sumBasePay() {
    BigDecimal sum = BigDecimal.ZERO;

    for (int i = 0; i < basePays.size(); i++) {
      String name = employees.get(i);
      if (!isHourly(name)) {
        sum = sum.add(basePays.get(i));
      }
    }

    return sum;
  }

  private SalaryResponse toResponse(String name, BigDecimal pay) {
    return new SalaryResponse(name, pay);
  }

  private BigDecimal calculateEmployeePay(String name, BigDecimal taxRate) {
    int index = employees.indexOf(name);
    BigDecimal pay = basePays.get(index);
    return pay.multiply(BigDecimal.ONE.subtract(taxRate));
  }

  private BigDecimal calculatePartTimePay(String name, BigDecimal taxRate) {
    int index = employees.indexOf(name);
    BigDecimal pay = basePays.get(index).multiply(timeCards.get((index)));
    return pay.multiply(BigDecimal.ONE.subtract(taxRate));
  }

  private BigDecimal getTaxRate(String tax) {
    return new BigDecimal(tax);
  }
}
