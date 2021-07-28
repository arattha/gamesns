<template>
    <div>
        <Header/>
        <div class="form">
            <h5 class="title">알림 - {{recvList.length}}개</h5>
            <div v-if="recvList.length > 0">
                <li class="list" v-for="(user, index) in recvList" :key="index">
                    <div>
                        <p>{{user}} 님이 팔로우를 신청하셨습니다.</p>
                        <b-button variant="success" @click="go(user, 1)">수락</b-button>
                        <b-button variant="danger" @click="go(user, 0)">거절</b-button>
                    </div>
                </li>
            </div>
        </div>
        <Footer/>
    </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import UserApi from '../../api/UserApi'

export default {
  name: 'App',
  props:["stompClient", "recvList"],
  components:{
      Header,
      Footer,
  },
  data() {
    return {
      userName: "",
      followingName: "",
      first: true,
    }
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.first = true;
    this.connect()
  },
  methods: {
    // sendMessage (e) {
    //   if(e.keyCode === 13 && this.userName !== '' && this.followingName !== ''){
    //     this.send()
    //     this.followingName = ''
    //   }
    // },    
    // send() {
    //   console.log("Send message:" + "조용일");
    //   if (this.stompClient && this.stompClient.connected) {
    //     const msg = {
    //       userName: "심찬인",
    //       followingName: "조용일"
    //     };
    //     this.stompClient.send("/receive", JSON.stringify(msg), {});
    //   }
    // },
    connect() {
      // const serverURL = "http://localhost:8080/alarm"
      // let socket = new SockJS(serverURL);
      // console.log("try connect!!!!!!")
      // console.log(this.socket);
      // this.stompClient = Stomp.over(this.socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      // this.stompClient.connect(
      //   {},
      //   frame => {
      //     // 소켓 연결 성공
      //     this.connected = true;
      //     console.log('소켓 연결 성공', frame);
      //   //   this.send();
      //     // 서버의 메시지 전송 endpoint를 구독합니다.
      //     // 이런형태를 pub sub 구조라고 합니다.
      //   //   this.stompClient.send("/first", res => {
      //   //       console("first", res);
      //   //   });
      //   if(this.first) {
      //       this.stompClient.send("/first", {}, {});
      //       console.log("first?")
      //       this.stompClient.subscribe("/firstSend", res => {
      //           console.log("here", res);
      //           this.recvList = res.body.replaceAll("[","").replaceAll("]","").replaceAll('"',"").split(",");
      //       })
      //       this.first = false;
      //   } else {
      //       this.stompClient.subscribe("/send", res => {
      //           console.log('구독으로 받은 메시지 입니다.', res.body);
      //           console.log(res.body);
      //           // console.log(res.body.replaceAll("[","").replaceAll("]","").replaceAll("\"","").split(","));
      //           // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
      //           this.recvList = res.body.replaceAll("[","").replaceAll("]","").replaceAll('"',"").split(",");
      //           console.log("recvList" + this.recvList);
      //           if(this.recvList == '') this.recvList = [];
      //       });
      //   }
      //   },
      //   error => {
      //     // 소켓 연결 실패
      //     console.log('소켓 연결 실패', error);
      //     this.connected = false;
      //   }
      // );        
    },
    go(u, n) {
        // WebSocket 의 알림 리스트에서 해당 항목 삭제
        let data = {
            userName: u,
            followingName: "조성표"
        }
        this.stompClient.send("/receive", JSON.stringify(data), {});
        // this.stompClient.subscribe("/send", res => {
        //         console.log('구독으로 받은 메시지 입니다.', res.body);
        //         console.log(res.body);
        //         // console.log(res.body.replaceAll("[","").replaceAll("]","").replaceAll("\"","").split(","));
        //         // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        //         this.recvList = res.body.replaceAll("[","").replaceAll("]","").replaceAll('"',"").split(",");
        //         console.log("recvList" + this.recvList);
        //         if(this.recvList == '') this.recvList = [];
        //     });

        if(n == 1) {

          // Follow 관련 테이블에서 해당 항목 추가
          UserApi
            .requestFollowUpdate(
              {
                fromNickname: u,
                toNickname: "조성표", 
                type: false,
              }
            ,(() => {})
            ,(() => {})
          )
        }
    },
  }
}
</script>

<style>
    @import "../../components/css/alarm/alarm.css";
</style>
