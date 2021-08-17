<template>
  <div>
    <Header />
    <div>{{ discordUrl }}</div>
    <div v-for="(user,idx) in matchedUser" :key="idx">
      <Nickname :userInfo="user"/>
      <Manner :userInfo="user"/>
      <MannerSelect :userInfo="user"/>
    </div>
    <Footer />
  </div>
</template>
<script>
import Header from '@/components/layout/header/Header.vue';
import Footer from '@/components/layout/footer/Footer.vue';
import MannerSelect from '@/components/user/myPage/MannerSelect.vue';
import Manner from '@/components/user/myPage/Manner.vue';
import Nickname from '@/components/user/myPage/Nickname.vue';

export default {
  components: {
    Header,
    Footer,
    Manner,
    MannerSelect,
    Nickname
  },
  data() {
    return {
      discordUrl: '',
      matchedUser: [],
    };
  },
  created() {
    this.discordUrl = this.$route.params.discordUrl;
    this.matchedUser = this.$route.params.matchedUser;
  },
};
</script>
<style></style>
