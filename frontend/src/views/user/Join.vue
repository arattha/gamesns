<!-- 테스트 -->
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="container">
    <div class="login-box">
      <div class="logo-box">
        <img src="@/assets/images/logo.png" alt="">
      </div>
      <h3 style="color: #FFB937	; margin-bottom: 40px;">가입하기</h3>

      <div class = "nickname-confirm">
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
          <!-- <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
      </label>

      <span @click="termPopup = true">약관보기</span> -->
    </div>
  </div>
</template>

<script>
import UserApi from '../../api/UserApi';

// import PV from 'password-validator';
// import * as EmailValidator from 'email-validator';

export default {
  data: () => {
    return {
      // email: '',
      // password: '',
      // passwordConfirm: '',
      nickName: '',
      isTerm: false,
      isLoading: false,
      // passwordSchema: new PV(),
      error: {
        // email: false,
        // password: false,
        nickName: false,
        // passwordConfirm: false,
        // term: false,
      },
      isSubmit: false,
      // passwordType: 'password',
      // passwordConfirmType: 'password',
      termPopup: false,

    };
  },
  watch: {
    nickName: function (v) {
      this.checkForm();
    // },
    // email: function (v) {
    //   this.checkForm();
    // },
    // password: function (v) {
    //   this.checkForm();
    // },
    // isTerm: function (v) {
    //   this.checkForm();
    },
  },
  methods: {
    checkForm() {
      // nickname 확인
      if (this.nickName.length == 0) this.error.nickName = '닉네임은 한 글자 이상이어야 합니다.';
      else this.error.nickName = false;
      

      // // email 확인
      // if (this.email.length >= 0 && !EmailValidator.validate(this.email))
      //   this.error.email = '이메일 형식이 아닙니다.';
      // else this.error.email = false;

      // // 비번 확인
      // if (this.password.length >= 0 && !this.passwordSchema.validate(this.password))
      //   this.error.password = '영문,숫자 포함 8 자리이상이어야 합니다.';
      // else this.error.password = false;

      // // 비번confirm 확인
      // if (this.passwordConfirm.length >= 0 && !this.passwordSchema.validate(this.password))
      //   this.error.passwordConfirm = '영문,숫자 포함 8 자리이상이어야 합니다.';
      // else if (this.passwordConfirm != this.password)
      //   this.error.passwordConfirm = '비번이 다릅니다.';
      // else this.error.passwordConfirm = false;

      // // term 확인
      // if (!this.isTerm) this.error.term = '약관을 동의해주세요.';
      // else this.error.term = false;

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
  .join-btn {
    background: #1565c0;
    color: white;
    height: 50px;
    text-align: center;
    line-height: 50px;
    font-weight: f600;
    position: relative;
    left: 0px;
    top: 25px;
    width: 100%;
    cursor: pointer;
    transform: rotate(.1deg);
    /* &.disabled {
        background: $grey;
        cursor: default; */
  }
  
  .disabled {
    background: grey;
    cursor: default;
  }

  #nickname {
    padding:0px;
    text-align: center;
    font-size: 18px;
  }
  
  .nickname-confirm {
    display: flex;
    justify-content:space-between;
    margin-bottom: 20px;
    align-items: center;
  }

  .nickname-word {
    font-size: 24px;
  }

  .nickname-confirm-box{
    background-color: #c67c00;
    color:white;
    padding: 10px;
  }

</style>