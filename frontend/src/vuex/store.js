import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import http from '@/util/http-common'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)

// const state = {
//     isUser: false,
// }

export default new Vuex.Store({
    state: {
        boardItems:[],
    },
    mutations: {
        GET_BOARD_ITEMS(state, payload) {
            state.sidos = payload;
        },
    },
    getters: {
        boardItems(state) {
            return state.boardItems;
        },
    },
    actions: {
        getBoardItems( { commit }, data) {
            console.log("test")
            data = {
                uid: 0
            };

            http
                .get(`/board`, { params:  data  })
                .then(({ data }) => {
                    commit('GET_BOARD_ITEMS', data);
                    console.log(data);
                })
                .catch(() => {
                    alert('에러가 발생했습니다.');
                });
            },
    }
})