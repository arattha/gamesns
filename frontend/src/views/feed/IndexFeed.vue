<template>
  <div class="feed newsfeed">
    <div class="wrapB">
      <h1>뉴스피드</h1>
      <FeedItem v-for="(boardItem,index) in boardItems" :key="index" :boardItem ="boardItem"/>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import "../../components/css/feed/feed-item.scss";
import "../../components/css/feed/newsfeed.scss";
import FeedItem from "../../components/feed/FeedItem.vue";

export default {
  props: ["boardItem"],
  components: { FeedItem },
  data(){
    return{
      //boardItems:[],
    }
  },
  created(){
    this.getBoardItems();
  },
  methods: {
    ...mapActions(["getBoardItems"]),
  },
  computed: {
    ...mapGetters(["boardItems"])
  },
  watch:{
    boardItems: function(val){
      console.log("test");
      console.log(val);
    }
  }

};
</script>

