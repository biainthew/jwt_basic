# Spring Boot JWT Security

Spring Boot 기반의 JWT(JSON Web Token) 인증/인가 시스템입니다. 세션 없이 Stateless 방식으로 동작하며, 역할 기반 접근 제어(RBAC)를 지원합니다.

## 기술 스택

- **Java 17**
- **Spring Boot 3.5.11**
- **Spring Security** - 인증/인가 프레임워크
- **Spring Data JPA** - ORM 및 데이터 액세스
- **MySQL** - 데이터베이스
- **Auth0 java-jwt 4.5.0** - JWT 토큰 생성 및 검증
- **Lombok** - 보일러플레이트 코드 제거
- **BCrypt** - 비밀번호 암호화

## 프로젝트 구조

```
src/main/java/com/cos/security2/
├── Security2Application.java              # 메인 애플리케이션
├── controller/
│   └── RestApiController.java             # REST API 엔드포인트
├── model/
│   └── User.java                          # 사용자 엔티티
├── repository/
│   └── UserRepository.java               # 사용자 데이터 액세스
├── config/
│   ├── SecurityConfig.java               # Spring Security 설정
│   ├── CorsConfig.java                   # CORS 설정
│   ├── FilterConfig.java                 # 커스텀 필터 등록
│   ├── auth/
│   │   ├── PrincipalDetails.java         # UserDetails 구현체
│   │   └── PrincipalDetailsService.java  # UserDetailsService 구현체
│   └── jwt/
│       ├── JwtProperties.java            # JWT 설정 상수
│       ├── JwtAuthenticationFilter.java  # 로그인 필터 (토큰 생성)
│       └── JwtAuthorizationFilter.java   # 인가 필터 (토큰 검증)
└── filter/
    ├── MyFilter1.java                    # 커스텀 서블릿 필터
    └── MyFilter2.java                    # 커스텀 서블릿 필터
```

## API 엔드포인트

| Method | URL | 권한 | 설명 |
|--------|-----|------|------|
| POST | `/join` | 없음 | 회원가입 |
| POST | `/login` | 없음 | 로그인 (JWT 토큰 발급) |
| GET | `/home` | 없음 | 홈 |
| GET | `/api/v1/user` | USER, MANAGER, ADMIN | 사용자 전용 |
| GET | `/api/v1/manager` | MANAGER, ADMIN | 매니저 전용 |
| GET | `/api/v1/admin` | ADMIN | 관리자 전용 |

## 인증 흐름

### 회원가입

```
POST /join
Content-Type: application/json

{
  "username": "user1",
  "password": "1234"
}
```

- 비밀번호는 BCrypt로 암호화되어 저장
- 기본 역할: `ROLE_USER`

### 로그인 및 토큰 발급

```
POST /login
Content-Type: application/json

{
  "username": "user1",
  "password": "1234"
}
```

- 인증 성공 시 응답 헤더에 JWT 토큰 반환: `Authorization: Bearer <token>`
- 토큰 만료 시간: 10분
- 서명 알고리즘: HMAC512

### 인가된 API 호출

```
GET /api/v1/user
Authorization: Bearer <token>
```

- 요청 헤더의 JWT 토큰을 검증하고 사용자 정보를 추출
- 역할에 따라 접근 가능한 엔드포인트가 결정됨

## 설정

### 데이터베이스

`src/main/resources/application.yml`에서 설정:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/security?serverTimezone=Asia/Seoul
    username: ...
    password: ...
```

### 서버

- 포트: `8000`
- 문자 인코딩: UTF-8

## 실행 방법

1. MySQL에 `security` 데이터베이스 생성
   ```sql
   CREATE DATABASE security;
   ```

2. `application.yml`에서 DB 접속 정보 수정

3. 애플리케이션 실행
   ```bash
   ./mvnw spring-boot:run
   ```