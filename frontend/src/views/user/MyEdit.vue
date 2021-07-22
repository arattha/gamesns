<template>
<div>
    <!-- 편집 폼 -->
    <form action="">
        <!-- 타이틀 -->
        <div class="title"><h1>프로필 편집</h1></div>
        <!-- 프로필 사진 block-->
        <div class="profile">
            <!-- 현재 프로필 사진/모양자르기 -->
            <img class="profile-img" src="" alt="">
            <!-- 사진 변경 버튼 -->
            <button>프로필 사진 변경</button>
        </div>
        <!-- 정보들 block -->
        <div class="infos">
            <!-- 닉네임 -->
            <div class="info">
                <h3>닉네임</h3>
                <!-- 현재 닉네임 보여주고/필수-->
                <input type="text">
            </div>
            <!-- 상태메시지 -->
            <div class="info">
                <h3>소개</h3>
                <input type="text">
            </div>
            <!-- 디스코드 계정  -->
            <div>
                <h3>디스코드 계정</h3>
                <input type="text">
            </div>
        </div>
        <!-- 버튼 블록 -->
        <div class="submit">
            <button>제출</button>
        </div>
    </form>
    
</div>
</template>

<script>

export default {

}
</script>
