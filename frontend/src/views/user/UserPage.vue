<template>
  <div class="h-100">
    <Header/>

		<!-- cdn -->
    <div>
      <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
			<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
			<link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
		</div>
    
		<div @scroll.passive="handleScroll" style="width:100%; margin: 0;" class="row h-100 justify-content-center align-items-center">
			<!-- 프로필 페이지 -->
			<div class="card" style="padding: 0;">

				<!-- 프로필 배경 이미지 -->
        <div class="card-header">
					<div class="profile_pic">
						<img :src="'http://localhost:8080/account/file/' + userInfo.uid">
					</div>
				</div>

				<!-- 프로필 -->
				<div class="card-body">
					<!-- 프로필 내용 -->
					<div class="d-lfex justify-content-center flex-column">
						<!-- 이름 -->
						<div class="name_container">
							<div class="name">{{userInfo.nickname}}</div>
						</div>
						<!-- 상태메세지 -->
            <div class="intro">저는 강아지를 좋아합니다.</div>
					</div>
					<!-- 팔로우 팔로잉 버튼 -->
          <div class="follow">
            <!-- Follow 상태면 팔로잉 버튼을 보여주고 Follow 상태가 아니면 팔로우 버튼을 보여준다.  -->
            <div class="follow_btn" v-if="isFollow" @click="send">팔로우</div>
            <div class="follow_btn" style="background: blue" v-else>팔로잉</div>
						<div class="chat_btn">채팅</div>
          </div>
				</div>

				<!-- 다른 컴포넌트 -->
				<div class="mypage">
					<!-- 팔로우, 매너점수 -->
          <div class="info_container">
						<div class="info">
							<p>팔로워</p>
              <p @click="showFollower">{{ userFollower.length }}</p>
						</div>
						<div class="info">
							<p>팔로잉</p>
							<p @click="showFollowing">{{ userFollowing.length }}</p>
						</div>
						<div class="info">
							<p>매너</p>
							<p>3.5</p>						
						</div>	
					</div>
				<!-- 피드, 뱃지 버튼 -->
        <div class="card-footer">
					<div class="myfeed drop-in-underline">
						<i class="fas fa-archive"></i>
					</div>
					<div class="myfeed drop-in-underline">
						<i class="far fa-smile"></i>
					</div>
				</div>
				<!-- 피드 -->
        <div class="feeditem-box">
          <ModalFeed v-if="isModalViewed" @close-modal="modalClose()" :boardItem="temp"/>
          <div v-for="(boardItem,index) in boardItems" :key="index" @click="modalShow(boardItem)">
						<FeedItem :boardItem ="boardItem"/>
          </div>
        </div>
				</div>
      </div>
		</div>
    <Footer/>
  </div>
</template>

<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import ModalFeed from '../../components/feed/ModalFeed.vue'
import UserApi from '../../api/UserApi'
var timer;
export default {

    name:'UserPage',
    props:["stompClient"],
    components: {
        Header,
        Footer,
        // Badge,
        // Manner,
        FeedItem,
        ModalFeed,
    },
    data() {
        return {
            uid: '',
            nickname: '',
            myPhoto: '',
            userInfo: null,
            userFollower: [],
            userFollowing: [],
            isModalViewed: false,
            temp: null,
            isFollow: true,
            boardItems: [],
        }
    },
    created() {
        this.uid = this.$store.state.uid;
        this.nickname = this.$store.state.nickname;
        this.userInfo = this.$route.params.suggest;
        this.getBoardItems();
        
        // console.log("userinfo",this.userInfo);
        UserApi
            .requestFollowing({from:this.userInfo.uid}
            ,((res) => {
                console.log(res);
                this.userFollowing = res;
                
                this.userFollowing.forEach(f => {
                    if(this.nickname == f.toNickname){
                        this.isFollow = false;
                    }
                })
            })
            ,(() => {})
        )

        UserApi
            .requestFollower({to:this.userInfo.uid}
            ,((res) => {
                this.userFollower = res;
            })
            ,(() => {})
        )
    },
    mounted(){
        window.addEventListener('scroll', this.handleScroll);
    },
    methods:{

        showFollowing() {
            this.$router.push({name:"Following", params: {following : this.userFollowing, id: this.userInfo.id}});
        },
        showFollower() {
            console.log("heyhey", this.userInfo);
            this.$router.push({name:"Follower", params: {follower : this.userFollower, id: this.userInfo.id}});
        },
        send() {
            if (this.stompClient && this.stompClient.connected) {
                const msg = {
                    memberName: this.nickname,
                    followingName: this.userInfo.nickname
                };
                this.stompClient.send("/receive", JSON.stringify(msg), {});
            }

            // 팔로우 버튼 '팔로잉' 으로 변환

        },
        getBoardItems(){
            let data;
            if (this.boardItems.length == 0) {
                data = {
                uid: this.userInfo.uid,
                };
            } else {
                data = {
                uid: this.userInfo.uid,
                bid: String(this.boardItems[this.boardItems.length - 1].bid),
                };
            }
            UserApi
                .requestUserFeedList( data ,
                ((list) => {
                    this.boardItems = this.boardItems.concat(list);
                }), 
                (() => {
                    alert("유저피드 가져오기 오류!");
                    })
                );
        },
        handleScroll() {

            let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
            let windowHeight = window.innerHeight; // 스크린 창
            let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
            //console.log(document.documentElement.scrollTop);

            if(( Math.abs(parseInt(fullHeight) - parseInt(scrollLocation + windowHeight)) < 3  ) && parseInt(scrollLocation) != 0){
                if( timer == null ){
                    this.getBoardItems(); //다음 뉴스피드 10개를 가져오는 함수
                    timer = setTimeout(function() {
                    timer = null;
                    }, 300);
                }
            }
        },
        modalShow(item){
            this.isModalViewed = !this.isModalViewed;
            this.temp = item;
            document.body.style.overflow = 'hidden';
        },
        modalClose(){
            this.isModalViewed = !this.isModalViewed;
            this.temp = null;
            document.body.style.overflow = 'scroll';
        }
    },
    beforeDestroy(){
        this.boardItems = [];
        window.removeEventListener('scroll', this.handleScroll);
    }
}
</script>

<style>
@import "../../components/css/user/mypage.css";
@import "../../components/css/user/followbtn.css";
@import "../../components/css/user/mypage.scss";
</style>