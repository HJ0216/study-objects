package com.study.objects._03_movie;

import java.math.BigDecimal;

public class ReservationAgency {

  public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
    Movie movie = screening.getMovie();

    boolean discountable = false;
    for (DiscountCondition condition : movie.getConditions()) {
      if (condition.getType() == DiscountConditionType.PERIOD) {
        discountable =
            screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0
                &&
                condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
      } else {
        discountable = condition.getSequence() == screening.getSequence();
      }

      if (discountable) {
        break;
      }
    }

    Money fee;
    if (discountable) {
      Money discountAmount = Money.ZERO;
      switch (movie.getType()) {
        case NONE_DISCOUNT -> discountAmount = Money.ZERO;
        case AMOUNT_DISCOUNT -> discountAmount = movie.getDiscountAmount();
        case PERCENT_DISCOUNT -> discountAmount = movie.getFee().times(movie.getDiscountPercent());
      }

      fee = movie.getFee().minus(discountAmount);
    } else {
      fee = movie.getFee();
    }

    return new Reservation(customer, screening, fee, audienceCount);
  }
}
