<template>
  <div>
    <Header />

    <div v-for="(item, index) in msgHistory" :key="index">
      <div v-if="item.nickname == nickname" class="msg right-msg">
        <img
          class="msg-img"
          :src="`http://i5c203.p.ssafy.io/api/account/file/` + uid"
        />

        <div class="msg-bubble">
          <div class="msg-info">
            <div class="msg-info-name">nickname</div>
          </div>

          <div class="msg-text">
            {{item.msg}}
          </div>
        </div>
      </div>
      <div v-else-if="item.nickname == yourNickname" class="msg left-msg">
        <img
          class="msg-img"
          :src="`http://i5c203.p.ssafy.io/api/account/file/` + uid"
        />

        <div class="msg-bubble">
          <div class="msg-info">
            <div class="msg-info-name">{{item.nickname}}</div>
            <!-- <div class="msg-info-time">12:45</div> -->
          </div>

          <div class="msg-text">
            {{item.msg}}
          </div>
        </div>
      </div>
      <div v-else>
        

          <div class="msg-text" style="text-align: center;">
            {{item.msg}}
          </div>
        </div>
      </div>
    

<!--     
    <div>
      <div style="text-align: center">
        <div class="mt-2 mb-4 pb-2" style="border-bottom:1px solid black">
          Message
        </div>
        <textarea
          v-model="textarea"
          style="width: 300px ; height:440px"
        ></textarea>
      </div> -->
      <div>
        <div v-if="!chatPossible">
          <p>상대방의 입장을 기다리고 있습니다.</p>
        </div>
        <div v-else>
          <label>Your Message</label>
          <input v-model="message" />
          <button class="md-primary md-raised" @click="sendMessage()">
            Submit
          </button>
        </div>
      </div>
    <Footer />
  </div>
</template>

<script>
import Header from "@/components/layout/header/Header.vue";
import Footer from "@/components/layout/footer/Footer.vue";

export default {
  name: "InChatting",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      id: "",
      uid: '',
      nickname: "",
      yourNickname: "",
      textarea: "",
      message: "",
      chatPossible: false,
      msgHistory: [],
    };
  },
  created() {
    this.id = this.$socketio.id;
    this.uid = this.$store.state.uid;
    this.nickname = this.$store.state.nickname;
    this.yourNickname = this.$route.params.yourNickname;

    // 입장했다고 node js 서버에 알려서 join_num 최신화하기
    // console.log("join 전에 data" + " yournickname : " + this.yourNickname);
    // this.$socketio.emit('join_num', {
    //   myNickname : this.nickname,
    //   yourNickname : this.yourNickname,
    //   type : 0,
    // });

    // msg 받기
    this.$socketio.on("recvMsg", (data) => {
      console.log("i received msg", data);
      // 받는 유저의 닉네임이 같을 때만
      if (data.sender == this.yourNickname) {
        // this.textarea += data.msg;

        


      }
    });
  },
  mounted() {
    this.$socketio.on("getJoinNum", (data) => {
      console.log("here is getJoinNum", data);
      if (data == 2) {
        this.chatPossible = true;
      } else {
        this.chatPossible = false;
      }
    });
  },
  methods: {
    sendMessage() {
      this.$socketio.emit("sendMsg", {
        id: this.id,
        myNickname: this.nickname,
        yourNickname: this.yourNickname,
        message: this.message,
        type: 2,
      });

      this.textarea += this.nickname + " : " + this.message + "\n";
      this.message = "";
    },
  },
  beforeDestroy() {
    this.$socketio.emit("sendMsg", {
      myNickname: this.nickname,
      yourNickname: this.yourNickname,
      type: 1,
    });

    // this.$socketio.emit("rmRooms", {
    //   myNickname : this.nickname,
    //   yourNickname : this.yourNickname,
    // })

    // this.$socketio.emit('join_num', {
    //   myNickname : this.nickname,
    //   yourNickname : this.yourNickname,
    //   type: -1,
    // })
  },
};
</script>

<style>
@import "../../css/home/chatting/inchatting.css";
</style>
