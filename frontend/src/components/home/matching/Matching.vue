<template>
  <div>
    <Header/>
    <div class="matching-container">
      <div class="game-kind-btns">
          <button class="hard-game game-btn">빡겜</button>
          <button class="funny-game game-btn">즐겜</button>
      </div>
      <div class="select-boxes">
        <div class="game-select-box">
          <select class="select-box">
            <option value="1">리그오브레전드</option>
            <option value="2">좀비 vs 플랜트</option>
            <option value="3">후르츠닌자</option>
          </select>
          <p>원하는 게임을 선택하세요</p>
        </div>
        <div class="number-select-box">
          <select class="select-box">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select>
          <p>원하는 인원을 선택하세요</p>
        </div>
      </div>
      <button class="matching-start-btn game-btn" id="btnJoin" style="">함고?</button>
    </div>



    <div>
      <link href="http://fonts.googleapis.com/earlyaccess/nanumgothic.css" rel="stylesheet">
    </div>

    <div id="main-content" class="container">
      <div class="row">
        <textarea id="chat-content" v-model="textarea" rows="20" style="width:100%; height:50%;" readonly></textarea>
      </div>
      <div class="row" id="chat-action-div"></div>
      <!--<button class="matching-start-btn game-btn" id="btnJoin" style="">Join</button>-->
      <!--<button id="btnSend"> 보내기 </button>-->
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import http from '@/util/http-common.js'

export default {
  name:'Matching',
  components: {
    Header,
    Footer
  },
  data(){
    return{
      textarea : "",
      socket : null,
      stompClient : null,
      sessionId : null,
      chatRoomId : null,
      joinInterval : null,
      message : "",
    }
  },
  mounted(){
    const $joinEvent =  document.querySelector('#btnJoin');
    //console.log($joinEvent.innerText);

    $joinEvent.addEventListener('click',(e) => this.clickEvent(e));

    //const $joinSend = document.querySelector('#btnSend');
    //$joinSend.addEventListener('click',() => this.sendMessage());

  },
  methods: {
    clickEvent(e){
      console.log(e);
      var type = e.target.innerText;
      if (type == '함고?') {
        this.join();
      } else if (type == '매칭중지') {
        this.cancel();
      }
    },
    join(){

      document.querySelector('#btnJoin').innerText = '매칭중지';
      this.updateText('waiting anonymous user', false);
      this.joinInterval = setInterval(() => {
        this.updateText('.', true);
      }, 1000);

      http
        .get("/matching/join", { params: { gameName : "LeagueOfLegends" } })
        .then((matchingResponse) => {

          console.log('Success to receive join result.');
          //console.log(matchingResponse.data);

          clearInterval(this.joinInterval);
          if(matchingResponse.data.responseResult == 'SUCCESS') {

            this.sessionId = matchingResponse.data.sessionId;
            this.chatRoomId = matchingResponse.data.chatRoomId;
            //this.updateTemplate('chat');
            this.updateText('>> Connect anonymous user :)', false);
            this.connectAndSubscribe();

          } else if(matchingResponse.data.responseResult == 'CANCEL') {

            this.updateText('>> Success to cancel', false);
            document.querySelector('#btnJoin').innerText = '함고?';

          } else if(matchingResponse.data.responseResult == 'TIMEOUT') {

            this.updateText('>> Can`t find user :(', false);
            document.querySelector('#btnJoin').innerText = '함고?';

          }
        })
        .catch((jqxhr) => {
          clearInterval(this.joinInterval);

          if (jqxhr.status == 503) {
            this.updateText('>>> Failed to connect some user :( Plz try again', true);
          } else {
            this.updateText(jqxhr, true);
          }
          console.log(jqxhr);
        })

    },
    cancel(){
      http
        .get("/matching/cancel", { params: { gameName : "LeagueOfLegends" } })
        .then(() => {
          this.updateText('', false);
        })
        .catch((jqxhr) => {
          console.log(jqxhr);
          alert('Error occur. please refresh');
        })
        clearInterval(this.joinInterval);
    },
    connectAndSubscribe(){
      console.log(this.stompClient);
      if(this.stompClient == null ||  ! this.stompClient.connected) {
        const serverURL = "http://localhost:8080/matching"
        var socket = new SockJS(serverURL);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({matchingRoomId : this.chatRoomId}, frame => {
          console.log('Connected: ' + frame);
          this.subscribeMessage();
        })
      } else {
        this.subscribeMessage();
      }
    },
    disconnect(){
      if(this.stompClient !== null) {
        this.stompClient.disconnect();
        this.stompClient = null;
        //this.updateTemplate('wait');
      }
    },
    // sendMessage(){ // 이거 이제 필요없음
    //   console.log('Check.. >> ', this.stompClient);
    //   console.log('send message.. >> ');
    //   //var $chatTarget = document.querySelector('#chat-message-input');
    //   //var message = $chatTarget.val();
    //   //$chatTarget.val('');

    //   var matchingMessage = {
    //     messageType    : 'CHAT',
    //     senderSessionId : this.sessionId,
    //     message : "채팅됨 ㅋㅋ"
    //   };

    //   this.stompClient.send('/chat.message/' + this.chatRoomId,JSON.stringify(matchingMessage),{});
    // },
    subscribeMessage(){
      this.stompClient.subscribe('/topic/chat/' + this.chatRoomId, resultObj => {
      console.log('>> success to receive message\n', resultObj.body);
        var result = JSON.parse(resultObj.body);
        var message = '';

        if (result.messageType == 'CHAT') {
          if (result.senderSessionId === this.sessionId) {
            message += '[Me] : ';
          } else {
            message += '[Anonymous] : ';
          }

          message += result.message + '\n';
        } else if (result.messageType == 'DISCONNECTED') {
          message = '>> Disconnected user :(';
          this.disconnect();
        }
        this.updateText(message, true);
      });
    },
    // updateTemplate(type){
    //   var source;
    //   if (type == 'wait') {
    //     source = document.querySelector('#wait-chat-template').innerHTML;
    //   } else if (type == 'chat') {
    //     source = document.querySelector('#send-chat-template').innerHTML;
    //   } else {
    //     console.log('invalid type : ' + type);
    //     return;
    //   }
    //   //var template = Handlebars.compile(source);
    //   //var $target = document.querySelector('#chat-action-div');
    //   //$target.empty();
    //   //$target.append(template({}));
    // },
    updateText(message,append){
      if (append) {
        this.textarea += message;
      } else {
        this.textarea = message;
      }
    },
  },
  destroyed(){
    this.disconnect();
  },
}
</script>

<style>
  @import "../../css/home/matching/matching.css";
</style>