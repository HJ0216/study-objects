package com.study.objects._07_salaries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Salary {

  private final List<Employee> employees = new ArrayList<>(
      Arrays.asList(
          new SalariedEmployee("Employee1", BigDecimal.valueOf(400)),
          new SalariedEmployee("Employee2", BigDecimal.valueOf(300)),
          new SalariedEmployee("Employee3", BigDecimal.valueOf(250)),
          new HourlyEmployee("PartTime1", BigDecimal.valueOf(1), BigDecimal.valueOf(120)),
          new HourlyEmployee("PartTime2", BigDecimal.valueOf(1), BigDecimal.valueOf(120)),
          new HourlyEmployee("PartTime3", BigDecimal.valueOf(1.5), BigDecimal.valueOf(100))
      )
  );

  public SalaryResponse calculatePay(Employee employee) {
    return toResponse(employee.getName(), employee.getMonthlyPay());
  }

  public SalaryResponse totalBasePay() {
    BigDecimal sum = sumBasePay();
    return toResponse("TOTAL BASE PAY", sum);
  }

  public SalaryResponse totalMonthlyPay() {
    BigDecimal sum = sumMonthlyPay();
    return toResponse("TOTAL MONTHLY PAY", sum);
  }

  private BigDecimal sumBasePay() {
    return employees.stream()
                    .map(Employee::getBasePay)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal sumMonthlyPay() {
    return employees.stream()
                    .map(Employee::getMonthlyPay)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private SalaryResponse toResponse(String name, BigDecimal pay) {
    return new SalaryResponse(name, pay);
  }
}
