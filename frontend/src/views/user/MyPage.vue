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
                <div class="center">
                    <h2>26</h2>
                    <p>following</p>
                </div>
                <div class="center">
                    <h2>36.5</h2>
                    <p>매너점수</p>
                </div>
                <div class="center">
                    <h2>117</h2>
                    <p>내가 쓴 글</p>
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
        <div>
            <h2>내 게시물</h2>
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
// import Badge from '@/components/user/myPage/Badge.vue'
// import Manner from '@/components/user/myPage/Manner.vue'
import FeedItem from '../../components/feed/FeedItem.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
    usernickname:'MyPage',
    components: {
        Header,
        // Badge,
        // Manner,
        FeedItem,
        Footer
    }
}
</script>

<style>
    @import "../../components/css/user/mypage.css";
</style>