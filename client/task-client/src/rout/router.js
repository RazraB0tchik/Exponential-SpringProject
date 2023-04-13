import {createRouter, createWebHistory} from "vue-router"
import Login from "@/components/Login";
const routes = [
    {path: "/", component: Login}
]

export default new createRouter({
  history: createWebHistory(),
  routes
})