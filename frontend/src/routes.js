

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import Main from './views/feed/Main.vue'
import Components from './views/Components.vue'
import NotFound from './views/error/PageNotFound.vue'
import ErrorP from './views/error/Error.vue'
import MyEdit from './views/user/MyEdit.vue'
import MyPage from './views/user/MyPage.vue'
import Following from './views/user/Following.vue'
import Follower from './views/user/Follower.vue'
import Search from './components/home/search/Search.vue'
import Writing from './components/home/writing/Writing.vue'
import Alarm from './views/alarm/Alarm.vue'
import UserPage from './views/user/UserPage.vue'
import Re from './views/user/Re.vue'


export default [


    {
        path : '/',
        name : 'Login',
        component : Login
    },
    {
        path : '/user/join',
        name : 'Join',
        component : Join
    },
    {
        path : '/main',
        name : 'Main',
        component : Main
    },
    {
        path : '/re',
        name : 'Re',
        component : Re
    },
    {
        path : '/userpage',
        name : 'UserPage',
        component : UserPage
    },
    {
        path : '/mypage/edit',
        name : 'MyEdit',
        component: MyEdit
    },
    {
        path : '/mypage/following',
        name : 'Following',
        component: Following
    },
    {
        path : '/mypage/follower',
        name : 'Follower',
        component: Follower
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/error',
        name : "error",
        component : ErrorP
    },
    {
        path : '/404',
        name : "notFound",
        component : NotFound
    },
    {
        path : '/MyPage',
        name : 'MyPage',
        component : MyPage
    },
    {
        path : '/search',
        name : 'Search',
        component : Search
    },
    {
        path : '/writing',
        name : 'Writing',
        component : Writing
    },
    {
        path : '/alarm',
        name : 'Alarm',
        component : Alarm
    },
    {
        path : '/:pathMatch(.*)*',
        redirect : '/404'
    },
]
