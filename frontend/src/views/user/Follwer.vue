<template>
    <div>
        <h1>팔로워 리스트</h1>
        <!-- v-for로 리스트 -->
        <!-- 일단,, 대충 ㅣㅇ런 느낌으로다가..,. -->
        <ul v-for="follower in followers" :key="follower.nickname"></ul>
        <!-- 유저목록 옆에 각각 팔로우 버튼 달기? -->
    </div>
</template>

<script>

export default {
    name:'Follower',
    data () { 
        return {
        followercnt: 0,
        followers: [],
        }
    },
    methods: {
      // 날 팔로잉하는 유저 정보 get
      // followerInfo (uid) {
        // get(
          // store..?
        // ).then(res => {
          // get으로 가져온 유저들 정보를을 followers에 넣는다.
          // this.followers = res.data

        // }) 
      // }
    }
}
</script>

<style>

</style>