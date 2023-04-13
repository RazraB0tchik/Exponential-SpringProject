import { createApp } from 'vue'
import App from './App.vue'
import router from "@/rout/router";
import "bootstrap/dist/css/bootstrap.css"
import bootstrap from "bootstrap/dist/js/bootstrap.js"

const app = createApp(App)
app.use(router)
app.use(bootstrap)
app.mount('#app')
