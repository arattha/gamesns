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
          <span><button class="nickname-confirm-box" @click="dupCheck">중복확인</button></span>
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
      isDup: false,
      error: {
        nickName: false,
      },
      isSubmit: false,
      code: '',
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

      console.log('ggg');
      console.log(this.code);

      UserApi.requestkakaoLogin(
        this.code,
        (res) => {
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
      if (this.isSubmit && this.isDup) {
        let data = {
          uid: this.uid,
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
    dupCheck() {
      if(!this.error.nickName) {
        let data = {
          uid: this.uid,
          nickname: this.nickName,
        };

        UserApi.requestSignUp(
          data,
          (res) => {
            this.isDup = true;
            // feed/main으로 가야함
            alert("사용 가능한 아이디입니다.");
          },
          (error) => {
            if (error == 403) alert("중복된 아이디입니다.");
            else alert("오류!");
          }
        );

      }
    }
  },
};
</script>

<style>
    @import "../../components/css/user/join.css";
</style>
