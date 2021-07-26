<template>
    <div>
        <Header/>
        <!-- 편집 폼 -->
        <form action="">
            <!-- 타이틀 -->
            <div class="title"><h1>프로필 편집</h1></div>
            <!-- 프로필 사진 block-->
            <div class="profile">
                <!-- 현재 프로필 사진/모양자르기 -->
                <img class="profile-img" :src="imgPath" alt="">
                <!-- 사진 변경 버튼 -->
                <button class="btn">프로필 사진 변경</button>
            </div>
            <!-- 정보들 block -->
            <div class="infos"> 
                <!-- 닉네임 -->
                <div class="info">
                    <h3>닉네임</h3>
                    <!-- 현재 닉네임 보여주고/필수-->
                    <input type="text" :placeholder="nickname" v-model="nickname">
                    <button class="btn">중복체크</button>
                </div>
                <!-- 상태메시지
                <div class="info">
                    <h3>소개</h3>
                    <input type="text">
                </div>
                디스코드 계정
                <div>
                    <h3>디스코드 계정</h3>
                    <input type="text">
                </div> -->
            </div>
            <!-- 버튼 블록 -->
            <div class="submit">
                <button 
                    @click="save"
                    :disabled="isSubmit"
                    :class="{disabled : !isSubmit}">저장</button>
            </div>
        </form>
        <Footer />
    </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import UserApi from '../../api/UserApi'

export default {

    name:"MyEdit",
    components:{
        Header,
        Footer,
    },
    data() {
        return{
            id: 1,
            imgPath: '',
            nickname: '',
            isSubmit: false,
        }
    },
    created() {

        // JWT 토큰으로 사용자 아이디 받아오기
        // 가능하면 닉네임까지..



        this.imgPath = "C:\\upload/" + this.id + ".PNG";
        // this.nickname = res.data.nickname;
        
    },
    methods: {
        dubCheck(){
            if(this.nickname.length == 0) {
                alert("닉네임을 입력해주세요.");
            } else {

                UserApi.requestCheckUser(this.nickname
                    ,() => { 
                        this.isSubmit = true;
                        alert("사용가능한 닉네임입니다."); 
                        }
                    ,() => { 
                        alert("중복된 닉네임입니다.");
                    });
            }
        },
        submit() {

            if(this.isSubmit) {
                this.isSubmit = false;

                let data = {
                    uid: 1,
                    nickname: this.nickname
                }

                UserApi.requestUpdateUser(data
                ,() => {
                    alert("회원정보가 수정되었습니다.");
                    this.$router.push("/feed/main");
                }
                ,() => {
                    this.isSubmit = true;
                })
            }

        }
    }
}
</script>
