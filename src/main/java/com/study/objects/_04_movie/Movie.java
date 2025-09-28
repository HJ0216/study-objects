package com.study.objects._04_movie;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

  public Money calculateAmountDiscountedFee(){
    if (type != MovieType.AMOUNT_DISCOUNT) {
      throw new IllegalArgumentException();
    }

    return fee.minus(discountAmount);
  }

  public Money calculatePercentDiscountedFee(){
    if (type != MovieType.PERCENT_DISCOUNT) {
      throw new IllegalArgumentException();
    }

    return fee.minus(fee.times(discountPercent));
  }

  public Money calculateNoneDiscountedFee(){
    if (type != MovieType.NONE_DISCOUNT) {
      throw new IllegalArgumentException();
    }

    return fee;
  }

  public boolean isDiscountable(LocalDateTime whenScreened, int sequence){
    for (DiscountCondition condition : conditions) {
      if (condition.getType() == DiscountConditionType.PERIOD) {
        if (condition.isDiscountable(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())) {
          return true;
        }
      } else {
        if (condition.isDiscountable(sequence)) {
          return true;
        }
      }
    }

    return false;
  }

}
