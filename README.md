# product-order-program
상품 주문 프로그램

## 프로젝트의 구조
관심사 분리 및 확장 용이성을 위해 layered architecture로 설계했고, 크게 3가지 레이어로 나뉩니다.
* Presentation Layer
  * 주문 프로그램의 진행 및 사용자의 요청을 받고 응답을 보여주는 역할을 가지고 있습니다.
  * 요청에 따른 컨버팅, 검증 등을 수행하고 Business Layer에 사용자의 요청 값을 전달하는 역할을 가지고 있습니다.  
  * controller, view 패키지가 Presentation Layer에 속합니다.
* Business Layer
  * 비즈니스 로직을 처리하는 역할을 가지고 있습니다.
  * 다른 레이어들 간에 통신하기 위한 인터페이스를 제공합니다.
  * 테스트 코드 작성이 용이하고, 도메인 객체의 응집도를 높이기 위해 주요 비즈니스 로직은 도메인 객체에서 처리하게 했습니다.
  * domain, service 패키지가 Business Layer에 속합니다.
* Data Access Layer
  * 데이터 저장소에 직접 접근해 필요한 데이터를 읽고 쓰는 Layer입니다.
  * repository 패키지가 Data Access Layer에 속합니다.
* ContainerConfig.kt 파일은 위 3가지 레이어에 필요한 객체를 생성하고 의존관계를 연결해 주는 역할을 담당합니다.

## 구현 방향
* 주문시 상품 재고를 thread-safe하게 처리하기 위해 ConCurrentHashMap를 사용했습니다.
* 주문시 주문 수량만큼 재고가 감소하게 처리했습니다. (요구사항에서 정의되지 않았지만 추가한 부분)
* Business Layer 위주로 테스트 코드를 작성했습니다.

## 구현 사항
* 상품은 고유의 상품번호와 상품명, 판매가격, 재고수량 정보를 가지고 있습니다.
* 한 번에 여러개의 상품을 같이 주문할 수 있습니다
* 상품번호, 주문수량은 반복적으로 입력 받을 수 있습니다.
  * 단, 한번 결제가 완료되고 다음 주문에선 이전 결제와 무관하게 주문이 가능합니다.
* 주문은 상품번호, 수량을 입력받습니다.
    * empty 입력 (space + ENTER)이 되었을 경우 해당 건에 대한 주문이 완료되고, 결제하는 것으로 판단합
    니다. 
    * 결제 시 재고 확인을 하여야 하며 재고가 부족할 경우 결제를 시도하면 SoldOutException 이 발생되어
    야 합니다. 
* 주문 금액이 5만원 미만인 경우 배송료 2,500원이 추가됩니다. 
* 주문이 완료되었을 경우 주문 내역과 주문 금액, 결제 금액 (배송비 포함) 을 화면에 display합니다.
* 'q' 또는 'quit' 을 입력하면 프로그램이 종료되어야 합니다. 
* Test 에서는 반드시 multi thread 요청으로 SoldOutException이 정상 동작하는지 확인하는 단위테스트가 존재합니다.
* 상품의 데이터는 csv 파일을 불러서 메모리에 저장해 사용합니다.

## 사용 기술
- Kotlin (JDK 17)
- Gradle
- Junit5
- Kotest
- Assertj
