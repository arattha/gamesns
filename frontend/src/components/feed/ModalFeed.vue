<template>
  <div style="display : flex"  class="modal modal-container">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div id = "test" class="modal-card" style="overflow:scroll; width:100%; height:90%;">
      <div class="feed-item">
        <div class="top">
          <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
          <div class="user-info">
            <div class="media-body">
              <p class="m-0 name">{{boardItem.nickname}} 임시</p>
              <p class="m-0 time">10 hours ago</p>
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
        <ul class="img-comment-list">
          <li v-for="(reply,index) in replyList" :key="index">
            <!--
              <div class="comment-img"> 일단은 코멘트만
                <img src="http://lorempixel.com/50/50/people/6">
              </div>
            -->
            <div  class="comment-text">
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

    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });

    this.getReplyList({ bid : this.boardItem.bid,
                        lastRid : 0
                      });
    this.nickname = this.$store.state.nickname;
    //console.log(this.$store.state.nickname);

  },
  mounted(){
    const $test = document.querySelector('#test');
    $test.addEventListener("scroll", (e) => this.handleScroll(e));
  },
  computed: {
    ...mapGetters(["replyList"]),
  },
  methods: {
    ...mapActions(["getReplyList","addReply"]),
    submitReply(){
      //console.log(this.$store.state.nickname);
      let data = {
        uid : this.$store.state.uid,
        bid : this.boardItem.bid,
        nickname : this.$store.state.nickname,
        content : this.search,
      }
      this.addReply(data);
    },
    handleScroll(e) {

      console.log(this.replyList);

      if(e.target.scrollHeight ==  e.target.scrollTop + e.target.clientHeight)
        this.getReplyList({ bid : this.boardItem.bid,
                            lastRid : this.replyList[this.replyList.length - 1].rid
                          });
    },
  },
  destroyed(){
    this.$store.state.replyList = [];
  },
}
</script>
<style>
  @import "../css/feed/modalfeed.css"; 
</style>