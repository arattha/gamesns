<template>
  <div class="user join wrapC">
    <h1>비밀번호 변경하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input
          v-model="email"
          autocapitalize="off"
          v-bind:class="{
            error: error.email,
            complete: !error.email && email.length !== 0,
          }"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
        />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{ error.email }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          v-bind:class="{
            error: error.password,
            complete: !error.password && password.length !== 0,
          }"
          id="password"
          :type="passwordType"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{ error.password }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          v-bind:class="{
            error: error.passwordConfirm,
            complete: !error.passwordConfirm && passwordConfirm.length !== 0,
          }"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">
          {{ error.passwordConfirm }}
        </div>
      </div>
    </div>

    <button
      class="btn-bottom"
      @click="chPwd"
      :disabled="!isSubmit"
      :class="{ disabled: !isSubmit }"
    >
      저장
    </button>
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
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
    };
  },
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    email: function(v) {
      this.checkForm();
    },
    password: function(v) {
      this.checkForm();
    },
    passwordConfirm: function() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
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
      else this.error.passwordConfirm = false;

      if(this.email.length == 0 || this.password.length == 0) this.isSubmit = false;
      else{
        let isSubmit = true;
        Object.values(this.error).map((v) => {
            if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;
      }
    },
    chPwd() {
        console.log(this.error);

      if (this.isSubmit) {
        let data = {
          email: this.email,
          password: this.password,
        };

        this.isSubmit = false;

        UserApi.requestChPwd(
          data,
          (res) => {
            this.isSubmit = true;

            this.$router.push("/");
          },
          (error) => {
            if(error) this.$router.push("/error");
            this.isSubmit = true;
          }
        );
      } else {
          console.log("hi");
        Object.values(this.error).map((v) => {
          if (v) alert(v);
        });
      }
    },
  },
};
</script>
