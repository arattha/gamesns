<template>
  <div>
    <Header/>
    <div>
      <h1>팔로잉 리스트</h1>
      <h3>{{follows.length}}명을 팔로우하고있ㅅ믄다</h3>
      <!-- 나중에 닉네임 같은 걸로 내용 바꾸기~ -->
      <li v-for="(follow, idx) in follows" :key="idx">{{follow.to}}</li>
    </div>
    <Footer/>
  </div>
</template>

<script>
import http from '@/util/http-common';
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'



export default {
    name:'Following',
    components: {
      Header,
      Footer
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
  @import "../../components/css/user/follow.css"
</style>