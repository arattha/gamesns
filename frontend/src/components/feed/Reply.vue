<template>
  <div style="display:flex; align-items: center;">
      <i class="far fa-comment fa-lg m-0" style="width:19.5px;height:19.5px;"></i>
    <p v-if="now_reply_num > 0">{{now_reply_num}}</p>
    <p v-else>{{reply_num}}</p>

    <div>
      <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
		</div>
  </div>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'Reply',
  props: ['boardItem','now_reply_num'],
  data: () => {
    return {
      reply_num:0,
    };
  },
  created() {
    let data = {
      bid : this.boardItem.bid
    }
      http
      .get('/reply/cnt', {params:data})
      .then(({data}) => {
        this.reply_num = data;
      })
      .catch((err) => {
        console.log('reply num 에러입니다')
        console.log(err)
      })
  },
}
</script>

<style>

</style>