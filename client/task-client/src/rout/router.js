import {createRouter, createWebHistory} from "vue-router"
import Login from "@/components/Login";
import Registration from "@/components/Registration";
import Main from "@/components/Main";
const routes = [
    {path: "/login", component: Login},
    {path: "/registration", component: Registration},
    {path: "/main", component: Main}
]

export default new createRouter({
  history: createWebHistory(),
  routes
})