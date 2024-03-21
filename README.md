## Thyemeleaf 와 JPA 로 만드는 쇼핑몰 프로젝트
* 스프링 부트 쇼핑몰 프로젝트 with JPA ([도서](https://cacu.kr/IG0jyc) / [Github](https://github.com/roadbook2/shop))

### 프로젝트 환경
* SpringBoot 3.2
* Java 17

### 책과 다른점
* Docker 기반
* Jacoco 활용으로 테스트 커버리지 확인
* builder 패턴 사용
* application.yml 사용

---

- git push 할 때 자동으로 테스트 커버리지 확인하도록 설정
  - `.git/hooks` 하위에 `pre-push` 파일 생성
  - ```
    #!/bin/bash
    
    ./gradlew test jacocoTestReport jacocoTestCoverageVerification
    ```
  - 파일 권한 변경 : `chmod +x .git/hooks/pre-push`
  