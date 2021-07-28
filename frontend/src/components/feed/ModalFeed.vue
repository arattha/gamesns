<template>
  <div style="display : flex"  class="modal modal-container">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div class="modal-card" style="overflow:scroll; width:100%; height:90%;">
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
            <div class="comment-text">
              <strong><a href="">{{reply.nickname}}</a></strong>
              <p>{{reply.content}}</p> 
              <span class="date sub-text">on December 5th, 2016</span>
            </div>
          </li>
        </ul>
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
export default {
  props:["boardItem"],
  data: () => {
    return {
      defaultProfile,
      img_src:[],
    };
  },
  created() {
    this.boardItem.imgFiles.forEach(element => {
      this.img_src.push("http://localhost:8080/board/file/"+element.file_name);
    });
    this.getReplyList({ bid : this.boardItem.bid,
                        lastRid : 0
                      });
  },
  computed: {
    ...mapGetters(["replyList"]),
  },
  methods: {
    ...mapActions(["getReplyList"]),
  },
  destroyed(){
    this.$store.state.replyList = [];
  },
}
</script>
<style>
  @import "../css/feed/modalfeed.css"; 
</style>