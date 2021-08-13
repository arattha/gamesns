<template>
  <div>
    <Header/>
      <div class="hashtag-guide-wording-div">
        <h4 style="margin:0px; font-family: 'Nanum Gothic', sans-serif;">
          #{{$route.params.hashtag}}
        </h4>
      </div>

      <div class="mainfeed" style="margin-top:0px">
        <!-- <div v-if="searched_hashtag_boards==0">
          검색결과가 없습니다.
        </div> -->
        <div class="" @scroll.passive="handleScroll">
          <ModalFeed v-if="isModalViewed" @close-modal="modalClose()" :boardItem="hashtag_board" />
          <div v-for="(hashtag_board, index) in searched_hashtag_boards" :key="index">
            <FeedItem @showModal="modalShow" :boardItem="hashtag_board" />
          </div>
        </div>
      </div>

    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet" />
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import FeedItem from '@/components/feed/FeedItem.vue';
import ModalFeed from '@/components/feed/ModalFeed.vue';
import UserApi from "../../../api/UserApi";

export default {
  name: 'Hashtagsearch',
  components: {
    Header,
    Footer,
    FeedItem,
    ModalFeed
  },
  data: () => {
    return {
      isModalViewed: false,
      search:"",
      searched_hashtag_boards:[],
    }
  },
  created() {
    this.search = '#' + this.$route.params.hashtag;
    UserApi.requestHashtagBoard(
          {
            hashtag: this.search,
          },
          (res) => {
            console.log(res);
            this.searched_hashtag_boards = res;
          },
          () => {}
        );
  },
  methods: {
    modalShow(item) {
      this.isModalViewed = !this.isModalViewed;
      this.temp = item;
      document.body.style.overflow = 'hidden';
    },
  },
}
</script>

<style>
  .hashtag-guide-wording-div {
    display: flex;
    justify-content: center;
    padding: 15px 0px;
    border-bottom: 1px solid #f4f4f4;
    font-family: 'Nanum Gothic', sans-serif;
  }

  .hashtag-feeds-container{
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .eachfeed {
    width: 70vw;
    height: 70vw;
    border: 1px solid black;
  }

</style>