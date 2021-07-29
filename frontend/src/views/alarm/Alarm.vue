<template>
  <div class="alarm-container">
    <Header />
    <div class="form">
      <h5 class="title">알림 - {{ List.length }}개</h5>
      <div v-if="List.length > 0">
        <li class="list" v-for="(user, index) in List" :key="index">
          <div class="small-user-img-div">
            <img
              src="http://lorempixel.com/50/50/people/9"
              class="small-user-img"
            />
            <!-- 임의의 이미지가 들어가는거라, user의 프로필사진이 나오게 해야 함. -->
          </div>
          <span class="small-user-comment">
            <div style="margin:0">{{ user }} 님이 팔로우를 신청하셨습니다.</div>
          </span>
          <button class="agree-btn" @click="go(user, 1)">수락</button>
          <button class="deny-btn" @click="go(user, 0)">거절</button>
        </li>
      </div>
    </div>
    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet" />
    </div>
    <Footer />
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue';
import Footer from '@/components/layout/footer/Footer.vue';
import UserApi from '../../api/UserApi';

export default {
  name: 'App',
  props: ['stompClient', 'recvList'],
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      List: [],
      nickname: '',
      userName: '',
      followingName: '',
    };
  },
  created() {
    this.nickname = this.$store.state.nickname;
    this.connect();
  },
  watch: {
    recvList: function() {
      if (this.recvList.length > 0) this.List = this.recvList[this.nickname];
    },
  },
  methods: {
    connect() {
      if (this.recvList.length > 0) this.List = this.recvList[this.nickname];
    },
    go(u, n) {
      // WebSocket 의 알림 리스트에서 해당 항목 삭제
      let data = {
        memberName: u,
        followingName: this.nickname,
      };
      console.log('alarm', data);
      this.stompClient.send('/receive', JSON.stringify(data), {});

      if (n == 1) {
        // Follow 관련 테이블에서 해당 항목 추가
        UserApi.requestFollowUpdate(
          {
            fromNickname: u,
            toNickname: this.nickname,
            type: false,
          },
          () => {},
          () => {}
        );
      }
    },
  },
};
</script>

<style>
@import '../../components/css/alarm/alarm.css';
</style>
