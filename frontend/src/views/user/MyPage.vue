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
						<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FxF5EE%2FbtqzBVKqpvW%2Ffa2TnqjoXkLRwwBkLnChJ1%2Fimg.png">
					</div>
				</div>
				<div class="card-body">
					<div class="d-lfex justify-content-center flex-column">
						<div class="name_container">
							<div class="name">성루비</div>
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
import { mapActions, mapGetters } from "vuex";
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import UserApi from '../../api/UserApi'


export default {
    name:'MyPage',
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
        }
    },
    created() {
        this.getFollowing();
        this.getFollower();
        this.getUserBoardItems();
    },
    computed: {  
        ...mapGetters(["following", "follower","boardItems"]),
    },
    methods:{
        ...mapActions(["getFollowing", "getFollower","getUserBoardItems"]),
        showFollowing() {
            console.log(this.following);
            this.$router.push("/mypage/following");
        },
        showFollower() {
            this.$router.push("/mypage/follower");
        },
        goMyedit() {
            this.$router.push("/mypage/edit");
        }
    },
    destroyed(){
        this.$store.state.boardItems = [];
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>