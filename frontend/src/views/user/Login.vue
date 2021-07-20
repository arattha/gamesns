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
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
// import PV from "password-validator";
// import * as EmailValidator from "email-validator";
// import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";
// import UserApi from "../../api/UserApi";

export default {
  // components: {
  //   KakaoLogin,
  //   GoogleLogin
  // },
//   data: () => {
//     return {
//       email: "",
//       password: "",
//       passwordSchema: new PV(),
//       error: {
//         email: false,
//         passowrd: false
//       },
//       isSubmit: false,
//       component: this
//     };
//   },
//   created() {
//     this.component = this;

//     this.passwordSchema
//       .is()
//       .min(8)
//       .is()
//       .max(100)
//       .has()
//       .digits()
//       .has()
//       .letters();
//   },
//   watch: {
//     password: function(v) {
//       this.checkForm();
//     },
//     email: function(v) {
//       this.checkForm();
//     }
//   },
//   methods: {
//     checkForm() {
//       console.log(EmailValidator.validate(this.email));
//       if (this.email.length >= 0 && !EmailValidator.validate(this.email))
//         this.error.email = "이메일 형식이 아닙니다.";
//       else this.error.email = false;

//       if (
//         this.password.length >= 0 &&
//         !this.passwordSchema.validate(this.password)
//       )
//         this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
//       else this.error.password = false;
      
//       let isSubmit = true;
//       Object.values(this.error).map(v => {
//         if (v) isSubmit = false;
//       });
//       this.isSubmit = isSubmit;
//     },
//     onLogin() {
//       if (this.isSubmit) {
//         let { email, password } = this;
//         let data = {
//           email,
//           password
//         };

//         //요청 후에는 버튼 비활성화
//         this.isSubmit = false;

//         UserApi.requestLogin(
//           data,
//           res => {
//             //통신을 통해 전달받은 값 콘솔에 출력
//             // console.log(res);

//             //요청이 끝나면 버튼 활성화
//             this.isSubmit = true;

//             this.$router.push("/feed/main");
//           },
//           error => {
//             if(error) this.$router.push("/error");
            
//             //요청이 끝나면 버튼 활성화
//             this.isSubmit = true;
            
//           }
//         );
//       }
//     }
//   },
};
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