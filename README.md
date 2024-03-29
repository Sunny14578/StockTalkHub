# 📊 네이버 주가 데이터를 활용한 주식 종목 토론방 서비스

<br>

## 🔍 목차
* [주요기능](#-주요기능)
* [트러블슈팅](#-트러블슈팅)
* [개발환경](#-개발환경)
* [설계](#-설계)
* [실행방법](#-실행방법)
* [개발 요구사항](#-개발-요구사항)


<br>

## ⚒ 주요기능

<details>
    <summary><strong>종목별 주가 정보 확인</strong></summary> 
    <ul>
      <li>메인지표 ( 시가, 고가, 저가, 종가, 거래량 )</li>
      <li>보조지표 ( 이동평균선, 볼린저 밴드, MACD )</li>
    </ul>
</details>

<details>
    <summary><strong>종목별 토론을 위한 소셜 기능</strong></summary> 
    <ul>
      <li>포스팅 생성, 수정, 삭제 기능</li>
      <li>댓글 생성, 수정, 삭제 기능</li>
      <li>포스팅 및 댓글 좋아요 기능</li>
      <li>팔로워 / 팔로잉 기능 </li>
    </ul>
</details>

<details>
    <summary><strong>유저 기능</strong></summary>
    <ul>
      <li>Spring Security & JWT Token을 활용한 인증처리</li>
      <li>회원가입, 로그인, 회원정보 수정 기능</li>
    </ul>
</details>

<br>

## 💥 트러블슈팅

<br>

## 🏠 개발환경

<ul>
    <li> Docker 24.0.7 </li>
    <li> Springboot(2.7.x) / Java 17</li>
    <li> Postgres 16.1 </li>
    <li> Jenkins 2.426 </li>
</ul>

<br>

## 🏛 설계

<details>
    <summary>아키텍처</summary>

![image](https://github.com/Sunny14578/StockTalkHub/assets/59717550/05a7550d-a854-42a5-8cb5-29c5de1efd98)



    
</details>

<details>
    <summary>ERD</summary>

![image](https://github.com/Sunny14578/StockTalkHub/assets/59717550/aa9ac37a-b033-48ba-a787-da2a342013c5)
</details>


 
<br>

## 🚀 실행방법
docker-compose up -d

<br>

## 📖 개발 요구사항
[요구사항](https://github.com/Sunny14578/StockTalkHub/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%AA%85%EC%84%B8%EC%84%9C)
