<!-- 테스트 -->
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="join-body-container">
    <div class="join-container" >
      <div class="login-box">
        <div class="logo-box">
          <img src="@/assets/images/logo.png" alt="" style="width: 90%; height: auto;" />
        </div>
        <h3 style="color: #FFB937	; margin-bottom: 40px;">가입하기</h3>

        <div class="nickname-confirm">
          <span class="nickname-word">닉네임</span>
          <span><button class="nickname-confirm-box">중복확인</button></span>
        </div>

        <div class="input-with-label" style="margin-top:15px">
          <input
            v-model="nickName"
            autocapitalize="off"
            v-bind:class="{
              error: error.nickName,
              complete: !error.nickName && nickName.length !== 0,
            }"
            id="nickname"
            placeholder="닉네임을 입력하세요."
            type="text"
          />
          <label for="nickname"></label>
          <div iv class="error-text" v-if="error.nickName">{{ error.nickName }}</div>
        </div>

        <button
          class="join-btn"
          @click="signUp"
          :disabled="!isSubmit"
          :class="{ disabled: !isSubmit }"
        >
          START
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import UserApi from '../../api/UserApi';

export default {
  data: () => {
    return {
      nickName: '',
      isTerm: false,
      isLoading: false,
      error: {
        nickName: false,
      },
      isSubmit: false,
      termPopup: false,
      code: '',
      userInfo: {
        uid: '',
        nickname: '',
      },
    };
  },
  created() {
    this.create();
  },
  watch: {
    nickName: function(v) {
      this.checkForm();
    },
  },
  methods: {
    create() {
      this.code = this.$route.query.code;

      UserApi.requestkakaoLogin(
        this.code,
        (res) => {
          console.log(res);
          this.userInfo.uid = res;
          console.log(this.userInfo);

          if (this.userInfo.uid == undefined) {
            alert('잘못된 접근 입니다.');

            this.$router.push('/');
          }

          // this.isPresentUser();
        },
        (error) => {
        }
      );
    },
    checkForm() {
      // nickname 중복 확인 필요
      if (this.nickName.length == 0) this.error.nickName = '닉네임은 한 글자 이상이어야 합니다.';
      else this.error.nickName = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    signUp() {
      if (this.isSubmit) {
        let data = {
          email: this.email,
          password: this.password,
          nickname: this.nickName,
        };

        console.log(data);

        this.isSubmit = false;

        UserApi.requestSignUp(
          data,
          (res) => {
            this.isSubmit = true;
            // feed/main으로 가야함
            this.$router.push('/user/joinSC');
          },
          (error) => {
            if (error) this.$router.push('/error');
            this.isSubmit = true;
          }
        );
      } else {
        Object.values(this.error).map((v) => {
          if (v) alert(v);
        });
      }
    },
  },
};
</script>

<style>
    @import "../../components/css/user/join.css";
</style>
