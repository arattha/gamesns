import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import http from '@/util/http-common'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)


export default new Vuex.Store({
    state: {
        boardItems:[],
        following:[],
        follower:[],
    },
    mutations: {
        GET_BOARD_ITEMS(state, payload) {
            state.boardItems = state.boardItems.concat(payload);
            //state.boardItems.push(payload);
        },
        GET_FOLLOWING(state, payload){
            state.following = payload;
        },
        GET_FOLLOWER(state, payload){
            state.follower = payload;
        },

    },
    getters: {
        boardItems(state) {
            return state.boardItems;
        },
        following(state){
            return state.following;
        },
        follower(state){
            return state.follower;
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
            
            if ( context.state.boardItems.length == 0) {
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
                .get(`/board`, { params:  data  })
                .then(({ data }) => {
                    console.log(data);
                    context.commit('GET_BOARD_ITEMS', data.object);
                    console.log(context.state.boardItems);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
            
            
        },


    }
})