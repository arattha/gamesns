<template>
    <div>
      <Header/>
    <div class="form">
      <h4 class="title">Follower - {{ follower.length }}</h4>
      <!-- 나중에 닉네임 같은 걸로 내용 바꾸기~ -->
      <li class="list" v-for="(follow, idx) in follower" :key="idx">
        <div>
          <img src="`http://localhost:8080/account/file/1`" alt="">
          {{follow.fromNickname}} 
        </div>
        <!-- 사용자 아이디와 같으면 삭제 버튼을 생성 : 사용자 아이디는 어떤 화면에서든 가져올 수 있다. -->
        <b-button variant="danger"  @click="deleteFollow(follow.fromNickname)">삭제</b-button>
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
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import UserApi from '../../api/UserApi';

export default {
    name:'Follower',
    components: {
      Header,
      Footer,
    },
    data () { 
      return {
        uid: -1,
        follower: [],
      }
    },
    created() {
      this.follower = this.$route.params.follower;
    },
    methods: {
      deleteFollow(u){
        let data = {
          fromNickname: u,
          toNickname: "조성표",
          type: false
        }
        UserApi
          .requestFollowUpdate(
            data,
            ((res) => {
              console.log(res);
              alert("삭제되었습니다.");
              this.follower = res;
            }),
            (() => {})
          )
      }
    }
      
}
</script>

<style>

</style>