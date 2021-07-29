<template>
    <div class="h-100">
        <Header/>
            <div>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
		<link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
	</div>
        <div @scroll.passive="handleScroll" style="width:100%; margin: 0;" class="row h-100 justify-content-center align-items-center">
			<div class="card" style="padding: 0;">
                <div class="card-header">
					<div class="profile_pic">
						<img :src="'http://localhost:8080/account/file/' + nickname">
					</div>
				</div>
				<div class="card-body">
					<div class="d-lfex justify-content-center flex-column">
						<div class="name_container">
							<div class="name">{{nickname}}</div>
						</div>
                        <div class="intro">저는 고양이를 좋아합니다.</div>
					</div>
                    <div class="edit">
							<div class="edit_btn" @click="goMyedit">프로필 편집</div>
						</div>
                    <div class="info_container">
							<div class="info">
								<p>팔로워</p>
								<p @click="showFollowing">{{ following.length }}</p>								
							</div>
							<div class="info">
								<p>팔로잉</p>
								<p @click="showFollower">{{ follower.length }}</p>							
							</div>
							<div class="info">
								<p>매너</p>
								<p>3.5</p>						
							</div>	
						</div>
            
        </div>
        <div class="card-footer">
						<div class="myfeed drop-in-underline">
							<i class="fas fa-archive"></i>
						</div>
						<div class="myfeed drop-in-underline">
							<i class="far fa-smile"></i>
						</div>
					</div>
        <div class="feeditem-box">
            <ModalFeed v-if="isModalViewed" @close-modal="modalClose()" :boardItem="temp"/>
            <div v-for="(boardItem,index) in boardItems" :key="index" @click="modalShow(boardItem)">
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
import ModalFeed from '../../components/feed/ModalFeed.vue'
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import UserApi from '../../api/UserApi'
import { mapActions , mapGetters } from "vuex";

export default {
    name:'MyPage',
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
            following: [],
            follower: [],
            isModalViewed: false,
            temp: null,
        }
    },
    created() {

        this.uid = this.$store.state.uid;
        this.nickname = this.$store.state.nickname;
        console.log("uid : " + this.uid);
        
        UserApi
            .requestFollowing({from: this.uid}
            ,((res) => {
                this.following = res.data;
            })
            ,(() => {})
        )

        UserApi
            .requestFollower({to: this.uid}
            ,((res) => {
                this.follower = res.data;
            })
            ,(() => {})
        )

        this.getUserBoardItems(this.uid);
        window.addEventListener('scroll', this.handleScroll);
    },
    computed: {  
        ...mapGetters(["boardItems"]),
    },
    methods:{
        ...mapActions(["getUserBoardItems"]),

        showFollowing() {
            this.$router.push({name:"Following", params: {following : this.following, uid: this.uid}});
        },
        showFollower() {
            this.$router.push({name:"Follower", params: {follower : this.follower, uid: this.uid}});
        },
        goMyedit() {
            this.$router.push("/mypage/edit");
        },
        handleScroll() {

            let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
            let windowHeight = window.innerHeight; // 스크린 창
            let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
            //console.log(document.documentElement.scrollTop);
            if(scrollLocation + windowHeight >= fullHeight){
                console.log('끝')
                this.getUserBoardItems(this.uid);
            }
        },
        modalShow(item){
            this.isModalViewed = !this.isModalViewed;
            console.log(this.isModalViewed);
            this.temp = item;
            document.body.style.overflow = 'hidden';
        },
        modalClose(){
            this.isModalViewed = !this.isModalViewed;
            this.temp = null;
            document.body.style.overflow = 'scroll';
        }
    },
    destroyed(){
        this.$store.state.boardItems = [];
        window.removeEventListener('scroll', this.handleScroll);
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>