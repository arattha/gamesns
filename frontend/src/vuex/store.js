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
    boardItems: [],
    searched: [],
    recentSearched: [],
    replyList: [],
    // accessToken: '',
    uid: '',
    nickname: '',
  },
  mutations: {
    SET_BOARD_ITEMS(state, payload) {
      state.boardItems = state.boardItems.concat(payload);
      //state.boardItems.push(payload);
    },
    SET_SEARCHED(state, payload) {
      state.searched = payload;
    },
    GET_REPLY_LIST(state, payload) {
      state.replyList = state.replyList.concat(payload);
    },
    SET_RECENTSEARCHED(state, payload) {
      
      console.log("first", state.recentSearched);
      state.recentSearched.splice(0,0,payload);
      console.log("second",state.recentSearched);
      
      var uniqueArr = [];
      state.recentSearched.forEach((element) => {
        if (!uniqueArr.includes(element)) {
          uniqueArr.push(element);
        }
      });

      state.recentSearched = uniqueArr;
      
    },
    SET_REPLY_LIST(state, payload) {
      state.replyList = payload;
    },
    // SET_ACCESS_TOKEN(state, payload) {
    //   state.accessToken = payload;
    // },
    SET_UID(state, payload) {
      state.uid = payload;
    },
    SET_NICKNAME(state, payload) {
      state.nickname = payload;
    }
  },
  getters: {
    boardItems(state) {
      return state.boardItems;
    },
    searched(state) {
      return state.searched;
    },
    recentSearched(state) {
      return state.recentSearched;
    },
    replyList(state) {
      return state.replyList;
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
  },
  actions: {
    // setAccessToken(context, data) {
    //   context.commit('SET_ACCESS_TOKEN', data);
    // },
    setUid(context, data) {
      context.commit('SET_UID', data);
    },
    setNickname(context, data) {
      context.commit('SET_NICKNAME', data);
    },
    getBoardItems(context, data) {
      if (context.state.boardItems.length == 0) {
        data = {
          uid: context.state.uid,
        };
      } else {
        data = {
          uid: context.state.uid,
          bid: String(context.state.boardItems[context.state.boardItems.length - 1].bid),
        };
      }
      http
        .get(`/board`, { params: data })
        .then(({ data }) => {
          context.commit('SET_BOARD_ITEMS', data.object);
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    getUserBoardItems(context, targerUid) {
      let data;
      if (context.state.boardItems.length == 0) {
        data = {
          uid: targerUid,
        };
      } else {
        data = {
          uid: targerUid,
          bid: String(context.state.boardItems[context.state.boardItems.length - 1].bid),
        };
      }
      http
        .get(`/board/user`, { params: data })
        .then(({ data }) => {
          context.commit('SET_BOARD_ITEMS', data.object);
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    getReplyList({ commit }, data) {
      http
        .get(`/reply`, { params: data })
        .then(({ data }) => {
          commit('SET_REPLY_LIST', data.object.content);
          console.log('reply');
          console.log(data);
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    addReply({ commit }, data) {
      http
        .post(`/reply`, data )
        .then(({ data }) => {
          console.log('reply');
          console.log(data);
          alert('댓글이 등록되었습니다.');
        })
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
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
        .then(() => router.push("/main"))
        .catch(() => {
          alert('에러가 발생했습니다.');
        });
    },
    searchUser({ commit }, val) {
      let data = {
        nickname: val,
      };

      http
        .get(`/search`, { params: data })
        .then(({ data }) => {
          commit('SET_SEARCHED', data);
        })
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
