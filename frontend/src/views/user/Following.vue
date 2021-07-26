<template>
  <div>
    <Header/>
    <div class="form">
      <h4 class="title">Following - {{follows.length}}</h4>
      <!-- 나중에 닉네임 같은 걸로 내용 바꾸기~ -->
      <li class="list" v-for="(follow, idx) in follows" :key="idx">
        <div>
          <img src="`http://localhost:8080/account/file/${follow.toId}`" alt="">
          {{follow.toId}} 
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
import http from '@/util/http-common';
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import Follow from '@/components/user/myPage/Follow.vue'



export default {
    name:'Following',
    components: {
      // Header,
      Footer,
      Follow
    },
    data () { 
      return {
      followcnt: 0,
      follows: [],
      }
    },
    created () {
      this.getUser()
    },
    methods: {
      getUser () {
        http.get('/follow/following', {params: {to: 1}}
        ).then(res => {
          console.log(res.data)
          this.follows = res.data
        }).catch(err => {
          alert('실패');
          console.log(err)
        })
      }
    }
}
</script>

<style>
  @import "../../components/css/user/follow.css";
</style>