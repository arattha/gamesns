<template>
<div>
  <Header/>
  <div style="background-color: #fafafa;">
  <div class="mainfeed">
    <div class="" @scroll.passive="handleScroll">
      <ModalFeed v-if="isModalViewed" @close-modal="modalClose()" :boardItem="temp"/>
      <div v-for="(boardItem,index) in boardItems" :key="index" @click="modalShow(boardItem)">
        <FeedItem :boardItem ="boardItem"/>
      </div>
    </div>
  </div>
  </div>
  <Footer/>
</div>
</template>

<script>
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/mainfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import ModalFeed from '../../components/feed/ModalFeed.vue';
import UserApi from '../../api/UserApi';
var timer;
export default {
  components: { 
      FeedItem,
      Header,
      Footer,
      ModalFeed, 
    },
    data(){
      return{
        isModalViewed: false,
        temp: null,
        uid: 0,
        nickname: "",
        boardItems: [],
        timer : null,
      }
  },
  created(){
    this.uid = this.$store.state.uid;
    this.nickname = this.$store.state.nickname;
    this.getBoardItems();
  },
  mounted(){
    window.addEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
        
      let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
      let windowHeight = window.innerHeight; // 스크린 창
      let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

      if(( Math.abs(parseInt(fullHeight) - parseInt(scrollLocation + windowHeight)) < 3  ) && parseInt(scrollLocation) != 0){
        if( timer == null ){
          this.getBoardItems(); //다음 뉴스피드 10개를 가져오는 함수
          timer = setTimeout(function() {
          timer = null;
          }, 300);
        }
      }
    },
    getBoardItems(){
      let data;
      if (this.boardItems.length == 0) {
        data = {
          uid: this.uid,
        };
      } else {
        data = {
          uid: this.uid,
          bid: String(this.boardItems[this.boardItems.length - 1].bid),
        };
      }
      UserApi
        .requestFeedList( data ,
          ((list) => {
            this.boardItems = this.boardItems.concat(list);
          }), 
          (() => {
            alert("메인피드 가져오기 오류!");
            })
        );
    },
    modalShow(item){
      this.isModalViewed = !this.isModalViewed;
      this.temp = item;
      document.body.style.overflow = 'hidden';
    },
    modalClose(){
      this.isModalViewed = !this.isModalViewed;
      this.temp = null;
      document.body.style.overflow = 'scroll';
    }
  },
  beforeDestroy(){
    this.boardItems = [];
    window.removeEventListener('scroll', this.handleScroll);
    this.modalClose()
  }

};
</script>

<!--

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

-->