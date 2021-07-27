<template>
  <div class="h-100">
        <Header/>
            <div>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
		<link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
	</div>
      <div style="width:100%; margin: 0;" class="row h-100 justify-content-center align-items-center">
			<div class="card" style="padding: 0;">
                <div class="card-header">
					<div class="profile_pic">
						<img src="https://t1.daumcdn.net/cfile/tistory/99D0593B5FA926B127">
					</div>
				</div>
				<div class="card-body">
					<div class="d-lfex justify-content-center flex-column">
						<div class="name_container">
							<div class="name">박루바</div>
						</div>
                        <div class="intro">저는 강아지를 좋아합니다.</div>
					</div>
                     <div class="follow">
							<div class="follow_btn" @click="send">팔로우</div>
						</div>
            <div class="info_container">
							<div class="info">
								<p>팔로워</p>
                                <p>-</p>
								<!-- <p @click="showFollowing">{{ following.length }}</p>								 -->
							</div>
							<div class="info">
								<p>팔로잉</p>
                                <p>-</p>
								<!-- <p @click="showFollower">{{ follower.length }}</p>							 -->
							</div>
							<div class="info">
								<p>매너</p>
								<p>3.5</p>						
							</div>	
						</div>
        </div>
        <div class="card-footer">
						<div class="feed drop-in-underline">
							<i class="fas fa-archive"></i>
						</div>
						<div class="feed drop-in-underline">
							<i class="far fa-smile"></i>
						</div>
					</div>
        <div class="feeditem-box">
            <div v-for="(boardItem,index) in boardItems" :key="index">
                <FeedItem :boardItem ="boardItem"/>
            </div>
        </div>
      </div>
    <Footer/>
  </div>
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
import UserApi from '../../api/UserApi'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { mapActions , mapGetters } from "vuex";

export default {

    name:'UserPage',
    components: {
        Header,
        Footer,
        // Badge,
        // Manner,
        FeedItem,

    },
    data() {
        return {
            id: '',
            myPhoto: '',
            userInfo: null,
        }
    },
    created() {
        //this.getUserBoardItems(this.$router.param.nickname);
        this.connect();
        this.getUserBoardItems();
    },
    computed: {  
        ...mapGetters(["boardItems"]),
    },
    methods:{
        ...mapActions(["getUserBoardItems"]),

        showFollowing() {
            console.log(this.following);
            this.$router.push("/mypage/following");
        },
        showFollower() {
            this.$router.push("/mypage/follower");
        },
        goMyedit() {
            this.$router.push("/mypage/edit");
        },
        connect() {
            const serverURL = "http://localhost:8080/alarm"
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
            this.stompClient.connect(
                {},
                frame => {
                // 소켓 연결 성공
                this.connected = true;
                console.log('소켓 연결 성공', frame);
                
                },
                error => {
                // 소켓 연결 실패
                console.log('소켓 연결 실패', error);
                this.connected = false;
                }
            );        
        },
        send() {
            
            if (this.stompClient && this.stompClient.connected) {
                const msg = {
                    userName: "yourname",
                    followingName: "조성표"
                };
                this.stompClient.send("/receive", JSON.stringify(msg), {});
            }

            // 팔로우 버튼 '팔로잉' 으로 변환

        },
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
    @import "../../components/css/user/followbtn.css";
</style>