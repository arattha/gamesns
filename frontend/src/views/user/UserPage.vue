<template>
  <div>
      <Header/>
      <div class="MyPage-container">
        <div class="profile">
            <div class="center">
                <b-avatar src="https://placekitten.com/300/300" size="12rem" style="z-index:-1" ></b-avatar>
            </div>
            <div class="user-nickname center">
                김냐옹 아님
            </div>
            <div class="userinfo">
                <div class="center profile-box">
                    <h2 @click="showFollowing">{{ following.length }}</h2>
                    <p>팔로잉</p>
                </div>
                <div class="center profile-box">
                    <h2 @click="showFollower">{{ follower.length }}</h2>
                    <p>팔로워</p>
                </div>
                <div class="center profile-box">
                    <h2>37.5</h2>
                    <p>매너점수</p>
                </div>
            </div>
        </div>
        <!-- <div class="center">
            <Badge/>
        </div>
        <div class="center">
            <Manner/>
        </div> -->
        <div style="text-align: center">
            <button class="fbtn" @click="send">팔로우</button>
        </div>
        <hr>
        <div class="my-writing">
            <div style="position:absolute; right:50%; transform: translate(50%);">
                <h2>Posting</h2>
            </div>
            <div>
                <h6>4개</h6>
            </div>
        </div>
        <div class="feeditem-box">
            <FeedItem />
            <FeedItem />
            <FeedItem />
        </div>
      </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import UserApi from '../../api/UserApi'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'


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
        this.connect();
    },
    computed: {  
        
    },
    methods:{
        
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