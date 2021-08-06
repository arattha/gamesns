<template>
  <div>
    <Header/>
    <div class="chatting-container">
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
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from '@/components/layout/header/Header.vue'
import Footer from '@/components/layout/footer/Footer.vue'
export default {
  name:'Chatting',
  components: { 
      Header,
      Footer,
  },
  methods: {
    goToInChatting: function() {
      this.$router.push('/inchatting');
    },
  },
}
</script>

<style>
  @import "../../css/home/chatting/chatting.css";
</style>