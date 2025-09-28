package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DiscountPolicyTest {

  @Nested
  @DisplayName("calculateDiscountAmount()는")
  class Describe_calculateDiscountAmount {

    private DiscountPolicy policy;
    private Movie movie;


    @Nested
    @DisplayName("할인 정책이 없으면")
    class Context_with_NonDiscountPolicy {

      @BeforeEach
      void setUp() {
        policy = new NonDiscountPolicy();
        movie = new Movie("Test Movie", Duration.ofMinutes(95), Money.wons(8_000), policy);
      }

      @Test
      @DisplayName("항상 0원을 반환한다")
      void it_always_returns_zero() {
        // given
        Screening screening = new Screening(movie, 1, LocalDateTime.now());

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(0, amount);
      }
    }

    @Nested
    @DisplayName("금액 할인 정책이 있으면")
    class Context_with_amount_discount_policy {

      @BeforeEach
      void setUp() {
        policy = new AmountDiscountPolicy(
            Money.wons(800),
            new SequenceCondition(1),
            new SequenceCondition(10),
            new PeriodCondition(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 0),
                LocalTime.of(11, 59)),
            new PeriodCondition(
                DayOfWeek.THURSDAY,
                LocalTime.of(10, 0),
                LocalTime.of(20, 59)
            )
        );
        movie = new Movie("Test Movie", Duration.ofMinutes(225), Money.wons(10_000), policy);
      }

      @Test
      @DisplayName("1회차 상영에 할인 금액 800원을 반환한다")
      void it_returns_discounted_amount_for_first_sequence() {
        // given
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(2025, 9, 28, 10, 00));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("10회차 상영에 할인 금액 800원을 반환한다")
      void it_returns_discount_amount_for_10th_sequence() {
        // given
        Screening screening = new Screening(movie, 10,
            LocalDateTime.of(2025, 9, 28, 10, 00));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("월요일 10시 상영에 할인 금액 800원을 반환한다")
      void it_returns_discount_amount_for_monday_10_00() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(2025, 9, 29, 10, 00));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("월요일 11시 59분 상영에 할인 금액 800원을 반환한다")
      void it_returns_discount_amount_for_monday_11_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 29, 11, 59));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("목요일 10시 상영에 할인 금액 800을 반환한다")
      void it_returns_discount_amount_for_thursday_10_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 10, 00));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("목요일 20시 59분 상영에 할인 금액 800원을 반환한다")
      void it_returns_discount_amount_for_thursday_20_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 20, 59));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(800, amount);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 0원을 반환한다")
      void it_returns_0_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(2025, 9, 28, 5, 0));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(0, amount);
      }
    }

    @Nested
    @DisplayName("비율 할인 정책이 있으면")
    class Context_with_percent_discount_policy {

      @BeforeEach
      void setUp() {
        policy = new PercentDiscountPolicy(
            0.1,
            new PeriodCondition(
                DayOfWeek.TUESDAY,
                LocalTime.of(14, 0),
                LocalTime.of(16, 59)),
            new SequenceCondition(2),
            new PeriodCondition(
                DayOfWeek.THURSDAY,
                LocalTime.of(10, 0),
                LocalTime.of(13, 59)
            )
        );
        movie = new Movie("Test Movie", Duration.ofMinutes(185), Money.wons(15_000), policy);
      }

      @Test
      @DisplayName("화요일 14시 상영에 영화 요금의 10%를 할인 금액으로 반환한다")
      void it_returns_10_percent_of_movie_fee_for_tuesday_14_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 30, 14, 0));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(1_500, amount);
      }

      @Test
      @DisplayName("화요일 16시 59분 상영에 영화 요금의 10%를 할인 금액으로 반환한다")
      void it_returns_10_percent_of_movie_fee_for_tuesday_16_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 30, 16, 59));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(1_500, amount);
      }

      @Test
      @DisplayName("2회차 상영에 영화 요금의 10%를 할인 금액으로 반환한다")
      void it_returns_10_percent_of_movie_fee_for_second_sequence() {
        // given
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(2025, 9, 28, 10, 0));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(1_500, amount);
      }

      @Test
      @DisplayName("목요일 10시 상영에 영화 요금의 10%를 할인 금액으로 반환한다")
      void it_returns_10_percent_of_movie_fee_for_thursday_10_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 10, 0));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(1_500, amount);
      }

      @Test
      @DisplayName("목요일 13시 59분 상영에 영화 요금의 10%를 할인 금액으로 반환한다")
      void it_returns_10_percent_of_movie_fee_for_thursday_13_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 13, 59));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(1_500, amount);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 0원을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(2025, 9, 28, 5, 0));

        // when
        Money amount = policy.calculateDiscountAmount(screening);

        // then
        assertAmountEquals(0, amount);
      }
    }
  }

  private void assertAmountEquals(long expected, Money actual) {
    assertEquals(0, actual.getAmount().compareTo(BigDecimal.valueOf(expected)));
  }

}