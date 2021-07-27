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
        <div>
            <Follow/>
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
            <div v-for="(boardItem,index) in boardItems" :key="index" @click="modalShow(boardItem)">
                <FeedItem :boardItem ="boardItem"/>
            </div>
        </div>
      </div>
    <Footer/>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import Follow from '../../components/user/myPage/Follow.vue'
import UserApi from '../../api/UserApi'


export default {
    name:'UserPage',
    components: {
        Header,
        Footer,
        // Badge,
        // Manner,
        FeedItem,
        Follow,

    },
    data() {
        return {
            id: '',
            myPhoto: '',
        }
    },
    created() {
        console.log(this.$route.params);
        this.getFollowing();
        this.getFollower();
    },
    computed: {  
        ...mapGetters(["following", "follower"]),
    },
    methods:{
        ...mapActions(["getFollowing", "getFollower"]),
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
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>