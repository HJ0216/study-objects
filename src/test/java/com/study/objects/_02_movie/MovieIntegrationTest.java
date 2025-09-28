package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovieIntegrationTest {

  @Nested
  @DisplayName("calculateMovieFee()는")
  class Describe_calculateMovieFee {

    private Movie movie;

    @Nested
    @DisplayName("할인 정책이 없으면")
    class Context_without_discount_policy {

      @BeforeEach
      void setUp() {
        movie = new Movie(
            "StarWars",
            Duration.ofMinutes(210),
            Money.wons(10_000),
            new NonDiscountPolicy()
        );
      }

      @Test
      @DisplayName("기본 요금을 반환한다")
      void it_returns_base_fee() {
        // given
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));


        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000, fee);
      }
    }

    @Nested
    @DisplayName("금액 할인 정책이 있으면")
    class Context_with_amount_discount_policy {

      @BeforeEach
      void setUp() {
        movie = new Movie(
            "Avatar",
            Duration.ofMinutes(120),
            Money.wons(10_000),
            new AmountDiscountPolicy(
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
            )
        );
      }

      @Test
      @DisplayName("1회차 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_first_sequence() {
        // given
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("10회차 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_10th_sequence() {
        // given
        Screening screening = new Screening(movie, 10,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10,  0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("월요일 10시 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_monday_10_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(10, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("월요일 11시 59분 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_monday_11_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(11, 59)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("목요일 10시 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_10_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(10, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("목요일 20시 59분 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_20_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(20, 59)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(15, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000, fee);
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Screening screening = new Screening(movie, 10,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(12, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }
    }

    @Nested
    @DisplayName("비율 할인 정책이 있으면")
    class Context_with_percent_discount_policy {

      @BeforeEach
      void setUp() {
        movie = new Movie(
            "Titanic",
            Duration.ofMinutes(180),
            Money.wons(11_000),
            new PercentDiscountPolicy(
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
            )
        );
      }

      @Test
      @DisplayName("화요일 14시 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_tuesday_14_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.TUESDAY), LocalTime.of(14, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("화요일 16시 59분 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_tuesday_16_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.TUESDAY), LocalTime.of(16, 59)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("2회차 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_second_sequence() {
        // given
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("목요일 10시 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_10_00() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(10, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("목요일 13시 59분 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_13_59() {
        // given
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(13, 59)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(15, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(11_000, fee);
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.TUESDAY), LocalTime.of(12, 0)));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }
    }

  }

  @Nested
  @DisplayName("changeDiscountPolicy()는")
  class Describe_changeDiscountPolicy {

    @Nested
    @DisplayName("유효한 할인 정책이라면")
    class Context_with_valid_discount_policy {

      @Test
      @DisplayName("할인 정책을 변경한다")
      void it_changes_discount_policy() {
        // given
        Movie movie = new Movie(
            "StarWars",
            Duration.ofMinutes(210),
            Money.wons(10_000),
            new NonDiscountPolicy()
        );
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));

        // when
        movie.changeDiscountPolicy(
            new AmountDiscountPolicy(Money.wons(1_000), new SequenceCondition(1)));
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_000, fee);
      }

      @Test
      @DisplayName("할인 정책을 제거한다")
      void it_removes_discount_policy() {
        // given
        Movie movie = new Movie(
            "Titanic",
            Duration.ofMinutes(180),
            Money.wons(11_000),
            new PercentDiscountPolicy(
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
            )
        );
        Screening screening = new Screening(movie, 2, LocalDateTime.now());

        // when
        movie.changeDiscountPolicy(null);
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(11_000, fee);
      }
    }
  }

  private void assertAmountEquals(long expected, Money actual) {
    assertEquals(0, actual.getAmount().compareTo(BigDecimal.valueOf(expected)));
  }

}