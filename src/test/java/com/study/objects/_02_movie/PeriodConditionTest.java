package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PeriodConditionTest {

  @Nested
  @DisplayName("isSatisfiedBy()는")
  class Describe_isSatisfiedBy {

    private static final PeriodCondition MONDAY_10_TO_12_CONDITION =
        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

    private static final Movie TEST_MOVIE = new Movie(
        "test movie",
        Duration.ofMinutes(75),
        Money.wons(10_000),
        new NonDiscountPolicy());

    @Nested
    @DisplayName("요일과 시간이 조건에 맞을 때")
    class Context_matches {

      @Test
      @DisplayName("true를 반환한다")
      void it_returns_true() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(11, 0)));

        // when & then
        assertTrue(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }

    @Nested
    @DisplayName("요일이 다를 때")
    class Context_different_day {

      @Test
      @DisplayName("false를 반환한다")
      void it_returns_false() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.TUESDAY), LocalTime.of(11, 0)));

        // when & then
        assertFalse(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }

    @Nested
    @DisplayName("시간이 시작 시간보다 이른 경우")
    class Context_before_start {

      @Test
      @DisplayName("false를 반환한다")
      void it_returns_false() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(9, 59)));

        // when & then
        assertFalse(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }

    @Nested
    @DisplayName("시간이 종료 시간보다 늦은 경우")
    class Context_after_end {

      @Test
      @DisplayName("false를 반환한다")
      void it_returns_false() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(12, 1)));

        // when, then
        assertFalse(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }

    @Nested
    @DisplayName("시간이 시작 시간과 정확히 같을 때")
    class Context_equals_start {

      @Test
      @DisplayName("true를 반환한다")
      void it_returns_true() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(10, 0)));

        // when, then
        assertTrue(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }

    @Nested
    @DisplayName("시간이 종료 시간과 정확히 같을 때")
    class Context_equals_end {

      @Test
      @DisplayName("true를 반환한다")
      void it_returns_true() {
        // given
        Screening screening = new Screening(
            TEST_MOVIE, 1, LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), LocalTime.of(12, 0)));

        // when, then
        assertTrue(MONDAY_10_TO_12_CONDITION.isSatisfiedBy(screening));
      }
    }
  }
}