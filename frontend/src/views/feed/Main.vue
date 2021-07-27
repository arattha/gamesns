<template>
  <div class="feed newsfeed" >
    <Header/>
    <div class="wrapB" @scroll.passive="handleScroll">
      <h1>뉴스피드</h1>
      <div v-for="(boardItem,index) in boardItems" :key="index" @click="isModalViewed = true">
        <FeedItem :boardItem ="boardItem"/>
        <ModalFeed v-if="isModalViewed" @close-modal="isModalViewed = false">
        </ModalFeed>
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import ModalFeed from '../../components/feed/ModalFeed.vue';

export default {
  props: ["boardItem"],
  components: { 
      FeedItem,
      Header,
      Footer,
      ModalFeed, 
    },
    data(){
    return{
      isModalViewed: false,
    }
  },
  created(){
    this.getBoardItems();
    window.addEventListener('scroll', this.handleScroll);
  },
  methods: {
    ...mapActions(["getBoardItems"]),
    // 무한 스크롤 정의
    handleScroll(e) {
      //console.log(e);
      //console.log(window);
      // console.log(document.documentElement.scrollTop);
      // console.log(document.body.parentNode.scrollTop);
      // console.log("--------------------");
      //const body = document.body;
      //const html = document.documentElement; 
      // console.log(body.scrollHeight);
      // console.log(body.offsetHeight);
      // console.log(body.clientHeight);
      
      
      // console.log(html.scrollHeight);
      // console.log(html.offsetHeight);
      // console.log(html.clientHeight);
      // console.log("--------------------");
      // console.log(this.boardItems[this.boardItems.length-1]);
      
      // console.log(this.getDocumentHeight());
      //const { scrollHeight, scrollTop, clientHeight } = e.target;
      //const isAtTheBottom = scrollHeight === scrollTop + clientHeight;
      // 일정 한도 밑으로 내려오면 함수 실행

      let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
      let windowHeight = window.innerHeight; // 스크린 창
      let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

      if(scrollLocation + windowHeight >= fullHeight){
        console.log('끝')
        this.getBoardItems();
      }


      //if (isAtTheBottom) this.handleLoadMore();
    },
    getDocumentHeight() { //창의 총높이
      //const body = document.body; 
      //const html = document.documentElement; 
      let scrollHeight = Math.max(
                          document.body.scrollHeight, document.documentElement.scrollHeight,
                          document.body.offsetHeight, document.documentElement.offsetHeight,
                          document.body.clientHeight, document.documentElement.clientHeight
                        );
      return scrollHeight;
      },
    // 내려오면 api 호출하여 아래에 더 추가, total값 최대이면 호출 안함
    // handleLoadMore() {
    //   console.log("end of the scroll");
    //   // if (this.notifications.length < this.total) {
    //   //   const params = {
    //   //     limit: this.params.limit,
    //   //     page: this.params.page + 1
    //   //   };
    //   //   this.$store.commit(
    //   //     "notification/SET_PARAMS",
    //   //     this.filterValue ? { ...params, type: this.filterValue } : params
    //   //   );
    //   //   this.dispatchGetNotifications(false);
    //   // }
    // },

    // // 스크롤을 맨위로 올리고 싶을 때
    // handleClickTitle() {
    //   this.$refs["notification-list"].scroll({ top: 0, behavior: "smooth" });
    // },

    // // 새로고침
    // handleClickRefresh() {
    //   this.$refs["notification-list"].scroll({ top: 0 });
    //   this.dispatchGetNotifications(true);
    // },

    // 처음 렌더링시 이전 알림 불러오기 or reset=true시 새로고침, false시 이전 목록에 추가
    // dispatchGetNotifications(reset) {
    //   this.$store.dispatch("notification/getNotifications", reset);
    // }}
    show(){
      this.showModal = !this.showModal;
    }
  },
  computed: {
    ...mapGetters(["boardItems"])
  },
  watch:{
    isModalViewed: function(val){
      console.log(val);
    }
  },
  destroyed(){
    window.removeEventListener('scroll', this.handleScroll);
  }

};
</script>

