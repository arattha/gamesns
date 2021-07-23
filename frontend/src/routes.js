

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinSC from './views/user/JoinSuccess.vue'
import ChPwd from './views/user/Chpwd.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import NotFound from './views/error/PageNotFound.vue'
import ErrorP from './views/error/Error.vue'
import Mainfeed from './views/feed/Mainfeed.vue'
import MyEdit from './views/user/MyEdit.vue'
import MyPage from './views/user/MyPage.vue'
import Following from './views/user/Following.vue'
import Follower from './views/user/Follower.vue'


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
        path : '/user/joinSC',
        name : 'JoinSC',
        component : JoinSC
    },
    {
        path : '/user/chpwd',
        name : 'ChPwd',
        component : ChPwd
    },
    {
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
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
        path : '/main',
        name : 'Mainfeed',
        component : Mainfeed
    },
    {
        path : '/MyPage',
        name : 'MyPage',
        component : MyPage
    },
    {
        path : '/:pathMatch(.*)*',
        redirect : '/404'
    },
]
