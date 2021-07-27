<template>
  <div>
    <Header/>
    <div class="form">
      <h4 class="title">Following - {{ following.length }}</h4>
      <!-- 나중에 닉네임 같은 걸로 내용 바꾸기~ -->
      <li class="list" v-for="(follow, idx) in following" :key="idx">
        <div>
          <img src="`http://localhost:8080/account/file/1`" alt="">
          {{follow.toNickname}} 
        </div>
        <Follow/>
      </li>
      <!-- 나중에 함 지우기 -->
      <!-- <li class="list">
        <div><img src="http://localhost:8080/account/file/{{}}" alt="">사용자1 </div>
        <Follow/>
      </li>
      <li class="list">
        <div><img src="http://localhost:8080/account/file/{{}}" alt="">사용자1 </div>
        <Follow/>
      </li> -->

    </div>
    <Footer/>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import Follow from '@/components/user/myPage/Follow.vue'

export default {
    name:'Following',
    components: {
      Header,
      Footer,
      Follow
    },
    data () { 
      return {
      }
    },
    created() {
    },
    computed: {
      ...mapGetters(["following"]),
      
    }
}
</script>

<style>
  @import "../../components/css/user/follow.css";
</style>