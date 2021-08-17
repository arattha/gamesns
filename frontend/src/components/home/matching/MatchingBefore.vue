<template>
  <div>
    <Header/>
    <div class="matching-container">
      <div class="game-kind-btns">
      </div>
      <div><a href="https://discord.gg/BbcYwNcFv9">함고 디스코드 서버</a></div>
      <div class="select-boxes">
        <div class="game-select-box">
          <input
          v-model="discordId"
          type="text"
          placeholder="Example#1234"
          :disabled="validated == 1"
          />
        <div class="error-text">{{error}}</div>
        <p>디스코드 아이디를 해시태그까지 적어주세요</p>
        </div>
        <button class="matching-start-btn game-btn" id = 'discordCheck' style="" :disabled="gamerCheck == 0">접속 확인</button>
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
      gamerCheck: 0,
      error : "",
      regex: /#[0-9]{4,4}/,
    }
  },
  watch: {
    discordId() {
      this.onUpdate();
    },
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
          this.error = "";
        } else{
          this.validated = 0;
          this.error = "접속 중이지 않은 아이디입니다.\n 함고 채널에 들어와 주세요!";
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
    onUpdate(){
      let gamerTag = this.regex.exec(this.discordId);
      this.error = "해시태그까지 적어주세요!"
      if(gamerTag){
        this.gamerCheck = 1;
      } else { 
        this.gamerCheck = 0;
      }

    }
  },
}
</script>
<style>
  
</style>