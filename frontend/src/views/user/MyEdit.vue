<template>
    <div class="myedit-container">
        <Header/>
        <form action="" class="center">
            <!-- 타이틀 -->
            <div class="myedit-title">
                프로필 편집
            </div>
            <!-- 프로필 사진 block-->
            <div class="photo-edit-container">
                <div class="avatar-upload">
                    <div class="avatar-edit">
                        <input type='file' id="imageUpload" @change="fileChange" accept=".png, .jpg, .jpeg" />
                        <label for="imageUpload">
                            <i class="fas fa-pen edit-logo" style="font-size:16px"></i>
                        </label>
                    </div>
                    <div class="avatar-preview">
                        <div id="imagePreview" v-bind:style="{backgroundImage: 'url(' + profileImg + ')'}">
                        </div>
                    </div>
                    <!-- <input v-on:change="fileChange($event.target.files)" type="file" name="file" id="chooseFile"> -->
                </div>
            </div>

            <!-- 정보들 block -->
            <div class="infos center"> 
                <!-- 닉네임 -->
                <div class="info-box">
                    <!-- 현재 닉네임 보여주고/필수-->
                    <div class="input-container" style="align-content: center;">
                        <input v-model="nickname" id="name" class="input" type="text" pattern=".+" required />
                        <label class="label" for="name">닉네임</label>
                        <div class="check">
                            <div class="check_btn" @click="dupCheck">중복체크</div>
                        </div>
                    </div>
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
            <div class="exit-div">
                <span class="cancle">
                    <button class="cancle-btn" @click="back">
                        취소
                    </button>
                </span>
                <span class="submit">
                    <button class="submit-btn"
                        @click="save"
                        :disabled="!isSubmit">저장</button>
                </span>
            </div>
        </form>
        <Footer />
        <div>
            <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
			<link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
        </div>
    </div>
</template>

<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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
            file: null,
            profileImg: null
        }
    },
    created() {

        // JWT 토큰으로 사용자 아이디 받아오기
        // 가능하면 닉네임까지..



        this.imgPath = "C:\\upload/" + this.id + ".PNG";
        // this.nickname = res.data.nickname;
        
    },
    methods: {
        fileChange(){
            // this.file = fileList[0];
            
            var input = event.target;
            if (input.files && input.files[0]) { 
                var reader = new FileReader(); 
                reader.onload = e => { 
                    this.profileImg = e.target.result; 
                }; 
                reader.readAsDataURL(input.files[0]); 
            } else { this.profileImg = null; }

            this.file = input.files[0]
            // this.profileImg = fileList.result;
            // console.log(this.profileImg);
        },
        dupCheck(){
            if(this.nickname.length == 0) {
                console.log("hi")
                alert("닉네임을 입력해주세요.");
            } else {
                console.log("hi2")
                UserApi.requestDupCheck(this.nickname
                    ,() => { 
                        this.isSubmit = true;
                        }
                    ,() => {});
            }
        },
        save() {

            if(this.isSubmit) {
                this.isSubmit = false;

                let formData = new FormData();

                formData.append('uid',1);
                formData.append('nickname',this.nickname);
                formData.append('multipartFile', this.file);

                UserApi.requestUpdateUser(formData
                ,() => {
                    alert("회원정보가 수정되었습니다.");
                    this.$router.push("/mypage");
                }
                ,() => {
                    this.isSubmit = true;
                })
            } else {
                alert("중복체크를 눌러주세요.");
            }

        },
        back() {
            this.$router.push("/mypage");
        }
    }
}
</script>
<style>
    @import "../../components/css/user/myedit.css";
</style>