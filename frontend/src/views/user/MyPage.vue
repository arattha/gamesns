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
						<img :src="'http://localhost:8080/account/file/' + uid">
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
								<p @click="showFollower">{{ follower.length }}</p>								
							</div>
							<div class="info">
								<p>팔로잉</p>
								<p @click="showFollowing">{{ following.length }}</p>							
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
  </div>
    <Footer/>
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
var timer;
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
            boardItems: [],
        }
    },
    created() {
        this.uid = this.$store.state.uid;
        console.log("sdanfal;skdfn",this.uid);
        this.nickname = this.$store.state.nickname;

        UserApi
            .requestFollowing({from: this.uid}
            ,((res) => {
                this.following = res;
            })
            ,(() => {})
        )

        UserApi
            .requestFollower({to: this.uid}
            ,((res) => {
                this.follower = res;
            })
            ,(() => {})
        )

        this.getBoardItems();
        
    },
    mounted(){
        window.addEventListener('scroll', this.handleScroll);
    },
    methods:{

        showFollowing() {
            this.$router.push({name:"Following", params: {following : this.following, id: this.uid}});
        },
        showFollower() {
            this.$router.push({name:"Follower", params: {follower : this.follower, id: this.uid}});
        },
        goMyedit() {
            this.$router.push("/mypage/edit");
        },
        handleScroll() {

            let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
            let windowHeight = window.innerHeight; // 스크린 창
            let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x
            //console.log(document.documentElement.scrollTop);
            if(parseInt(scrollLocation + windowHeight) == parseInt(fullHeight) && parseInt(scrollLocation) != 0){
                if( timer == null ){
                    this.getBoardItems(); //다음 뉴스피드 10개를 가져오는 함수
                    timer = setTimeout(function() {
                    timer = null;
                    }, 300);
                }
            }
        },
        getBoardItems(){
            let data;
            if (this.boardItems.length == 0) {
                data = {
                uid: this.uid,
                };
            } else {
                data = {
                uid: this.uid,
                bid: String(this.boardItems[this.boardItems.length - 1].bid),
                };
            }
            UserApi
                .requestUserFeedList( data ,
                ((list) => {
                    this.boardItems = this.boardItems.concat(list);
                }), 
                (() => {
                    alert("마이피드 가져오기 오류!");
                    })
                );
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
    beforeDestroy(){
        this.boardItems = [];
        window.removeEventListener('scroll', this.handleScroll);
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>