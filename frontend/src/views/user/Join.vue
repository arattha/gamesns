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
          <img src="@/assets/images/logo2.png" alt="" style="width: 90%; height: auto;" />
        </div>
        <h5 style="margin-bottom: 40px; font-family: 'Nanum Gothic', sans-serif;">회원가입</h5>

        <div>
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
            class="nickname-input"
          />
          <input
            type="submit"
            class="nickname-input"
            id="nickname-submit"
            value="중복확인"
            @click="dupCheck"
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
    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet" />
    </div>
  </div>
</template>

<script>
import UserApi from '../../api/UserApi';
import { mapActions } from 'vuex';

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
    ...mapActions(['setUid', 'setNickname']),
    create() {
      this.code = this.$route.query.code;

      console.log('ggg');
      console.log(this.code);

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
            // this.accessToken = res.object;
            // console.log(this.accessToken);

            // 기존에 만든 axios create를 이용해야한다!
            // http.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;

            this.setUid(this.uid);
            this.setNickname(this.nickname);
            // this.setAccessToken(this.accessToken);
            // axios.defaults.headers.common["Authorization"] = `Bearer ${this.accessToken}`;

            this.$router.push('/main');
          } else {
            alert('로그인 실패 다시 시도해주세요.');
            this.$router.push('/');
          }
        },
        (error) => {}
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
      this.isSubmit = isSubmit && this.isDup;
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
            this.$router.push('/main');
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
      if (!this.error.nickName) {
        UserApi.requestDupCheck(
          this.nickName,
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
