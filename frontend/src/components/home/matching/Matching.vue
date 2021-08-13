<template>
  <div>
    <Header/>
    <div class="matching-container">
      <div class="game-kind-btns">
        <!--
          <button class="hard-game game-btn">빡겜</button>
          <button class="funny-game game-btn">즐겜</button
        -->
      </div>
      <div class="select-boxes">
        <div class="game-select-box">
          <select v-model="selectedGame" class="select-box">
            <option value="1">리그오브레전드</option>
            <option value="2">좀비 vs 플랜트</option>
            <option value="3">후르츠닌자</option>
          </select>
          <p>원하는 게임을 선택하세요</p>
        </div>
        <div class="number-select-box">
          <select v-model="selectedPeople" class="select-box">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select>
          <p>원하는 인원을 선택하세요</p>
        </div>
      </div>
      <button class="matching-start-btn game-btn" id = 'matchStart' style="">함고?</button>
    </div>
    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
  name:'Matching',
  components: {
    Header,
    Footer
  },
  data(){
    return{
      selectedGame : "",
      selectedPeople: "",
    }
  },
  mounted(){
    const $matchStart =  document.querySelector('#matchStart');
    $matchStart.addEventListener('click',() => this.clickEvent());
  },
  methods: {
    // clickDummy(e){
    //   console.log(e);
    //   var type = e.target.innerText;
    //   if (type == '함고?') {
    //     this.join();
    //   } else if (type == '매칭중지') {
    //     this.cancel();
    //   }
    // },
    clickEvent(){
      let data = {
        selectedGame : this.selectedGame ,
        selectedPeople : this.selectedPeople,
      }
      this.$router.push({ name: 'MatchingStart', params: {data}});
    },
  },
}
</script>

<style>
  @import "../../css/home/matching/matching.css";
</style>