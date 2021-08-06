<template>
  <div>
    <!-- 현재 뒤로가기 버튼이 없어서 url을 적어서 이동해야함 ㅜ -->
    <div class="inchatting-container">
      <!-- 상단 프로필 바 -->
      <div class="chatting-contact bar">
        <div class="chatting-pic stark"></div>
        <div class="name">
            Tony Stark
        </div>
        <div class="seen">
            Today at 12:56
        </div>
      </div>

      <!-- 메세지 나오는 칸 -->
      <div class="messages" id="chat">
      <div class="time">
          Today at 11:41
      </div>
      <div class="message mychatting">
          Hey, man! What's up, Mr Stark?
      </div>
      <div class="message">
          Kid, where'd you come from? 
      </div>
      <div class="message mychatting">
          Field trip!
      </div>
      <div class="message mychatting">
          Uh, what is this guy's problem, Mr. Stark?
      </div>
      <div class="message">
          Uh, he's from space, he came here to steal a necklace from a wizard.
      </div>
            <div class="message">
          Uh, he's from space, he came here to steal a necklace from a wizard.
      </div>
            <div class="message">
          Uh, he's from space, he came here to steal a necklace from a wizard.
      </div>
      <div class="message">
          <div class="typing typing-1"></div>
          <div class="typing typing-2"></div>
          <div class="typing typing-3"></div>
      </div>
      </div>
      <div class="inchatting-input">
        <i class="fas fa-camera"></i><input placeholder="Type your message here!" type="text" />
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
  @import "../../css/home/chatting/inchatting.css";
</style>