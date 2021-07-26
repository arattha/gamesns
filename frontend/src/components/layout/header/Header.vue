<template>
    <b-container fluid class="main-container">
        <nav>
            <span class="header" style="padding: 25px 0px 25px 0px">
                <div class="logo">
                    <img src="@/assets/images/logo.png" alt="" style="width:156px">
                </div>
                <div class="mypage-btn">
                    <img src="@/assets/images/mypage.png" alt="" style="width: 24px;">
                </div>
            </span>
        </nav>
        <div class="for-empty-space">
        </div>
    </b-container>
</template>

<script>
export default {
    name:'Header'
}
</script>

<style>
    @import "../../css/layout/header.css";  
</style>