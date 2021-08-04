<template>
  <div style="display : flex;"  class="modal modal-container">
    <div class="overlay" @click="$emit('close-modal')"></div>
    <div id = "modalScroll" class="modal-card" style="overflow:scroll; width:100%; height:90%;">
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
        <!-- 이미지-->
        <div class="mcardbox-item">
          <div class="mimage-slider" style="padding: 0;" >
          <div class="mslider" style="padding: 0;" v-if="img_src.length > 1">
            <button class="prev" @click="prev"><i class="fas fa-chevron-left"></i></button>
            <button class="next" @click="next"><i class="fas fa-chevron-right"></i></button>
          </div>
          <!-- 이미지 -->
          <div class="mffimg" style="padding: 0px;" v-for="number in [currentNumber]" v-bind:key="number" transition="fade">
            <img class="img-fluid" alt=""
              :src="img_src[Math.abs(currentNumber) % img_src.length]">
          </div>
          </div>
        </div><!--/ cardbox-item -->
        <div style="mcontent">{{boardItem.contents}}</div>

        <div class="cardbox-base">
          <div class="likebox">
            <div><i class="far fa-heart fa-lg"></i></div>
            <p>242</p>		   
            <div><i class="far fa-comment fa-lg"></i></div>
            <p>20</p>
          </div>
          <div class="sharebox">
            <Sharelink :boardItem="boardItem"/>
          </div>
        </div><!--/ cardbox-base -->

        <ul class="img-comment-list">
          <li class="list" v-for="(reply,index) in replyList" :key="index">
            
              <!-- <div class="small-user-img-div">
                <img src="http://lorempixel.com/100/100/people/6" class="small-user-img" style="object-fit: fill; margin:0px">
              </div> -->
           
            <div class="comment-text">
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
import UserApi from '../../api/UserApi';
import Sharelink from "./Sharelink";

export default {
  //components: { Input },
  props:["boardItem"],
  components: {
    Sharelink
  },
  data: () => {
    return {
      defaultProfile,
      img_src:[],
      search: "",
      nickname : "",
      isModalViewed: false,
      currentNumber: 0,
      replyList: []
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

  },
  mounted(){
    const $modalScroll = document.querySelector('#modalScroll');
    $modalScroll.addEventListener("scroll", (e) => this.handleScroll(e));
  },
  methods: {
    submitReply(){
      //console.log(this.$store.state.nickname);
      let data = {
        uid : this.$store.state.uid,
        bid : this.boardItem.bid,
        nickname : this.$store.state.nickname,
        content : this.search,
      }
      UserApi
        .requestAddReply( data ,
        (() => {
            alert("댓글 작성이 성공하였습니다.");
        }), 
        (() => {
            alert("댓글 가져오기 오류!");
            })
        );
    },
    getReplyList(data){
      UserApi
        .requestReplyList( data ,
        ((list) => {
            this.replyList = this.replyList.concat(list);
        }), 
        (() => {
            alert("댓글 가져오기 오류!");
            })
        );
    },
    handleScroll(e) {
      if(e.target.scrollHeight ==  e.target.scrollTop + e.target.clientHeight)
        this.getReplyList({ bid : this.boardItem.bid,
                            lastRid : this.replyList[this.replyList.length - 1].rid
                          });
    },
    next: function(e) {
      e.stopPropagation();
      this.currentNumber += 1
    },
    prev: function(e) {
      e.stopPropagation();
      this.currentNumber -= 1
    }
  },
}
</script>
<style>
  @import "../css/feed/modalfeed.css"; 
</style>