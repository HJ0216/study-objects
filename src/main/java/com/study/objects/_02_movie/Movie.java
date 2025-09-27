package com.study.objects._02_movie;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class Movie {

  private String title;
  private Duration runningTime;
  private Money fee;
  private DiscountPolicy discountPolicy;

  public void changeDiscountPolicy(DiscountPolicy discountPolicy){
    this.discountPolicy = discountPolicy;
  }

  public Money getFee() {
    return fee;
  }

  public Money calculateMovieFee(Screening screening) {
    if (discountPolicy == null) {
      return fee;
    }

    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }

  public Movie getAvatar() {
    return new Movie(
        "Avatar",
        Duration.ofMinutes(120),
        Money.wons(10_000),
        new AmountDiscountPolicy(
            Money.wons(800),
            new SequenceCondition(1),
            new SequenceCondition(10),
            new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))));
  }

  public Movie getTitanic() {
    return new Movie(
        "Titanic",
        Duration.ofMinutes(180),
        Money.wons(11_000),
        new PercentDiscountPolicy(
            0.1,
            new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
            new SequenceCondition(2),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))));
  }

  public Movie getStarWars(){
    return new Movie(
        "StarWars",
        Duration.ofMinutes(210),
        Money.wons(10_000),
        new NonDiscountPolicy()
    );
  }
}
