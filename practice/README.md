# test-code
How to write test code

## subject
- TDD 로 진행하는 간단한 카페 키오스크 서비스

## 요구사항
- 주문 목록에 음료 추가 / 삭제 기능
- 주문 목록 전체 지우기
- 주문 목록 총 금액 계산하기
- 주문 생성하기
- 한 종류의 음료 여러 잔을 한 번에 담는 기능
- 가게 운영 시간 (10:00 - 22:00) 외에는 주문을 생성할 수 없다.
- 키오스크 주문을 위한 상품 후보 리스트 조회하기
- 상품의 판매 상태는 판매중, 판매보류, 판매중지, 판매중, 판매보류인 상태를 가진다.
- 상품 객체는 id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격을 가진다.
- 상품 번호 리스트를 받아 주문 생성하기
- 주문 객체는 주문 상태, 주문 등록 시간을 가진다.
- 주문의 총 금액을 계산할 수 있어야 한다.
- 주문 생성 시 재고 확인 및 개수 차감 후 생성하기
- 재고는 상품번호를 가진다.
- 재고와 관련 있는 상품 타입은 병 음료, 베이커리이다.
- 관리자 페이지에서 신규 상품을 등록할 수 있다.
- 신규 상품 등록시 상품명, 상품 타입, 판매 상태, 가격 등을 입력받는다.
- 지정된 날짜의 총 매출 금액을 계산하여 메일로 보내는 기능

## API 명세
- Spring REST Docs 이용
- localhost:8080/docs/index.html

## project
- cafekiosk

## dependency
- Spring Boot
- Spring Web
- Spring Data Jpa
- Spring Validation
- h2
- Spring Boot Test
  - AssertJ
  - JUnit5
  - Mockito
- Spring REST Docs
  - asciidocs
  - mockmvc
