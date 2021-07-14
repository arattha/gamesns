

import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
import JoinSC from './views/user/JoinSuccess.vue'
import FeedMain from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'

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
        path : '/feed/main',
        name : 'FeedMain',
        component : FeedMain
    },
    {
        path : '/components',
        name : 'Components',
        component : Components
    }
]
