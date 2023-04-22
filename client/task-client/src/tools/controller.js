import axios from "axios";
import router from "@/rout/router";
import {onBeforeRouteLeave} from "vue-router/dist/vue-router";
import Login from "@/components/Login";
import Main from "@/components/Main";

const api = axios.create({
    baseURL: "http://localhost:8000",
});

let newsMapElements = [];

async function updateAccess() {
    await api.post("/update/getAccess", {
            "username": localStorage.username,
            "role": localStorage.role,
        },
        {
            mode: "cors",
            credentials: "same-origin",
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            newsMapElements = response.data;
            localStorage.username = newsMapElements.username;
            localStorage.token = newsMapElements.tokenUpdate;
            localStorage.role = newsMapElements.role;
        })

}

api.interceptors.request.use(config => {
    console.log("im here")
    config.headers = {'Authorization': 'Bearer_' + localStorage.token,
        'Content-Type': 'application/json'
    };
    config.mode = "cors";
    return config;
}, error => {
    console.log(error);
}),
api.interceptors.response.use(undefined, async error => {
    if (error.response.status === 401) {
        await updateAccess();
        error.response.statusUpdate = true;
        if (error.response.statusUpdate) {
            return await api.get("/controller1/getNews")
                .then(response => {
                    return response
                })
        }

    }
    if (error.response.status === 405){
        Main.methods.stopInterval();
        router.push("/login");
    }
})

export default {
    method: {
        async getNews() {
            return await api.get("/controller1/getNews")
                .then(response => {
                    return response
                })
        },
    },
}