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
        <b-button variant="danger" @click="deleteFollow(follow.fromNickname)">삭제</b-button>
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
import { mapActions, mapGetters } from "vuex";
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
      }
    },
    created() {
    },
    computed: {
      ...mapGetters(["follower"]),
    },
    methods: {
      ...mapActions(["getFollower"]),
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
              alert("삭제되었습니다.");
              this.getFollower();
            }),
            (() => {})
          )
      }
    }
      
}
</script>

<style>

</style>