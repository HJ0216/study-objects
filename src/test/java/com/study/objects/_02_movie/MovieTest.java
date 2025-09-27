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
        assertEquals(BigDecimal.valueOf(10_000), fee.getAmount());
      }
    }

    @Nested
    @DisplayName("할인 정책이 있으면")
    class Context_with_discount_policy {

      @Test
      @DisplayName("할인 요금을 반환한다")
      void it_returns_discounted_fee() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 10, LocalDateTime.now());

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(BigDecimal.valueOf(9_200), fee.getAmount());
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_applies_single_discount_when_multiple_conditions_met() {
        // given
        Movie movie = Movie.getAvatar();
        Screening screening = new Screening(movie, 10, LocalDateTime.of(2025, 9, 25, 12, 0));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(BigDecimal.valueOf(9_200), fee.getAmount());
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
        Screening screening = new Screening(movie, 1, LocalDateTime.now());

        // when
        movie.changeDiscountPolicy(
            new AmountDiscountPolicy(Money.wons(1_000), new SequenceCondition(1)));
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertEquals(BigDecimal.valueOf(9_000), fee.getAmount());
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
        assertEquals(BigDecimal.valueOf(11_000), fee.getAmount());

      }
    }
  }

}