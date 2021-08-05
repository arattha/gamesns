<template>
  <div>
    <Header/>
    <div class="chatting-container">
      <div class="chatting-contact" @click="goToInChatting">
        <div class="chatting-pic rogers"></div>
        <div class="badge">
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
        <div class="badge">
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
        <div class="badge">
          3
        </div>
        <div class="message">
          I like this one
        </div>
      </div>
      <div class="chatting-contact">
        <div class="chatting-pic danvers"></div>
        <div class="badge">
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
        <div class="badge">
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
        <div class="badge">
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

@import url("https://fonts.googleapis.com/css?family=Red+Hat+Display:400,500,900&display=swap");
.chatting-container {
  font-family: Red hat Display, sans-serif;
  font-weight: 400;
  line-height: 1.25em;
  letter-spacing: 0.025em;
  color: #333;
  background:white;
  display: flex;
  flex-direction: column;
  height: 100%;
  /* width: 50vh; */
}

.center {
  position: absolute;
  top: 50%;
  left: calc(50% + 12rem);
  transform: translate(-50%, -50%);
}

.chatting-pic {
  width: 3.8rem;
  height: 3.8rem;
  background-size: cover;
  background-position: center;
  border-radius: 50%;
}

.chatting-contact {
  position: relative;
  margin: 0.5rem 0.5rem;
  padding-left: 5rem;
  height: 4.5rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.chatting-contact .chatting-pic {
  position: absolute;
  left: 0%;
}
.chatting-contact .name {
  font-weight: 500;
  margin-bottom: 0.125rem;
}
.chatting-contact .message, .chatting-contact .seen {
  font-size: 0.9rem;
  color: #999;
}
.chatting-contact .badge {
  box-sizing: border-box;
  position: absolute;
  width: 1.5rem;
  height: 1.5rem;
  text-align: center;
  font-size: 0.9rem;
  border-radius: 1rem;
  top: 0;
  left: 2.5rem;
  background: #333;
  color: white;
}

.chatting-pic.stark {
  background-image: url("https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/7/73/SMH_Mentor_6.png");
}

.chatting-pic.banner {
  background-image: url("https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/4/4f/BruceHulk-Endgame-TravelingCapInPast.jpg");
}

.chatting-pic.thor {
  background-image: url("https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/9/98/ThorFliesThroughTheAnus.jpg");
}

.chatting-pic.danvers {
  background-image: url("https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/0/05/HeyPeterParker.png");
}

.chatting-pic.rogers {
  background-image: url("https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/7/7c/Cap.America_%28We_Don%27t_Trade_Lives_Vision%29.png");
}

.chatting-container hr {
  margin:0px;
}
</style>