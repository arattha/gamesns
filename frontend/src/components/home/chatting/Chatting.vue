<template>
  <div>
    <Header />
    <!-- <div class="chatting-container">
      <div class="chatting-contact" @click="goToInChatting">
        <div class="chatting-pic rogers"></div>
        <div class="chatting-badge">
          1
        </div>
        <div class="name">
          Steve Rogers
        </div>
        <div class="message">
          That is America's ass 🇺🇸🍑
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic stark"></div>
        <div class="name">
          Tony Stark
        </div>
        <div class="message">
          Uh, he's from space, he came here to steal a necklace from a wizard.
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic banner"></div>
        <div class="chatting-badge">
          1
        </div>
        <div class="name">
          Bruce Banner
        </div>
        <div class="message">
          There's an Ant-Man *and* a Spider-Man?
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic thor"></div>
        <div class="name">
          Thor Odinson
        </div>
        <div class="chatting-badge">
          3
        </div>
        <div class="message">
          I like this one
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic danvers"></div>
        <div class="chatting-badge">
          2
        </div>
        <div class="name">
          Carol Danvers
        </div>
        <div class="message">
          Hey Peter Parker, you got something for me?
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic danvers"></div>
        <div class="chatting-badge">
          2
        </div>
        <div class="name">
          Carol Danvers
        </div>
        <div class="message">
          Hey Peter Parker, you got something for me?
        </div>
      </div>
       <div class="chatting-contact">
        <div class="chatting-pic danvers"></div>
        <div class="chatting-badge">
          2
        </div>
        <div class="name">
          Carol Danvers
        </div>
        <div class="message">
          Hey Peter Parker, you got something for me?
        </div>
      </div>
    </div> -->
    <div class="search-bar">
      <input
        v-model="search"
        class="search__input"
        type="text"
        placeholder="검색"
      />
      <div
        v-for="(suggest, index) in searched"
        :key="index"
        id="suggestion_box"
        @click="addRoom(suggest)"
      >
        <!--<img />-->
        {{ suggest.nickname }}
      </div>
    </div>

    <div>{{ rooms }}</div>

    <!-- 채팅 룸 리스트 반환 -->
    <div v-for="(item, index) in rooms" :key="index" @click="chatLink(item)">
      {{ index }} : {{ item }}
    </div>

    <Footer />
  </div>
</template>

<script>
import Header from "@/components/layout/header/Header.vue";
import Footer from "@/components/layout/footer/Footer.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "Chatting",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      id: "",
      nickname: "",
      rooms: [],
      search: "",
    };
  },
  created() {
    this.id = this.$socketio.id;
    this.nickname = this.$store.state.nickname;

    // this.getRooms();
    // this.$socketio.emit('getRooms', this.$store.state.nickname);
  },
  mounted() {
    this.$socketio.emit("callRooms", this.nickname);

    this.$socketio.on("getRooms", (data) => {
      console.log("hi rooms", data);
      this.rooms = data;
    });
  },

  watch: {
    search: function(val) {
      if (val != "") this.searchUser(val);
    },
  },
  computed: {
    ...mapGetters(["searched"]),
  },
  methods: {
    ...mapActions(["searchUser"]),
    // getRooms(){

    //   this.$socketio.emit('callRooms', this.nickname);

    //   this.$socketio.on('getRooms', (data) => {
    //     console.log("hi rooms", data);
    //     this.rooms = data;
    //   })
    // },
    chatLink(yourNickname) {
      this.$socketio.emit("sendMsg", {
        id: this.id,
        myNickname: this.nickname,
        yourNickname: yourNickname,
        // 0 : join, 1 : leave
        type: 0,
      });

      this.$router.push({
        name: "InChatting",
        params: { yourNickname: yourNickname },
      });
    },
    addRoom(suggest) {
      // 기존의 rooms 배열 안에 검색 후 선택한 유저가 있는지 확인할 변수
      var flag = true;
      for (const item of this.rooms) {
        // rooms 안에 체크할 유저가 있다면 flag = false
        if (suggest.nickname == item) {
          flag = false;
          break;
        }
      }

      // 새로 추가해야 할 유저
      if (flag) {
        this.rooms.push(suggest.nickname);
      }
    },
  },
};
</script>

<style>
@import "../../css/home/chatting/chatting.css";
</style>
