<template>
  <div class="for-empty-space-footer">
    <div id="menu-nav">
      <div id="navigation-bar">
        <ul>
          <li @click="goToMain"><i class="fa fa-home"></i></li>
          <li @click="goToSearch"><i class="fa fa-search"></i></li>
          <li @click="goToMatching"><i class="fas fa-gamepad"></i></li>
          <li @click="goToChatting"><i class="fas fa-comments"></i></li>
          <li @click="goToWriting"><i class="fas fa-edit"></i></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer',
  methods: {
    goToMain: function() {
      this.$router.push('/main');
    },
    goToSearch: function() {
      this.$router.push('/search');
    },
    goToMatching: function() {
      this.$router.push('/Matching');
    },
    goToChatting: function() {
      this.$router.push('/chatting');
    },
    goToWriting: function() {
      this.$router.push('/writing');
    },
  },
};
</script>

<style>
@import '../../css/layout/footer.css';
@import '../../css/layout/footer.scss';
</style>
