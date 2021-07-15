# 공통 프로젝트

<br /><br />

## Tool
### 개발
### 1. IntelliJ
### 2. vscode
- 백엔드와 통신으로 axios 사용
### 3. mariaDB

<br />

### 프로젝트 관리
### 1. GitLab
### 2. Jira

<br /><br />

## 기능
### 1. 로그인 기능
- DB 에 저장된 회원 정보로만 로그인 가능
- DB 에 저장된 회원 정보가 아닐 시 **'아이디나 비밀번호가 맞지 않습니다.'** 라는 알림창을 띄움
- get 방식으로 params 형태의 데이터를 전송

### 2. 회원 관리
1. 회원 가입
    - 이메일, 비밀번호를 형식에 맞도록 기입
        - frontend
            - 이메일 : '@', '.'
            - 비밀번호 : 8자리 이상
        - backend
            - 이메일 : 중복 검사 => 중복 시 <b>'이미 존재하는 이메일입니다.'</b> 라는 알림창을 띄움
            - 비밀번호 : 영어, 숫자, 문자 포함 검사
    - 가입 필수 항목을 모두 기입 시 '가입 완료' 버튼 활성화
    - 모바일에서 입력 시 이메일 input 의 첫 글자가 대문자가 되는 현상 방지 => **autocapitalize="off"**
    - 회원 가입 완료 시 **'회원가입에 성공했습니다.'** 라는 알림창을 띄움
2. 회원 가입 완료
    - 회원 가입 완료 시 '회원가입 인증 메일이 발송되었습니다. 이메일을 확인해 주세요.' 라는 문구와 함께 재발송, 로그인하기 버튼 을 보여줌
3. 비밀번호 변경 페이지
    - 첫 화면 하단에 비밀번호 변경하기 버튼을 클릭 시 화면 이동
    - 이메일과 비밀번호, 비밀번호 확인을 형식에 맞도록 기입
    - 이메일이 없으면 **'이메일에 맞는 회원 정보가 없습니다.'** 알림창을 띄움
    - 저장 버튼을 클릭하면 기입한 이메일에 맞는 회원정보의 비밀번호를 변경

### 3. 에러
1. Page Not Found
    - Page Not Found 에러 상황 시 보여줄 vue 파일 생성(./views/error/PageNotFound.vue)
    - routes.js 에 /:pathMatch(.*)* 를 path 로 갖는 route 추가
    - url 에 이동할 수 없는 페이지를 입력하면 자동으로 PageNotFound.vue 를 보여줌
2. Error
    - Error 상황 시 보여줄 vue 파일 생성(./views/error/Error.vue)
    - Network 오류 시 Error.vue 를 보여줌

<br /><br />

## DB

```
CREATE TABLE user (
uid bigint NOT NULL AUTO_INCREMENT,
email varchar(128) DEFAULT NULL,
password varchar(128) DEFAULT NULL,
create_date datetime DEFAULT current_timestamp(),
PRIMARY KEY (uid),
UNIQUE KEY user_idx_unique_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

