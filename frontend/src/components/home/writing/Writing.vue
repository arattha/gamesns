<template>
  <div>
      <Header/>
        <div class="Writing-container">
            <b-container fluid>
                <!--
                <b-row>
                    <b-col sm="10" class="text-box">
                    <b-form-textarea v-model="title"
                        id="textarea-title"
                        size="lg"
                        placeholder="제목 (선택)"
                        class = "text-input"
                    ></b-form-textarea>
                    </b-col>
                </b-row>
                -->
                <b-row>
                    <b-col sm="10" class="text-box">
                        <editor id="textarea-content" class = "text-input" @getMeta="getMeta" v-model="content" :isOK="true"/>

                        

                    <!-- <b-form-textarea
                        v-model="contents"
                        id="textarea-content"
                        size="lg"
                        placeholder="내용 (필수)"
                        class = "text-input"
                    ></b-form-textarea> -->
                    </b-col>
                    <editor v-model="metaData" />
                </b-row>
            </b-container>
            <div class="Writing-bottom">
                <span>
                    <label for="chooseFile" class="input-file-button">파일 업로드</label>
                    <input v-on:change="fileChange($event.target.files)" type="file" name="file" id="chooseFile" multiple style="display:none">
                </span>
                <span>
                    <button class="finish-button" @click.prevent="registBoard()">작성 완료</button>
                </span>
            </div>
            <!--
            <div class="datalist-container">
                <b-container fluid class="datalist-box">
                    <b-row>
                        <b-col sm="10" class="text-box">
                            <b-form-input list="my-board-list-id" placeholder="게시판을 선택하세요"></b-form-input>
                            <datalist id="my-board-list-id">
                                <option>자유 게시판</option>
                                <option :v-for="dataitem in dataitems">{{ dataitem }}</option>
                            </datalist>
                        </b-col>
                    </b-row>
                </b-container>

                <b-container fluid class="datalist-box">
                    <b-row>
                        <b-col sm="10" class="text-box">
                            <b-form-input list="my-game-list-id" placeholder="게임을 선택하세요"></b-form-input>
                            <datalist id="my-game-list-id">
                                <option>스타크래프트</option>
                                <option :v-for="dataitem in dataitems">{{ dataitem }}</option>
                            </datalist>
                        </b-col>
                    </b-row>
                </b-container>
            </div>
            -->
        </div>
        <div>
            <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
        </div>
      <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import { mapActions } from "vuex";
import Editor from './Editor'

export default {
    name:'Writing',
    components: {
        Header,
        Footer,
        Editor
    },
    data() {
        return {
            // dataitems: ['자유게시판', '신고합니다', '빠르게 모여라'],
            //formData:new FormData(),
            uid: '',
            nickname: '',
            files:[],
            content:'',
            metaData: null,
        }
    },
    created(){
        this.uid = this.$store.state.uid;
        this.nickname = this.$store.state.nickname;
    },
    methods:{
        ...mapActions(["addBoard"]),
        getMeta(data){
            console.log("data", data);
            this.metaData = data;
        },
        fileChange(fileList){
            fileList.forEach(file => {
                this.files.push(file);
            });
        },
        registBoard(){
            // this.formData.append('uid',0);
            // this.formData.append('contents',this.contents);
            // this.addBoard(this.formData);
            


            // let data = {
            //     uid : 0,
            //     contents : this.contents,
            //     multipartFiles : this.files,
            // }

            let formData = new FormData();
            
            formData.append('uid', this.uid);
            formData.append('content',this.content);
            
            this.files.forEach(element => {
                formData.append('multipartFiles',element);
            });
            
            for (let key of formData.entries()){
                console.log(`${key}`);
            }

            this.addBoard(formData);
            //this.$router.push('/main');

        },
    }
    
}

</script>

<style>
    @import "../../css/home/writing/writing.css";
</style>