<template>
  <div style="display : flex"  class="modal modal-container">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div class="modal-card" style="overflow:scroll; width:100%; height:90%;">
      <div class="feed-item">
        <div class="top">
          <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
          <div class="user-info">
            <div class="user-name">
              <button>{{boardItem.nickname}}</button>
            </div>
            <div class="user-writing-time">
              <h5>12minutes ago</h5>
            </div>
            <!-- <p class="date">9시간 후</p> -->
          </div>
          <div class="ellipsis">
            <img src="@/assets/images/ellipsis.png" alt="">
          </div>
        </div>
        <div class="image my-image" >
          <img v-for="(img,index) in img_src" :key="index" :src="img" alt="">
        <!--이미지 -->
        </div>
        <div class="content">
          <span>{{boardItem.contents}}</span>
        </div>
        <hr>
        <ul class="img-comment-list">
          <li v-for="(reply,index) in replyList" :key="index">
            <!--
              <div class="comment-img"> 일단은 코멘트만
                <img src="http://lorempixel.com/50/50/people/6">
              </div>
            -->
            <div id = "real" class="comment-text">
              <strong><a href="">{{reply.nickname}}</a></strong>
              <p>{{reply.content}}</p> 
              <span class="date sub-text">on December 5th, 2016</span>
            </div>
          </li>
        </ul>
        <div class="search-bar">
          <input v-model="search" class="search__input" type="text" placeholder="댓글 입력">
          <button class="" @click="submitReply">게시</button>
        </div>
      </div>
    </div>
    <div>
      <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
    </div>
  </div>
</template>
<script>
import defaultProfile from "../../assets/images/profile_default.png";
import { mapActions, mapGetters } from "vuex";
//import Input from '../common/Input.vue';
export default {
  //components: { Input },
  props:["boardItem"],
  data: () => {
    return {
      defaultProfile,
      img_src:[],
      search: "",
      nickname : "",
      isModalViewed: false,
    };
  },
  created() {
    window.addEventListener('scroll', this.handleScroll);
    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });

    this.getReplyList({ bid : this.boardItem.bid,
                        lastRid : 0
                      });
    this.nickname = this.$store.state.nickname;
    console.log(this.$store.state.nickname);
  },
  computed: {

    ...mapGetters(["replyList"]),
  },
  methods: {
    ...mapActions(["getReplyList","addReply"]),
    submitReply(){
      console.log(this.$store.state.nickname);
      let data = {
        uid : this.$store.state.uid,
        bid : this.boardItem.bid,
        nickname : this.$store.state.nickname,
        content : this.search,
      }
      this.addReply(data);
    },
    // handleScroll() {

    //         let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
    //         let windowHeight = window.innerHeight; // 스크린 창
    //         let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
    //         //console.log(document.documentElement.scrollTop);
            
    //         let result = document.querySelector('#real').clientHeight;
    //         let result2 = document.querySelector('#real').scrollHeight;
    //         let result3 = document.querySelector('#real').scrollTop;
    //         console.log(result);
    //         console.log(result2);
    //         console.log(result3);
    //         if(scrollLocation + windowHeight >= fullHeight){
    //             console.log('끝')
    //             let temp = ( this.replyList.length > 0 ) ? this.replyList[this.replyList.length - 1].rid : 0;
    //             this.getReplyList({ bid : this.boardItem.bid,
    //                     lastRid : temp
    //                   });
    //         }

            
    // },
  },
  destroyed(){
    this.$store.state.replyList = [];
    window.removeEventListener('scroll', this.handleScroll);
  },
}
</script>
<style>

.modal-container {
  font-family: 'Nanum Gothic', sans-serif;
  position: relative;
  top: 50%;
  left: 100%;
}

.modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: black;
}
.modal-card {
  position: relative;
  max-width: 80%;
  margin: auto;
  margin-top: 30px;
  background-color: white;
  min-height: 500px;
  z-index: 10;
  opacity: 1;
}

.user-writing-time{
  display: flex;
  align-items: center;
  position: absolute;
  right: 12%;
}


/* 아래 부터 feed-item scss */

.feed-item {
    padding: 0;
    margin-bottom: 30px;
    background-color: white;
    padding-bottom: 20px;
    border: 1px solid #dcdcdc;
}

.top {
    display: flex;
    padding: 10px 15px 10px 15px;
    border-bottom: 1px solid #dcdcdc;
}
.profile-image {
    float: left;
    height: 35px;
    width: 35px;
}
.feed-item .content {
    padding-left: 10px;
    padding-right: 10px;
    margin: 15px;
}
.user-info {
  display: flex;
}
.user-name {
  float: left;
  display: flex;
}            
button {
  font-weight: f600;
  align-items: center;
            }

.my-image {
  display: flex;
  width:90%;
  height:90%;
  margin: 0 auto;
  margin-top: 10px; 
}

/* 하단 부터 comment-list */

.feed-item li {
  display: flex;
  text-align: -webkit-match-parent;
  padding: 10px 15px;
  align-items: center;
}

.img-comment-list .comment-img {
  margin-right:15px;
}

.img-comment-list img {
  margin: 0px;
}

.modal-container .img-comment-list {
    list-style: none;  
    padding: 0;
}
.modal-container .img-comment-list li {
    margin:0;
}

.modal-container .img-comment-list li > div {
    display:table-cell;
}

.modal-container .img-comment-list img {
    border-radius:50%;
    width: 42px;
    height: 42px;
    margin-right: 10px;
    margin-top: 20px;
}

.modal-container .img-comment-list p {
    margin: 0;
}

.modal-container .img-comment-list span {
    font-size: .8em; 
    color: #aaa;
}

.feed-item hr {
  margin: 5px 0px;
}
</style>