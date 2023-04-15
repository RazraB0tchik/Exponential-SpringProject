import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8000/",
});

api.interceptors.request.use(config => {
    config.headers = {'Authorization': 'Bearer_' + localStorage.tokenUser,
        'Content-Type': 'application/json'
    };
    config.mode = "cors";
    return config;
}, error => {
    console.log(error);
})

export default {
    data(){
        return {
            username: "",
            token: "",
            role: "",
            mapAdmin: [],
            mapToken: []
        }
    },
    methods: {
        async registrateUser(login, passwd, email) {
            axios.post("http://localhost:8000/reg/registrationUser", {
                "username": login,
                "password": passwd,
                "email": email,
            }).catch(error => {
                console.log(error)
            })
        }
    }
}