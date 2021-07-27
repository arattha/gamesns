import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import http from '@/util/http-common'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)


export default new Vuex.Store({
    state: {
        boardItems: [],
        searched: [],
        following:[],
        follower: [],
        replyList:[],
    },
    mutations: {
        GET_BOARD_ITEMS(state, payload) {
            state.boardItems = state.boardItems.concat(payload);
            //state.boardItems.push(payload);
        },
        GET_SEARCHED(state, payload) {
            state.searched = payload;
        },
        GET_FOLLOWING(state, payload){
            state.following = payload;
        },
        GET_FOLLOWER(state, payload){
            state.follower = payload;
        },
        GET_REPLY_LIST(state, payload){
            state.replyList = payload;
        },
    },
    getters: {
        boardItems(state) {
            return state.boardItems;
        },
        searched(state) {
            return state.searched;
        },
        following(state){
            return state.following;
        },
        follower(state){
            return state.follower;
        },
        replyList(state) {
            return state.replyList;
        }
    },
    actions: {
        getFollowing({commit}, data){

            data = {
                from: 1
            }
            http
                .get('/follow/following', {params: data})
                .then((res) => {
                    console.log(res);
                    commit("GET_FOLLOWING", res.data);
                })
                .catch(() =>{
                    console.log("get following error");
                })
        },
        getFollower({commit}, data){

            data = {
                to: 1
            }
            http
                .get('/follow/follower', {params: data})
                .then((res) => {
                    console.log(res);
                    commit("GET_FOLLOWER", res.data);
                })
                .catch(() =>{
                    console.log("get following error");
                })
        },
        getBoardItems(context, data) {
            if (context.state.boardItems.length == 0) {
                data = {
                    uid: 0
                };
            } else {
                data = {
                    uid: 0,
                    bid: String(context.state.boardItems[context.state.boardItems.length - 1].bid)
                };
            }
            console.log(data);
            http
                .get(`/board`, { params: data })
                .then(({ data }) => {
                    console.log(data);
                    context.commit('GET_BOARD_ITEMS', data.object);
                    console.log(context.state.boardItems);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
        },
        getUserBoardItems(context) {
            let data = {
                    uid: 1  
                };
            //uid 또는 닉네임으로 구현되면 주석으로 변경
            /*
            let data = {
                nickname : nick
            }
            */
            http
                .get(`/board/user`, { params: data })
                .then(({ data }) => {
                    context.commit('GET_BOARD_ITEMS', data.object);
                    console.log(context.state.boardItems);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
        },
        getReplyList({ commit }, data) {
            http
                .get(`/reply`, { params: data })
                .then(({ data }) => {
                    commit('GET_REPLY_LIST', data.object.content);
                    console.log("reply");
                    console.log(data);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
        },
        addBoard({ commit }, formData) {
            commit
            http
                .post(`/board`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                )
                .then(({ data }) => {
                    console.log(data);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
        },
        searchUser({ commit }, val) {
            console.log(val);
            let data = {
                nickname : val
            }

            http
                .get(`/search`, {params : data})
                .then(({ data }) => {
                    commit('GET_SEARCHED', data);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
        }
    }
})