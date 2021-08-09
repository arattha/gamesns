<template>
  <div style="display: flex">
    <div><i class="far fa-comment fa-lg"></i></div>
    <p>{{reply_num}}</p>

    <div>
      <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
		</div>
  </div>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'Reply',
  props: ['boardItem'],
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