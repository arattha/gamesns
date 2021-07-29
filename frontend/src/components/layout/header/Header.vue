<template>
    <div style="margin-bottom: 50px">
    <div class="main-container" style="padding: 0px;">
        <nav>
            <ul class="header" style="padding: 25px 0px 25px 0px">
                <div class="logo">
                    <img @click="goToMain" src="@/assets/images/logo2.png" alt="">
                </div>
                <div class="alarm-btn">
                    <div @click="goAlarm"><i class="fas fa-bell"></i></div>
                </div>
                <div class="mypage-btn">
                    <div @click="goToMypage"><i class="fas fa-user-circle" ></i></div>
                </div>
                <div class="logout-btn">
                    <div><i class="fas fa-sign-out-alt"></i></div>
                </div>
            </ul>
        </nav>
    </div>
    </div>
</template>

<script>
export default {
    name:'Header',
    methods: {
        goToMypage: function() {
            this.$router.push('mypage')
        },
        goToMain: function() {
            this.$router.push('main')
        },
        goAlarm: function() {
            this.$router.push('alarm')
        }
    },
}
</script>

<style>
    @import "../../css/layout/header.css";  
</style>