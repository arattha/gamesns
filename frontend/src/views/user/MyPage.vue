<template>
  <div>
      <Header/>
      <div class="MyPage-container">
        <div class="profile">
            <div class="center">
                <b-avatar src="https://placekitten.com/300/300" size="12rem" style="z-index:-1" ></b-avatar>
            </div>
            <div class="user-nickname center">
                김냐옹
            </div>
            <div class="center">
                <b-button variant="outline-dark" class="profile-edit-btn">프로필 편집</b-button>
            </div>
            <div class="userinfo">
                <div class="center profile-box">
                    <h2 @click="following">{{ followingNum }}</h2>
                    <p>팔로잉</p>
                </div>
                <div class="center profile-box">
                    <h2>{{ followerNum }}</h2>
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
        <hr>
        <div class="my-writing">
            <div style="position:absolute; right:50%; transform: translate(50%);">
                <h2>내가 쓴 글</h2>
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
            followingNum: 0,
            followerNum: 0,
            followingArr: [],
            followerArr: [],
        }
    },
    created() {
        
        let data = {
            from: 1,
        }

        UserApi.requestFollowing(data
        ,(res) => {
            this.followingNum = res.data.length;
            this.followingArr = res.data;
        }
        ,() => {
            
        })

        data = {
            to: 1,
        }

        UserApi.requestFollower(data
        ,(res) => {
            this.followerNum = res.data.length;
            this.followerArr = res.data;
        }
        ,() => {
            
        })
        
    },
    methods:{
        following() {
            this.$router.push("/mypage/following");
        },
        follower() {
            this.$router.push("/mypage/follower");
        }
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>