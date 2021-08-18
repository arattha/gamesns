<template>
  <div>
    <Header/>
    <div class="matching-container">
      <!-- <div class="game-kind-btns">
      </div> -->
      <div class="discord-link-div">
        <a style="color:white;margin:0px;text-decoration:none" href="https://discord.gg/BbcYwNcFv9" target="_blank">함고 서버링크</a>
      </div>
        <div class="discord-id-box">
          <input
          class="discord-id-input"
          v-model="discordId"
          type="text"
          placeholder="Example#1234"
          :disabled="validated == 1"
          />
          <p id="discord-comment">디스코드 아이디를 해시태그까지 적어주세요</p>
        </div>
      <button class="matchingBefore-btn" @click="discordCheckClick" id = 'discordCheck' >접속 확인</button>
      <button class="matchingBefore-btn" id = 'matchNext' :disabled="validated == 0">다음</button>
    </div>
    <Footer/>
  </div>
</template>
<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import UserApi from '@/api/UserApi'
var clicks = 0; 

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
          const discordComment = document.querySelector("#discord-comment")
          discordComment.style.color = "white";
          const discordCheckBtn = document.querySelector("#discordCheck")
          discordCheckBtn.style.backgroundColor = "#FFAF0A"
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
    discordCheckClick(){
        if (clicks >= 10) { 
      alert("잠시만 기다려주십시오");
      clicks = 0; 
      } 
      clicks += 1; 
    }
  },
}
</script>
<style>
  @import "../../css/home/matching/matchingBefore.css";
  @import "../../css/home/matching/matchingBefore.scss";
</style>