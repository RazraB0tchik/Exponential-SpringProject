import {createRouter, createWebHistory} from "vue-router"
import Login from "@/components/Login";
import Registration from "@/components/Registration";
const routes = [
    {path: "/login", component: Login},
    {path: "/registration", component: Registration}
]

export default new createRouter({
  history: createWebHistory(),
  routes
})