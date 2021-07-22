<template>
  <div class="container" >
    <div class="login-box" style="padding-top: 70px;">
      <div class="logo-box">
        <img src="@/assets/images/logo.png" alt="" style="width: 90%; height: auto;">
      </div>

      <div>
        <h3 style="color: #FFB937	; margin-bottom: 30px;
        ">간편 로그인</h3>
      </div>
      <!-- <div class=" naver_login">
        <img src="@/assets/images/naver.png" class="login_bars" alt="">
      </div>
      <div class="label-with-input kakao_login">
        <img src="@/assets/images/kakao.png" class="login_bars" alt="">
      </div> -->
      <div class="login-btn">

        <KakaoLogin
        api-key="4166630b8a0719cc4a5abf3edd87e8fd"
        :on-success=onSuccess
        :on-failure=onFailure
        />
      </div>
    </div>
  </div>
</template>

<script>
import KakaoLogin from 'vue-kakao-login'
import UserApi from '../../api/UserApi';

export default {
  name: 'App',
  components: {
    KakaoLogin
  },
  methods: {
    onSuccess: function(e) {

        let data = {
          access_token: e.access_token
        };

        UserApi.requestkakaoLogin(data,
        (res) => {
          console.log("success");
          this.$router.push('/feed/main');
        },
        (error) => {
          console.log("need signup");
          this.$router.push('/user/join');
        }
        )
    },
    onFailure: function(e) {
      console.log(e)
      console.log("failure")
    },
    logout(e) {
        let data = {
          access_token: e.access_token
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
  max-height: 500px;
  min-height: 500px;
  margin-top: 180px;
  margin-bottom: 200px;
  background-color: white;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 20%);
  position: relative;
  float: none;
  display: flex;
  flex-wrap: wrap;
  text-align: center;
}

.login-box {
  /* background-color: black; */
  margin: 0 auto;
  justify-content: center;
  /* border: 1px solid black; */
  text-align: center;
  position: relative;
}

.login-btn {
  text-align: center;
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