import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'

import App from './App.vue'
import ListNote from './components/ListNote.vue'
import Note from './components/Note'

Vue.use(VueRouter);
Vue.use(VueResource);

const routes = [
    {path: '/', component: ListNote},
    {path: '/note', component: Note}
];

const router = new VueRouter({
    routes
});

Vue.config.productionTip = false;

new Vue({
    render: h => h(App),
    router
}).$mount('#app');
