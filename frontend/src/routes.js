

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinSC from './views/user/JoinSuccess.vue'
import ChPwd from './views/user/Chpwd.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'
import NotFound from './views/error/PageNotFound.vue'

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
        path : '/components',
        name : 'Components',
        component : Components
    },
    {
        path : '/404',
        name : "notFound",
        component : NotFound
    },
    {
        path : '/:pathMatch(.*)*',
        redirect : '/404'
    }
]
