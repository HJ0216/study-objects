package com.study.objects._02_movie;

import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PeriodCondition implements DiscountCondition {

  private final DayOfWeek dayOfWeek;
  private final LocalTime startTime;
  private final LocalTime endTime;

  @Override
  public boolean isSatisfiedBy(Screening screening) {
    return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
        startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
        endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;

  }
}
