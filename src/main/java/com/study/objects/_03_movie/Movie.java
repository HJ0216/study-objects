package com.study.objects._03_movie;

import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {

  private String title;
  private Duration runningTime;
  private Money fee;
  private List<DiscountCondition> conditions;

  private MovieType type;
  private Money discountAmount;
  private double discountPercent;

}
