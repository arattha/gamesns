<template>
	<div class="b-select-wrap">
		<select v-model="score" class="form-control b-select">
      <option v-for="(op, index) in options" :key="index" :value="op.value">{{ op.name }}</option>
    </select>
		<input type="submit" @click="addManner">
	</div>
</template>

<script>
import http from '@/util/http-common';

export default {
  name: 'MannerSelect',
  props:['userInfo'],
	data: () => {
		return {
      uid: 0,
			score: 0,
			options: [
				{ 
          value: 1,
          name: '너무 싫어요!'
        },
        { 
          value: 2,
          name: '싫어요!'
        },
        { 
          value: 3,
          name: '그냥 그래요'
        },
        { 
          value: 4,
          name: '좋아요'
        },
        { 
          value: 5,
          name: '너무 좋아요!'
        },
			]
		}
	},
  created() {
      if (this.userInfo == null) {
        this.uid = this.$store.state.uid;
      } else {
        this.uid = this.userInfo.uid;
      }
      console.log('아아아아아')
      console.log(this.userInfo)
    },
  methods: {
    addManner: function() {

      // console.log(this.score)

      let data = {
        uid: String(this.uid),
        score: parseInt(this.score)
      };

      console.log('dkdkljdkdsajlkadsklj')
      console.log(data)

      http
      .put('/member/manner', data)
      .then(({data}) => {
        console.log('추가')
        console.log(data)
      })
      .catch((err) => {
        console.log('addmanner 실패')
        console.log(data)
        console.log(err)

      })
    }
  }
}
</script>

<style>

	.b-select-wrap {
    border: none;
    color: #1092C9;
    overflow: hidden;
    position: relative;
    border-radius: 3px;
}

.b-select-wrap::after {
    content: "⌄";
    position: absolute;
    right: 0px;
    top: -15px;
    z-index: 1;
    text-align: center;
    width: 10%;
    height: 100%;
    pointer-events: none;
    font-size: 35px;
}

.b-select {
    padding: 5px 15px;
    background-color: white;
    border: 0;
    outline: none;
    font-size: 22px;
    -webkit-appearance: none; /* for webkit browsers */
    -moz-appearance: none; /* for firefox */
    appearance: none; /* for modern browsers */
}

/* remove default caret for ie */
.b-select::-ms-expand {
   display:none;
}

</style>