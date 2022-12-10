# MyBook
- 교보문고를 모티브로 한 온라인 서점 서버 만들기 프로젝트 입니다.
- 백엔드에 집중하기 위해 프론트엔드 대신 Kakao Oven으로 만든 UI로 대신합니다.

## 사용 기술
- Spring Boot 2.7.6
- JAVA 11
- MyBatis 2.3.0
- MySQL
- Quartz
- Spring Security(Crypto)
- IntelliJ
- Postman

## Application UI

https://github.com/psh94/MyBook/wiki/Application-UI

예시)![예시](https://user-images.githubusercontent.com/84213252/206840722-7af81627-9535-4c07-bea0-cd58f52fa891.PNG)



## Use Case
https://github.com/psh94/MyBook/wiki/Use-Case


## ERD
![erd](https://user-images.githubusercontent.com/84213252/206838089-aa367fc7-4124-42fb-a937-82575c940064.PNG)


## 비즈니스 로직
Member
- 회원가입
1. 회원가입 폼에서 값들을 받습니다.
2. 아이디 중복 체크를 진행합니다.
3. 아이디 중복 체크 통과 시, 비밀번호를 암호화합니다.
4. 암호화 한 비밀번호를 기존의 입력한 비밀번호와 바꿉니다.
5. 비밀번호 암호화 된 비밀번호와 다른 값들을 DB에 저장합니다.

- 로그인
1. 로그인 폼에서 값들을 받습니다.
2. DB에서 memberId와 password 값을 비교하여 회원을 찾습니다.
3. 찾은 회원을 반환합니다.

- 회원 조회
1. 로그인 당시 회원의 memberId와 DB에서 일치하는 정보를 찾습니다.
2. 해당 정보를 반환합니다.

- 회원 정보 수정
1. 회원 정보 수정 폼에서 값들을 입력합니다.
2. 
