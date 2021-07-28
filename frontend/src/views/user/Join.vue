<!-- 테스트 -->
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="join-body-container">
    <div class="join-container">
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
            v-model="nickname"
            autocapitalize="off"
            v-bind:class="{
              error: error.nickname,
              complete: !error.nickname && nickname.length !== 0,
            }"
            id="nickname"
            placeholder="닉네임을 입력하세요."
            type="text"
          />
          <label for="nickname"></label>
          <div iv class="error-text" v-if="error.nickname">{{ error.nickname }}</div>
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
import http from '@/util/http-common';
import { mapActions, mapGetters } from 'vuex';

export default {
  data: () => {
    return {
      nickname: '',
      isDup: false,
      error: {
        nickname: false,
      },
      isSubmit: false,
      code: '',
      uid: '',
      accessToken: '',
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
    ...mapActions(['setAccessToken', 'setUid', 'setNickname']),

    create() {
      this.code = this.$route.query.code;

      UserApi.requestkakaoLogin(
        this.code,
        (res) => {
          console.log(res);
          this.uid = res;
          this.isUser();
        },
        (error) => {
          console.log(error);
          alert('잘못된 접근입니다!');
          this.$router.push('/');
        }
      );
    },

    isUser() {
      UserApi.requestExistUser(
        this.uid,
        (res) => {
          console.log(res);

          if (res.status) {
            this.nickname = res.object;
            this.login();
          }
        },
        (error) => {
          console.log(error);
          alert('잘못된 접근입니다!!');
          this.$router.push('/');
        }
      );
    },

    login() {
      UserApi.requestLogin(
        this.uid,
        (res) => {
          console.log(res);
          if (res.status) {
            this.accessToken = res.object;
            console.log(this.accessToken);

            // 기존에 만든 axios create를 이용해야한다!
            http.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;

            this.setUid(this.uid);
            this.setNickname(this.nickname);
            this.setAccessToken(this.accessToken);
            // axios.defaults.headers.common["Authorization"] = `Bearer ${this.accessToken}`;

            this.$router.push('/main');
          } else {
            alert('로그인 실패 다시 시도해주세요.');
            this.$router.push('/');
          }
        },
        (error) => {
          alert('잘못된 접근입니다?');
          this.$router.push('/');
        }
      );
    },

    checkForm() {
      // nickname 중복 확인 필요
      if (this.nickname.length == 0) this.error.nickname = '닉네임은 한 글자 이상이어야 합니다.';
      else this.error.nickname = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit && this.isDup;
    },
    signUp() {
      if (this.isSubmit && this.isDup) {
        let data = {
          uid: this.uid,
          nickname: this.nickname,
        };

        console.log(data);

        this.isSubmit = false;

        UserApi.requestSignUp(
          data,
          (res) => {
            if (res.status) {
              alert('회원가입 완료!');
              this.isSubmit = true;
              this.login();
            } else {
              alert('회원가입 실패 다시 시도해주세요.');
              // this.$router.push('/');
            }
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
      if (!this.error.nickname) {
        UserApi.requestDupCheck(
          this.nickname,
          () => {
            this.isDup = true;
            this.checkForm();
          },
          () => {}
        );
      }
    },
  },
};
</script>

<style>
@import '../../components/css/user/join.css';
</style>
