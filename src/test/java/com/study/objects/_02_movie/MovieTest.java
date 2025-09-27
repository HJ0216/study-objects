package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovieTest {

  @Nested
  @DisplayName("calculateMovieFee()는")
  class Describe_calculateMovieFee {

    @Nested
    @DisplayName("할인 정책이 없으면")
    class Context_without_discount_policy {

      @Test
      @DisplayName("기본 요금을 반환한다")
      void it_returns_base_fee() {
        // given
        Movie movie = Movie.getStarWars();
        Screening screening = new Screening(movie, 1, LocalDateTime.now());

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(10_000)));
      }
    }

    @Nested
    @DisplayName("금액 할인 정책이 있으면")
    class Context_with_amount_discount_policy {

      @Test
      @DisplayName("1회차 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_first_sequence() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 1, LocalDateTime.now());

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("10회차 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_10th_sequence() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 10, LocalDateTime.now());

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("월요일 10시 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_monday_10_00() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 29, 10, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("월요일 11시 59분 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_monday_11_59() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 29, 11, 59));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("월요일 할인 시간대를 벗어나면 기본 요금을 반환한다")
      void it_returns_base_fee_outside_monday_discount_period() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 29, 12, 00));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(10_000)));
      }

      @Test
      @DisplayName("목요일 10시 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_10_00() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 10, 00));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("목요일 20시 59분 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_20_59() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 20, 59));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(2025, 9, 28, 5, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(10_000)));
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 10,
            LocalDateTime.of(2025, 9, 25, 12, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_200)));
      }
    }

    @Nested
    @DisplayName("비율 할인 정책이 있으면")
    class Context_with_percent_discount_policy {

      @Test
      @DisplayName("화요일 14시 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_tuesday_14_00() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 30, 14, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
      }

      @Test
      @DisplayName("화요일 16시 59분 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_tuesday_16_59() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 30, 16, 59));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
      }

      @Test
      @DisplayName("화요일 할인 시간대를 벗어나면 기본 요금을 반환한다")
      void it_returns_base_fee_outside_tuesday_discount_period() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 9, 30, 17, 00));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(11_000)));
      }

      @Test
      @DisplayName("2회차 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_second_sequence() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 2, LocalDateTime.now());

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
      }

      @Test
      @DisplayName("목요일 10시 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_10_00() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 10, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
      }

      @Test
      @DisplayName("목요일 13시 59분 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_thursday_13_59() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 3,
            LocalDateTime.of(2025, 10, 2, 13, 59));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(2025, 9, 28, 5, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(11_000)));
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(2025, 9, 30, 14, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_900)));
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
        Movie movie = Movie.getStarWars();
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(2025 ,9, 28, 10, 0));

        // when
        movie.changeDiscountPolicy(
            new AmountDiscountPolicy(Money.wons(1_000), new SequenceCondition(1)));
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(9_000)));
      }

      @Test
      @DisplayName("할인 정책을 제거한다")
      void it_removes_discount_policy() {
        // given
        Movie movie = Movie.getTitanic();
        Screening screening = new Screening(movie, 2, LocalDateTime.now());

        // when
        movie.changeDiscountPolicy(null);
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(0, fee.getAmount().compareTo(BigDecimal.valueOf(11_000)));
      }
    }
  }
}