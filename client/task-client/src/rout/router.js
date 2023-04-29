import {createRouter, createWebHistory} from "vue-router"
import Login from "@/components/Login";
import Registration from "@/components/Registration";
import Main from "@/components/Main";
const routes = [
    {path: "/login", component: Login, name: "Login"},
    {path: "/registration", component: Registration, name: "Registration"},
    {path: "/main", component: Main, name: "Main"}
]

const router = createRouter({history: createWebHistory(),
    routes})

router.beforeEach((to) => {
    if(to.name === "Main"){
        Main.methods.startInterval();
    }
    if(to.name === "Login"){
        Main.methods.stopInterval();
    }

})

export default router