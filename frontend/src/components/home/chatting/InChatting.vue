<template>
  <div>
    <Header />
    <!-- 현재 뒤로가기 버튼이 없어서 url을 적어서 이동해야함 ㅜ -->
    <!-- <div class="inchatting-container"> -->
          <div>
            <div>
              <div>Message</div>
              <textarea
                v-model="textarea"
                style="height:500px"
              ></textarea>
            </div>
            <div>
              <label>Your Message</label>
              <input v-model="message" />
              <button class="md-primary md-raised" @click="sendMessage()"
                >Submit</button
              >
            </div>
          </div>
          <Footer />
        </div>
      
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'

export default {
  name: "InChatting",
  components: {
      Header,
      Footer,
  },
  data() {
    return {
      id: "",
      nickname: "",
      yourNickname: "",
      textarea: "",
      message: "",
    };
  },
  created() {
    this.id = this.$socketio.id;
    this.nickname = this.$store.state.nickname;
    this.yourNickname = this.$route.params.yourNickname; 
    console.log("yourNickname2",this.yourNickname);
    // msg 받기
    this.$socketio.on("recvMsg", (data) => {
      if(data.sender == this.yourNickname) this.textarea += data.sender + " : " + data.message + "\n";
    });
  },
  methods: {
    sendMessage() {
      this.$socketio.emit("sendMsg", {
        id: this.id,
        myNickname: this.nickname,
        yourNickname : this.yourNickname,
        message: this.message,
      });

      this.textarea += this.nickname + " : " + this.message + "\n";
      this.message = "";
    },
  },
  beforeDestroy(){
    this.$socketio.emit("rmRooms", {
      myNickname : this.nickname,
      yourNickname : this.yourNickname,
    })
  }
};
</script>

<style>
@import "../../css/home/chatting/inchatting.css";
</style>
