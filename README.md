# 오브젝트: 코드로 이해하는 객체지향 설계
도서: [오브젝트: 코드로 이해하는 객체지향 설계](https://product.kyobobook.co.kr/detail/S000001766367)

## 목차
* CHAPTER01: 객체, 설계
* CHAPTER02: 객체지향 프로그래밍

### CHAPTER01: 객체, 설계
* 의존성이라는 말 속에는 어떤 객체가 변경될 때 그 객체에게 의존하는 다른 객체도 함께 변경될 수 있다.
  * 필요한 최소한의 의존성만 유지하고 불필요한 의존성을 제거
* 개념적이나 물리적으로 객체 내부의 세부적인 사항을 감주는 것을 캡슐화라고 부른다.
  * 캡슐화의 목적은 변경하기 쉬운 객체를 만드는 것이다.
  * 캡슐화를 통해 객체 내부로의 접근을 제한하면 객체와 객체 사이의 결합도를 낮출 수 있기 때문에 설계를 좀 더 쉽게 변경할 수 있게 된다.

### CHAPTER02: 객체지향 프로그래밍
* 클래스는 공통적인 상태와 행동을 공유하는 객체들을 추상화한 것이다.
* 따라서 클래스의 윤곽을 잡기 위해서는 어떤 객체들이 어던 상태와 행동을 가지는지를 먼저 결정해야 한다.
* 객체를 독립적인 존재가 아니라 기능을 구현하기 위해 협력하는 공동체의 일원으로 봐야 한다.
* 객체들의 모양과 윤곽이 잡히면 공통된 특성과 상태를 가진 객체들을 타입으로 분류하고 이 타입을 기반으로 클래스를 구현하라.
* 클래스를 구현하거나 사용할 때 가장 중요한 것은 클래스의 경계를 구분 짓는 것이다.
* public interface: 외부에서 접근 가능한 부분
* implementation: 오직 내부에서만 접근 가능한 부분
* 의미를 좀 더 명시적이고 분명하게 표현할 수 있다면 객체를 사용해서 해당 개념을 구현하라.
* 그 개념이 비록 하나의 인스턴스 변수만 포함하더라도 개념을 명시적으로 표현하는 것은 전체적인 설계의 명확성과 유연성을 높인다.
* template design pattern: 부모 클래스에 기본적인 알고리즘을 구현하고 중간에 필요한 처리를 자식 클래스에 위임하는 디자인 패턴
```java
public class Movie {

  public Money calculateMovieFee(Screening screening) {
    if (discountPolicy == null) {
      return fee;
    }

    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}
// 기존 할인 정책의 경우에는 할인할 금액을 계산하는 책임이 discountPolicy에 있었지만,
// 할인 정책이 없을 경우, 할인 금액이 0원이라는 사실을 결정하는 책임이 discountPolicy가 아닌 movie에 있음
// → 예외 케이스를 최소화하고, 일관성을 유지해야 함

public class NonDiscountPolicy extends DiscountPolicy{

  @Override
  protected Money getDiscountAmount(Screening screening) {
    return Money.ZERO;
  }
}
```

