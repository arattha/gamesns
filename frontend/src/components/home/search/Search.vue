<template>
  <div class="Search-container">
      <div class="search-bar">

          <input v-model="search" class="search__input" type="text" placeholder="유저 검색">
          <button v-for="(suggest,index) in searched" :key="index" id="suggestion_box" @click="userLink(suggest)">
              <!--<img />-->
              {{suggest.nickname}}
          </button>
      </div>

  <!--
      <div class="credits__container">
          <p class="credits__text">추천태그:
               해당 태그 누르면 올라가게 
              <a href="https://www.pantone.com/color-of-the-year-2016"
              class="credits__link">리그오브레전드</a>
              <a href="https://www.pantone.com/color-of-the-year-2016"
              class="credits__link">Steam</a>
              <a href="https://www.pantone.com/color-of-the-year-2016"
              class="credits__link">스타크래프트</a>
          </p>
      </div>
      -->
      <hr>
      <div class="Search-list">
          <!-- 여기는 따로 SearchList.vue 만들어서 import하자 -->
          <h4 style="margin-left:8px">나의 검색기록</h4>
          <!-- 내가 검색했던 기록들 아래와 같이 나올 수 있게
          <ul>
              <li>- 서든어택</li>
              <li>- 리그오브레전드</li>
              <li>- 로스트아크</li>
              <li>- 블레이드소울</li>
          </ul>
        -->
      </div>
      <Footer/>
  </div>
</template>

<script>
import Footer from '@/components/layout/footer/Footer.vue';
import { mapActions , mapGetters } from "vuex";
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
        ...mapGetters(["searched"]),
    },
    methods:{
        ...mapActions(["searchUser"]),
        userLink(suggest){
            this.$router.push({ name: 'UserPage', params: {suggest}})
        }
    },
}
</script>

<style>

/* Base styling */

body {
    width: 100%;
    margin: 0 auto;
    background-color: #F7CAC9;
}

.Search-container{
    width:100%;
    }

.search-bar {
    width:95%;
    padding-top:16px;
    margin: 0 auto;
    text-align: center;
}

.search__input {
        width: 100%;
        padding: 12px 24px;

        background-color: transparent;
        transition: transform 250ms ease-in-out;
        font-size: 16px;
        line-height: 18px;
        
        color: #575756;
        background-color: transparent;
/*         background-image: url(http://mihaeltomic.com/codepen/input-search/ic_search_black_24px.svg); */

        background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24'%3E%3Cpath d='M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z'/%3E%3Cpath d='M0 0h24v24H0z' fill='none'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-size: 18px 18px;
        background-position: 95% center;
        border-radius: 50px;
        border: 1px solid #575756;
        transition: all 250ms ease-in-out;
        backface-visibility: hidden;
        transform-style: preserve-3d;
    }

.search__input::placeholder {
            color: rgba(87, 87, 86, 0.8);
            text-transform: uppercase;
            letter-spacing: 1.5px;
        }

.search__input:hover,
        .search__input:focus {
            padding: 12px 0;
            outline: 0;
            border: 1px solid transparent;
            border-bottom: 1px solid #575756;
            border-radius: 0;
            background-position: 100% center;
        }

.credits__container {
        margin-top: 24px;
    }

.credits__text {
        text-align: center;
        font-size: 13px;
        line-height: 18px;
    }

.credits__text a{
    padding: 0 5px;
    }

.credits__link {
        color: #ff8f00;
        text-decoration: none;
        transition: color 250ms ease-in;
    }

.credits__link:hover,
        .credits__link:focus {
            color: rgb(191, 137, 135);
        }


.Search-list ul li {
    padding: 10px 0;
}
</style>