<template>
  <div class="likeBtn" @click="boardLiked" style="width: 3rem; display: flex; margin-right:5px;">
    <div v-if="likes == 0">
      <p class="m-0" style="color:#ff8f00"><i class="far fa-heart fa-lg"></i></p>
    </div>
    <div v-if="likes > 0">
      <p class="m-0" style="color:#ff8f00"><i class="fas fa-heart fa-lg"></i></p>
    </div>
    <p>{{ likelist.length }}</p>

    <div>
      <link
        href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
        rel="stylesheet"
      />
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'Like',
  props: ['boardItem'],
  data: () => {
    return {
      likelist: [],
      likes: 0,
      uid: 0,
    };
  },
  created() {
    this.uid = this.$store.state.uid;
    this.setLiked();
  },
  methods: {
    getLikeList: function() {
      let data;
      data = {
        bid: this.boardItem.bid,
      };

      http
        .get(`/board/like`, { params: data })
        .then(({ data }) => {
          if (data == null) {
            this.likelist = [];
          } else {
            this.likelist = data;
          }
        })
        .catch(() => {
          console.log('좋아요 리스트 에러');
        });
    },
    setLiked: function() {
      let data;
      data = {
        bid: this.boardItem.bid,
        uid: this.uid,
      };

      http
        .get(`/board/liked`, { params: data })
        .then(({ data }) => {
          this.likes = data.object;
          this.getLikeList();
        })
        .catch(() => {
          console.log('좋아요 로드 에러');
        });
    },
    boardLiked: function(e) {
      let data;
      data = {
        bid: this.boardItem.bid,
        uid: this.uid,
      };

      http
        .post(`/board/AddOrDeleteLike`, data)
        .then(({ data }) => {
          this.likes = data.object;

          this.getLikeList();
        })
        .catch(() => {
          console.log('좋아요 에러');
        });
    },
  },
};
</script>

<style>
.likeBtn {
  cursor: pointer;
}
</style>
