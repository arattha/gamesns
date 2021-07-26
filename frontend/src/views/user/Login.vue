<template>
  <div class="body-container">
    <div class="container" style="max-width: 400px;">
      <div class="login-box" style="padding-top: 60px;">
        <div class="logo-box">
          <img src="@/assets/images/logo.png" alt="" style="width: 90%; height: auto;" />
        </div>

        <div>
          <h4
            style="color: #FFB937   ; margin-bottom: 30px;
          "
          >
            간편 로그인
          </h4>
        </div>
        <!-- <div class=" naver_login">
          <img src="@/assets/images/naver.png" class="login_bars" alt="">
        </div>
        <div class="label-with-input kakao_login">
          <img src="@/assets/images/kakao.png" class="login_bars" alt="">
        </div> -->
        <div class="login-btn">
          <img src="@/assets/images/kakao.png" style="width: 300px ; " @click="login" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserApi from '../../api/UserApi';

export default {
  name: 'App',
  components: {},
  methods: {
    login() {
      const CLIENT_ID = process.env.VUE_APP_KAKAO_ID;
      const REDIRECT_URI = process.env.VUE_APP_KAKAO_URI;

      window.location.replace(
        'https://kauth.kakao.com/oauth/authorize?client_id=' +
          CLIENT_ID +
          '&redirect_uri=' +
          REDIRECT_URI +
          '&response_type=code'
      );
    },
    onSuccess: function(e) {
      let data = {
        access_token: e.access_token,
      };

      UserApi.requestkakaoLogin(
        data,
        (res) => {
          console.log('success');
          this.$router.push('/feed/main');
        },
        (error) => {
          console.log('need signup');
          this.$router.push('/user/join');
        }
      );
    },
    onFailure: function(e) {
      console.log(e);
      console.log('failure');
    },
    logout(e) {
      let data = {
        access_token: e.access_token,
      };
      UserApi.logout(
        data,
        (res) => {},
        (error) => {}
      );
    },
  },
};
</script>

<style >
  @import "../../components/css/user/login.css";
</style>
