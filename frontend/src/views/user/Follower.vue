<template>
    <div>
      <Header/>
    <div class="form">
      <h4 class="title">Follower - {{ follower.length }}</h4>
      <!-- 나중에 닉네임 같은 걸로 내용 바꾸기~ -->
      <li class="list" v-for="(follow, idx) in follower" :key="idx">
        <div>
          <img :src="`http://localhost:8080/account/file/` + follow.fromNickname">
          {{follow.fromNickname}} 
        </div>
        <!-- 사용자 아이디와 같으면 삭제 버튼을 생성 : 사용자 아이디는 어떤 화면에서든 가져올 수 있다. -->
        <b-button variant="danger"  @click="deleteFollow(follow.fromNickname)">삭제</b-button>
      </li>

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
        uid: '',
        nickname: '',
        follower: [],
      }
    },
    created() {
      this.follower = this.$route.params.follower;
      this.uid = this.$store.state.uid;
      this.nickname = this.$store.state.nickname;
    },
    methods: {
      deleteFollow(u){
        let data = {
          fromNickname: u,
          toNickname: this.nickname,
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