package com.study.objects._07_salaries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Salary {

  private final List<Employee> employees = new ArrayList<>(
      Arrays.asList(
          new Employee("Employee1", BigDecimal.valueOf(400), false, BigDecimal.ZERO),
          new Employee("Employee2", BigDecimal.valueOf(300), false, BigDecimal.ZERO),
          new Employee("Employee3", BigDecimal.valueOf(250), false, BigDecimal.ZERO),
          new Employee("PartTime1", BigDecimal.valueOf(1), true, BigDecimal.valueOf(120)),
          new Employee("PartTime2", BigDecimal.valueOf(1), true, BigDecimal.valueOf(120)),
          new Employee("PartTime3", BigDecimal.valueOf(1.5), true, BigDecimal.valueOf(100))
      )
  );

  public SalaryResponse calculatePay(Employee employee) {
    BigDecimal taxRate = getTaxRate(employee.hourly());
    BigDecimal pay = calculatePay(employee, taxRate);
    return toResponse(employee.name(), pay);
  }

  private BigDecimal calculatePay(Employee employee, BigDecimal taxRate) {
    if (isHourly(employee.name())) {
      return calculatePartTimePay(employee, taxRate);
    }

    return calculateEmployeePay(employee, taxRate);
  }

  private boolean isHourly(String name) {
    return employees.stream()
                    .filter(e -> e.name().equals(name))
                    .map(Employee::hourly)
                    .findFirst()
                    .orElse(false);
  }

  public SalaryResponse totalBasePay() {
    BigDecimal sum = sumBasePay();
    return toResponse("TOTAL", sum);
  }

  private BigDecimal sumBasePay() {
    BigDecimal sum = BigDecimal.ZERO;

    for (Employee employee : employees) {
      if (!employee.hourly()) {
        sum = sum.add(employee.basePay());
      }
    }

    return sum;
  }

  private SalaryResponse toResponse(String name, BigDecimal pay) {
    return new SalaryResponse(name, pay);
  }

  private BigDecimal calculateEmployeePay(Employee employee, BigDecimal taxRate) {
    BigDecimal pay = employee.basePay();

    return pay.multiply(BigDecimal.ONE.subtract(taxRate));
  }

  private BigDecimal calculatePartTimePay(Employee employee, BigDecimal taxRate) {
    BigDecimal pay = employee.basePay().multiply(employee.workingHours());

    return pay.multiply(BigDecimal.ONE.subtract(taxRate));
  }

  private BigDecimal getTaxRate(boolean hourly) {
    return hourly ? BigDecimal.ZERO : BigDecimal.TEN;
  }
}
