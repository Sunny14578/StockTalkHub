# 📊 네이버 주가 데이터를 활용한 주식 종목 토론방 서비스

## 🏠 개발환경

<ul>
    <li> Docker 24.0.7 </li>
    <li> Springboot(2.7.x) / Java 17</li>
    <li> Postgres 16.1 </li>
    <li> Jenkins 2.426 </li>
</ul>


## 🛠️ 주요기능
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


















## 🏛️ ERD & Structure
<details>
    <summary>ERD</summary>

<!-- summary 아래 한칸 공백 두고 내용 삽입 -->
![image](https://github.com/Sunny14578/StockTalkHub/assets/59717550/aa9ac37a-b033-48ba-a787-da2a342013c5)
</details>

<details>
    <summary>Structure</summary>

<!-- summary 아래 한칸 공백 두고 내용 삽입 -->
![image](https://github.com/Sunny14578/StockTalkHub/assets/59717550/aa9ac37a-b033-48ba-a787-da2a342013c5)
</details>

 


## 🚀 도커 컴포즈 명령어
docker-compose up -d


## 개발 요구사항
