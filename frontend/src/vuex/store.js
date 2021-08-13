import Vue from 'vue';
import Vuex from 'vuex';
import getters from './getters';
import http from '@/util/http-common';
import actions from './actions';
import mutations from './mutations';
import createPersistedState from 'vuex-persistedstate';
import { router } from '../main';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    recentSearched: [],
    // accessToken: '',
    uid: '',
    nickname: '',
    boardContent: '',
  },
  mutations: {
    SET_RECENTSEARCHED(state, payload) {
      console.log('first', state.recentSearched);
      state.recentSearched.splice(0, 0, payload);
      console.log('second', state.recentSearched);

      var uniqueArr = [];
      state.recentSearched.forEach((element) => {
        if (!uniqueArr.includes(element)) {
          uniqueArr.push(element);
        }
      });

      state.recentSearched = uniqueArr;
    },
    // SET_ACCESS_TOKEN(state, payload) {
    //   state.accessToken = payload;
    // },
    SET_UID(state, payload) {
      state.uid = payload;
    },
    SET_NICKNAME(state, payload) {
      state.nickname = payload;
    },
    SET_BOARD_CONTENT(state, payload) {
      state.boardContent = payload;
    },
  },
  getters: {
    searched(state) {
      return state.searched;
    },
    searched_hashtag_boards(state) {
      return state.searched_hashtag_boards;
    },
    recentSearched(state) {
      return state.recentSearched;
    },
    // accessToken(state) {
    //   return state.accessToken;
    // },
    uid(state) {
      return state.uid;
    },
    nickname(state) {
      return state.nickname;
    },
    boardContent(state) {
      return state.boardContent;
    },
  },
  actions: {
    // setAccessToken(context, data) {
    //   context.commit('SET_ACCESS_TOKEN', data);
    // },
    setBoardContent(context, data) {
      context.commit('SET_BOARD_CONTENT', data);
    },
    setUid(context, data) {
      context.commit('SET_UID', data);
    },
    setNickname(context, data) {
      context.commit('SET_NICKNAME', data);
    },
    addBoard({ commit }, formData) {
      http
        .post(`/board`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(({ data }) => {
          console.log(data);
          alert('글이 작성되었습니다.');
        })
        .then(() => router.push('/main'))
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    recentUser({ commit }, val) {
      commit('SET_RECENTSEARCHED', val);
    },
  },
  plugins: [createPersistedState()],
});
