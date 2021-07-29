<template>
  <div class="Search-container">
      <div class="search-bar">

          <input v-model="search" class="search__input" type="text" placeholder="검색">
          <div v-for="(suggest,index) in searched" :key="index" id="suggestion_box" @click="userLink(suggest)">
              <!--<img />-->
              {{suggest.nickname}}
          </div>
      </div>

      <div class="Search-list" v-if="searched.length == 0">
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
      <Footer/>
  </div>
</template>

<script>
import Footer from '@/components/layout/footer/Footer.vue';
import http from '@/util/http-common.js'
import { mapActions , mapGetters } from "vuex";
import UserApi from '../../../api/UserApi';

export default {
    name:'Search',
    components: {
        Footer
    },
    data() {
        return{
            search : "",
        };
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
            this.$router.push({ name: 'UserPage', params: {suggest}})
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
</script>

<style>
    @import '../../css/home/search/search.scss';
</style>