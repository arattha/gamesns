<template>
  <div class="for-empty-space-header">
    <div class="main-container">
      <nav class="nav">
        <ul class="header" style="right: 0px">
          <div class="logo">
            <img @click="goToMain" src="@/assets/images/logolight.png" alt="" style="width:120px;"/>
          </div>
          <div class="alarm-btn">
            <div @click="goAlarm"><i class="fas fa-bell"></i></div>
          </div>
          <div class="mypage-btn">
            <div @click="goToMypage"><i class="fas fa-user-circle"></i></div>
          </div>
          <!-- <div class="logout-btn">
            <div><i class="fas fa-sign-out-alt"></i></div>
          </div> -->
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  methods: {
    goToMypage: function() {
      this.$router.push('/mypage');
    },
    goToMain: function() {
      this.$router.push('/main');
      document.location.reload();
    },
    goAlarm: function() {
      this.$router.push('/alarm');
    },
  },
};
</script>

<style>
@import '../../css/layout/header.css';
@import '../../css/layout/header.scss';
</style>
