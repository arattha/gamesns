<template>
  <div>
      <Header/>
        <div class="Writing-container">
            <b-container fluid>
                <b-row>
                    <b-col sm="10" class="text-box">
                    <b-form-textarea
                        id="textarea-title"
                        size="lg"
                        placeholder="제목 (선택)"
                        class = "text-input"
                    ></b-form-textarea>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col sm="10" class="text-box">
                    <b-form-textarea
                        id="textarea-content"
                        size="lg"
                        placeholder="내용 (필수)"
                        class = "text-input"
                    ></b-form-textarea>
                    </b-col>
                </b-row>
            </b-container>
            <div class="file-container">
                <b-container fluid>
                    <b-row>
                        <b-col class="file-select text-box">
                            <input type="file" name="chooseFile" id="chooseFile">
                        </b-col>
                    </b-row>
                </b-container>
            </div>

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

            <div style="height:150px">
                <!-- 공간 채우기 용 입니다. -->
            </div>

            <div class="center">
                <b-button variant="outline-dark" class="profile-edit-btn">작성 완료</b-button>
            </div>
        </div>
      <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
    name:'Writing',
    components: {
        Header,
        Footer,
    },
    data() {
        return {
            dataitems: ['자유게시판', '신고합니다', '빠르게 모여라']
        }
    }
}

</script>

<style>
    .Writing-container {
        margin-top:80px;
    }

    .text-input {
        text-align: left;
        padding:0px 0px 0px 0px;
        margin-top: 0px;
        color: #9c9c9c;
        box-shadow: 0 4px 6px 0 rgba(0, 0, 0, 0.1);
    }

    .text-box {
        border-radius: 15px;
        margin:0px;
        margin: 0 auto;
    }

    #textarea-title {
        height: 50%;
    }

    #textarea-content {
        height: 250%;
    }

    .file-container {
        margin-top: 150px;
    }
    
    .file-select {
        display: flex;
        justify-content: center;
    }
    
    #chooseFile {
        height: 100%;
        width: 100%;
        max-width: 1580px;
        padding: 0;
    }

    .center {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .profile-edit-btn {
        margin-bottom: 10px;
        width: 40%;
        height: 10%;
        padding: 0 0 0 0;
    }

    .datalist-box {
        margin-top: 30px;
    }
</style>