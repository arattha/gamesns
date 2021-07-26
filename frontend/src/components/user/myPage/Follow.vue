<template>
    <div>
        <!-- 다른 사람 팔로우 버튼 누르면 내 팔로잉 목록에 추가 -->
        <!-- 팔로우 하면 버튼 팔로잉으로 바뀌기 -->
        <!-- <button v-if="followState" @click="addFollow">팔로잉</button>
        <button v-else @click="addFollow">팔로우</button> -->
        <button class="fbtn">팔로우</button>


    </div>
</template>

<script>
export default {
    name:'Follow',
    data () {
        return {
            followState: false,
        }
    },
    // methods: {
    //     // 일단 대충 이런 느낌,,ㅎ,,,
    //     // 팔로우 추가, 삭제
    //     addFollow() {
    //         // post 요청으로
    //         post(
    //             // store..?
    //         ).then(res => {
    //             if (this.followState) {
    //                 this.followState = false
    //             } else {
    //                 this.followState = true
    //             }
    //         })
    //     },
        // 

    // }
}
</script>

<style>
    @import "../../../components/css/user/followbtn.css";
</style>