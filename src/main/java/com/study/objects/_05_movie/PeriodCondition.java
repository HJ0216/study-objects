package com.study.objects._05_movie;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PeriodCondition implements DiscountCondition {

  private final DiscountConditionType type;
  private final DayOfWeek dayOfWeek;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private int sequence;

  @Override
  public boolean isSatisfiedBy(Screening screening) {
    LocalTime whenScreened = screening.getWhenScreened().toLocalTime();

    return screening.getWhenScreened().getDayOfWeek().equals(dayOfWeek) &&
        startTime.compareTo(whenScreened) <= 0 &&
        endTime.compareTo(whenScreened) >= 0;
  }
}
