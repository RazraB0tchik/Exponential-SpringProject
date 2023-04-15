<template>
  <div class="container-fluid">
    <div class="row" style="background-color: #4A4A4A; justify-content: center">
      <div class="col-md-10 col-xl-6 " style="height: 100vh">
        <ul v-for="elem in newsMap" :key="elem">
          <li>{{elem}}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "@/rout/router";

export default {
  name: "Main",
  data(){
    return{
      newsMap: []
    }
  },
  mounted() {
    this.getNews()
  },
  methods:{
    async getNews(){
      await axios.get("http://localhost:8000/controller1/getNews")
      .then(response => {
        this.newsMap = response.data;
        console.log(this.newsMap)
      })
      .catch(error => {
        if(error.response.status === 401){
          router.push("/login");
        }
        else{
          this.updateAccess();
        }
      })
    },
    async updateAccess() {
      await axios.post("http://localhost:8000/update/getAccess", {
            "username": localStorage.user,
            "role": localStorage.roleUser,
          },
          {
            mode: "cors",
            credentials: "same-origin",
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            this.mapUser = response.data;
            localStorage.user = this.mapUser.username;
            localStorage.tokenUser = this.mapUser.tokenUpdate;
            localStorage.roleUser = this.mapUser.role;
          })

    },
  }


}
</script>

<style scoped>

</style>