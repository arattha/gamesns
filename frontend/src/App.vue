<template>
  <div id="app">
    <router-view :stompClient="stompClient" :recvList="recvList"></router-view>
  </div>
</template>
<script>
import "./components/css/style.scss";
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: "app",
  data(){
    return{
      first: true,
      recvList:[],
    }
  },
  created() {
    this.connect();
  },
  methods: {
    connect() {
      const serverURL = "http://localhost:8080/alarm"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
        //   this.send();
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
        //   this.stompClient.send("/first", res => {
        //       console("first", res);
        //   });
        if(this.first) {
            this.stompClient.send("/first", {}, {});
            console.log("first?")
            this.stompClient.subscribe("/firstSend", res => {
                console.log("here", res);
                this.recvList = res.body.replaceAll("[","").replaceAll("]","").replaceAll('"',"").split(",");
            })
            this.first = false;
        }

        this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);
            console.log(res.body);
            // console.log(res.body.replaceAll("[","").replaceAll("]","").replaceAll("\"","").split(","));
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList = res.body.replaceAll("[","").replaceAll("]","").replaceAll('"',"").split(",");
            console.log("recvList" + this.recvList);
            if(this.recvList == '') this.recvList = [];
        });
        
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    }
  }
};
</script>
