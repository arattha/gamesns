<template>
  <div class="fbody">
    
    <div class="cardbox bg-white">
      <!-- 프로필 -->
        <div class="cardbox-heading">
          <!-- 내부 사진 크기 조절 필요 -->
            <div class="fimg">
                <img :style="{'background-image': 'url('+defaultProfile+')'}" alt="">
            </div>
            <div class="media-body">
                <p class="m-0 name">{{boardItem.uid}}</p>
                <p class="m-0 time">10 hours ago</p>
            </div><!--/ media -->
        </div><!--/ cardbox-heading -->

        <!-- 이미지나 내용 -->
        <div class="cardbox-item">
          <div class="image-slider" style="padding: 0;">
          <div class="slider" style="padding: 0;" v-if="img_src.length > 1">
            <button class="prev" @click="prev"><i class="fas fa-chevron-left"></i></button>
            <button class="next" @click="next"><i class="fas fa-chevron-right"></i></button>
          </div>
          <!-- 이미지 -->
          <div class="ffimg" style="padding: 0px;" v-for="number in [currentNumber]" v-bind:key="number" transition="fade">
            <img class="img-fluid" alt=""
              :src="img_src[Math.abs(currentNumber) % img_src.length]">
          </div>
          </div>
          <!-- 내용 -->
          <!-- <editor v-model="boardItem.contents" :isOK="false"/> -->
          <editor-content :editor="editor" />
          <!-- <div>{{boardItem.contents}}</div> -->
        </div><!--/ cardbox-item -->
        
        <!-- 좋아요 등 -->
        <div class="cardbox-base">
          <div class="likebox">
            <Like :boardItem="boardItem" />
            <!-- <div @click="boardLiked" v-if="likes==0">
                <p class="m-0" style="color:#ff8f00"><i class="far fa-heart fa-lg"></i></p>
            </div>
            <div @click="boardLiked" v-if="likes==1">
                <p class="m-0" style="color:#ff8f00"><i class="fas fa-heart fa-lg"></i></p>
            </div>
            <p>{{ likelist.length }}</p>		    -->
            <div><i class="far fa-comment fa-lg"></i></div>
            <p>20</p>
          </div>
          <div class="sharebox">
            <Sharelink :boardItem="boardItem" />
          </div>
        </div><!--/ cardbox-base -->

        <div>
          <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
          <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        </div>
    </div>

  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
import {Editor, EditorContent} from '@tiptap/vue-2'
import StarterKit from '@tiptap/starter-kit'
import Sharelink from "./Sharelink";
import Like from "./Like";
import http from '@/util/http-common';

export default {
  props:['boardItem'],
  components: {
      EditorContent,
      Sharelink,
      Like,
    },
  data: () => {
    return { 
      editor: null,
      defaultImage,
      defaultProfile,
      img_src:[],
      currentNumber: 0,
      // likelist: [],
      // likes: 0,
      // uid: 0,
    };
  },created() {
    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });
    // this.uid = this.$store.state.uid;
    // this.setLiked();
  },
  methods: {
    next: function(e) {
      e.stopPropagation();
      this.currentNumber += 1
    },
    prev: function(e) {
      e.stopPropagation();
      this.currentNumber -= 1
    },
    // getLikeList: function() {
      
    //   let data;
    //   data = {
    //     bid: this.boardItem.bid
    //   }

    //   http
    //     .get(`/board/like`, { params: data })
    //     .then(({ data }) => {

    //       if (data == null) {
    //         this.likelist = []
    //       } else {
    //         this.likelist = data
    //       }
    //     })
    //     .catch(() => {
    //       console.log('좋아요 리스트 에러')
    //     })
    // },
    // setLiked: function() {

    //   let data;
    //   data = {
    //     bid: this.boardItem.bid,
    //     uid: this.uid
    //   }

    //   http
    //   .get(`/board/liked`, { params: data } )
    //   .then(({ data }) => {

    //     this.likes = data.object
    //     this.getLikeList();
    //   })
    //   .catch(() => {
    //     console.log('좋아요 로드 에러')
    //   })

    // },
    // boardLiked: function(e) {

    //   let data;
    //   data = {
    //     bid: this.boardItem.bid,
    //     uid: this.uid
    //   }

    //   http
    //   .post(`/board/AddOrDeleteLike`, data )
    //   .then(({ data }) => {

    //     this.likes = data.object

    //     this.getLikeList();
    //   })
    //   .catch(() => {
    //     console.log('좋아요 에러')
    //   })
    // },
  },
  mounted(){
    this.editor = new Editor({
      editable: false,
      content: this.boardItem.contents,
      extensions: [
        StarterKit,
      ],
    });
  },
  beforeDestroyed(){
    this.editor.destroy();
  },
};
</script>
