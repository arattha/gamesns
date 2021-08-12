<template>
  <div class="Search-container">
      <Header/>
      <div class="search-res">
      <div class="search-bar">

          <input v-model="search" class="search__input" type="text" placeholder="검색">
          <div v-for="(suggest,index) in searched" :key="index" id="suggestion_box" @click="userLink(suggest)">
              <!--<img />-->
              {{suggest.nickname}}
          </div>
      </div>

      <div class="Search-list">
        <!-- 여기는 따로 SearchList.vue 만들어서 import하자 -->
        <div class="title">최근 검색</div>
        <!-- 내가 검색했던 기록들 아래와 같이 나올 수 있게 -->
            
            <div class="slist" v-for="(user,index) in recentSearched" :key="index" @click="recentSearch(user)">
              <!--<img />-->
              {{user}}
          </div>

        <!-- <ul>
            <li>- 서든어택</li>
            <li>- 리그오브레전드</li>
            <li>- 로스트아크</li>
            <li>- 블레이드소울</li>
        </ul> -->
      </div>
      </div>
      <Footer/>
  </div>
</template>

<script>
import Footer from '@/components/layout/footer/Footer.vue';
import Header from '@/components/layout/header/Header.vue';
import http from '@/util/http-common.js'
import { mapActions , mapGetters } from "vuex";
import UserApi from '../../../api/UserApi';

export default {
    name:'Search',
    components: {
        Footer,
        Header
    },
    data() {
        return{
            nickname: '',
            search : "",
        };
    },
    created(){
        this.nickname = this.$store.state.nickname;
    },
    watch: {
        search: function (val) {
            if(val != "") this.searchUser(val);
        }
    },
    computed: {
        ...mapGetters(["searched", "recentSearched"]),
    },
    methods:{
        ...mapActions(["searchUser", "recentUser"]),
        userLink(suggest){
            this.recentUser(suggest.nickname);
            if(this.nickname == suggest.nickname) this.$router.push('/mypage');
            else this.$router.push({ name: 'UserPage', params: {suggest}});
        },
        recentSearch(user) {
            this.recentUser(user);

            UserApi.requestGetUser(user
            ,(res) => { 
                this.userLink(res.data.object);
            }
            ,() => {});
        }
    },
}

// 서치에 해시태그 검색을 하면
// #싸피
// #싸피 선택시
// http://localhost:8081/#싸피 이렇게해서
// main과 비슷하게 생겼으나 #싸피가 해시태그가 존재하는 feed들을 보여준다.
// 해당 feed들을 가져오는 방법은
// hashtag == #광주 #싸피 #찬인 #싸피언스
// #싸피 in hashtag == true? 

</script>

<style>
    @import '../../css/home/search/search.scss';
</style>