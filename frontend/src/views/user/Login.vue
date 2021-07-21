<template>
  <div class="container">
    <div class="login-box">
      <div class="logo-box">
        <img src="@/assets/images/logo.png" alt="">
      </div>

      <div>
        <h3 style="color: #FFB937	; margin-bottom: 30px;
        ">간편 로그인</h3>
      </div>
      <div class="label-with-input naver_login">
        <img src="@/assets/images/naver.png" class="login_bars" alt="">
      </div>
      <div class="label-with-input kakao_login">
        <img src="@/assets/images/kakao.png" class="login_bars" alt="">
      </div>
      <KakaoLogin
      api-key="4166630b8a0719cc4a5abf3edd87e8fd"
      image="kakao_login_btn_large"
      :on-success=onSuccess
      :on-failure=onFailure
      />
      <button @click="logout">logout</button>
    </div>
  </div>
</template>

<script>
import KakaoLogin from 'vue-kakao-login'
import UserApi from '../../api/UserApi';

let token = "";
let onSuccess = (data) => {
  console.log(data);
  token = data.access_token;
  console.log("success")
}
let onFailure = (data) => {
  console.log(data)
  console.log("failure")
}

 
export default {
  name: 'App',
  components: {
    KakaoLogin
  },
  methods: {
    onSuccess,
    onFailure,
    logout() {
        let data = {
          access_token: token
        };
      UserApi.logout(
        data,
        (res) => {

        },
        (error) => {

        }
      )
    }
  }
}
</script>


<style>

body {
  background-color: #ffe9c6;
}

.login-comment {
  margin-bottom: 20px;
}

.login_bars {
  object-fit: contain;
  width: 80%;
}

.container {
  margin: 0 auto;
  width: 450px;
  max-height: 700px;
  margin-top: 180px;
  margin-bottom: 200px;
  background-color: white;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 20%);
  position: relative;
}

.login-box {
  /* background-color: black; */
  margin: 50px 80px 80px 80px;
  /* border: 1px solid black; */
  text-align: center;
  position: relative;
}

.logo-box {
  margin-bottom: 90px;
}
.easy-login{
  margin-top: 40px;
  text-align: left;
  margin-left: 30px;
}
</style>