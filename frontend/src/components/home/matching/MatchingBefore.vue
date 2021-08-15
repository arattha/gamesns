<template>
  <div>
    <Header/>
    <div class="matching-container">
      <div class="game-kind-btns">
      </div>
      <div>여기에 디스코드 링크</div>
      <div class="select-boxes">
        <div class="game-select-box">
          <input
          v-model="discordId"
          type="text"
          placeholder="Example#1234"
          :disabled="validated == 1"
          />
        <p>디스코드 아이디를 해시태그까지 적어주세요</p>
        </div>
        <button class="matching-start-btn game-btn" id = 'discordCheck' style="">접속 확인</button>
      </div>
      <button class="matching-start-btn game-btn" id = 'matchNext' style="" :disabled="validated == 0">다음</button>
    </div>
    <Footer/>
  </div>
</template>
<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import UserApi from '@/api/UserApi'

export default {
  name:'MatchingBefore',
  components: {
    Header,
    Footer
  },
  mounted(){
    const $matchStart =  document.querySelector('#matchNext');
    $matchStart.addEventListener('click',() => this.next());

    const $discordCheck =  document.querySelector('#discordCheck');
    $discordCheck.addEventListener('click',() => this.check());
  },
  data(){
    return {
      discordId : "",
      validated : 0,
    }
  },
  methods: {
    check(){
      let data = {
        userTag : this.discordId
      }

      UserApi
      .requestDiscord(data,
      ((res) => {
        if(res){
          this.validated = 1;
        } else{
          this.validated = 0;
        }
      }),
      (() => {}))
    },
    next(){
      console.log("test")
      let data = {
        discordId : this.discordId,
      }
      this.$router.push({ name: 'Matching', params: {data}});
    },
  },
}
</script>
<style>
  
</style>