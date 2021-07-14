
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="user join wrapC">
    <h1>가입하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="nickName" 
        autocapitalize="off"
        v-bind:class="{error : error.nickName, complete:!error.nickName&&nickName.length!==0}"
        id="nickname" 
        placeholder="닉네임을 입력하세요." 
        type="text" />
        <label for="nickname">닉네임</label>
        <div class="error-text" v-if="error.nickName">{{error.nickName}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="email" 
        autocapitalize="off"
        v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
        id="email" 
        placeholder="이메일을 입력하세요."
        type="text" />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input v-model="password"
        v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}" 
        id="password" 
        :type="passwordType" 
        placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}" 
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>  

    <span @click="termPopup=true">약관보기</span>

    <button class="btn-bottom"
      @click="signUp"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
    >가입하기</button>
  </div>
</template>

<script>
import UserApi from "../../api/UserApi";
import PV from "password-validator";
import * as EmailValidator from "email-validator";

export default {
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      isTerm: false,
      isLoading: false,
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false,
    };
  },
  watch:{
    passwordConfirm: function(){
      this.checkForm();
    },
    nickName: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
    password: function(v) {
      this.checkForm();
    },
    isTerm: function(v) {
      this.checkForm();
    }
  },
  methods:{
    checkForm() {

      // nickname 확인
      if (this.nickName.length == 0)
        this.error.nickName = "닉네임을 적어주세요.";
      else this.error.nickName = false;

      // email 확인
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      // 비번 확인
      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      // 비번confirm 확인
      if (
        this.passwordConfirm.length >= 0 &&
        !this.passwordSchema.validate(this.password) 
      )
        this.error.passwordConfirm = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else if (this.passwordConfirm != this.password)
        this.error.passwordConfirm = "비번이 다릅니다.";
      else 
        this.error.passwordConfirm = false;  

      // term 확인
      if(!this.isTerm)
        this.error.term = "약관을 동의해주세요.";
      else this.error.term = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;

    },
    signUp() {
      if (this.isSubmit) {
        let data = {
          email : this.email,
          password : this.password,
          nickname : this.nickName
        };

        console.log(data);

        this.isSubmit = false;

        UserApi.requestSignUp(
          data,
          res => {

            this.isSubmit = true;

            this.$router.push("/user/joinSC");
          },
          error => {
            this.isSubmit = true;
          }
        );
      } else {
        Object.values(this.error).map(v => {
          if (v) alert(v);
        });
      }
    }
  }
};
</script>


