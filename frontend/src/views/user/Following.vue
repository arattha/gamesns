<template>
  <div>
      <h1>팔로잉 리스트</h1>
      <!-- v-for로 리스트 -->
      <!-- 일단,, 대충 ㅣㅇ런 느낌으로다가..,. -->
      <ul v-for="follow in follows" :key="follow.nickname"></ul>
  </div>
</template>

<script>

export default {
    name:'Following',
    data () { 
      return {
      // 팔로우수도 추가..?
      followcnt: 0,
      follows: [],
      }
    },
    methods: {
      // 팔로잉하는 유저 정보 get
      // followInfo (uid) {
        // get(
          // store..?
        // ).then(res => {
          // get으로 가져온 유저들 정보를을 followings에 넣는다.
          // this.follows = res.data

        // }) 
      // }
    }
}
</script>

<style>

</style>