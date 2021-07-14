<template>
    <div class="container" style="text-align: left">
        <div style="padding: 10px">
            <div class="content">
                <h1>
                    회원가입<br />
                    인증 메일이<br />
                    발송되었습니다.<br /><br />

                    이메일을<br />
                    확인해주세요.<br />
                </h1>

            </div>

            <div class="next" style="margin-top: 20px; text-align:center">
                <button 
                    @click="resend" 
                    class="btn btn--back btn--login"
                >재발송</button>
                <button 
                    @click="login" 
                    style="margin-top:10px"
                    class="btn btn--back btn--login"
                >로그인하기</button>
            </div>
        </div>
    </div>
</template>

<script>
import "../../components/css/user.scss";

export default {
    methods:{
        resend(){
            this.$router.push("/");
        },
        login(){
            this.$router.push("/");
        }
    }
};
</script>
