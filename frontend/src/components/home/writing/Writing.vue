<template>
  <div>
    <Header />
    <div class="Writing-top">
      <span>
        <label for="chooseFile" class="input-file-button">파일 업로드</label>
        <input
          v-on:change="fileChange($event.target.files)"
          type="file"
          name="file"
          id="chooseFile"
          multiple
          style="display:none"
        />
      </span>
      <span>
        <button class="finish-button" @click.prevent="registBoard()">작성 완료</button>
      </span>
    </div>
    <div class="Writing-container">
      <b-container fluid>
        <editor />
      </b-container>
      <b-container fluid>
        <textarea name="hashtag-input" id="hashtag-input" placeholder="#해시태그를입력하세요" v-model="hashtags"></textarea>
      </b-container>
    </div>
    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet" />
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue';
import Footer from '@/components/layout/footer/Footer.vue';
import { mapActions, mapGetters } from 'vuex';
import Editor from './Editor';

export default {
  name: 'Writing',
  components: {
    Header,
    Footer,
    Editor,
  },
  data() {
    return {
      uid: '',
      nickname: '',
      files: [],
      content: '',
      metaData: null,
      hashtags:'',
    };
  },
  created() {
    this.uid = this.$store.state.uid;
    this.nickname = this.$store.state.nickname;
  },
  methods: {
    ...mapActions(['addBoard', 'setBoardContent']),
    fileChange(fileList) {
      fileList.forEach((file) => {
        this.files.push(file);
      });
    },
    registBoard() {
      let formData = new FormData();

      formData.append('uid', this.uid);
      formData.append('content', this.boardContent);
      formData.append('hashtags',this.hashtags)


      this.files.forEach((element) => {
        formData.append('multipartFiles', element);
      });

      for (let key of formData.entries()) {
        console.log(`${key}`);
      }

      this.setBoardContent('');

      this.addBoard(formData);
      
    },
    // registHashtag() {
    //   let hashtagData = new hashtagData();

    //   hashtagData.append('hashtags',this.hashtags)
    //   this.addHashtag(this.hashtags);
    // }
  },
  computed: {
    ...mapGetters(['boardContent']),
  },
};
</script>

<style>
@import '../../css/home/writing/writing.css';
</style>
