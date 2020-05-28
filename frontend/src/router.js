import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '@/page/MainPage'


import store from './store'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        { path: '/', component: MainPage },
        { path: '/callservice', component: Service },
        { path: '/bootstrap', component: Bootstrap },
        { path: '/user', component: User },
        { path: '/login', component: Login },
        {
            path: '/protected',
            component: Protected,
            meta: {
                requiresAuth: true
            }
        },

        { path: '*', redirect: '/' }
    ]
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!store.getters.isLoggedIn) {
            next({
                path: '/login'
            })
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;