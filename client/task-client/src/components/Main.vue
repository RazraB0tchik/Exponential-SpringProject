<template>
  <div class="container-fluid">
    <div class="row" style="background-color: #4A4A4A; justify-content: center">
      <div class="col-md-10 col-xl-6 " style="height: 100vh">
        <ul v-for="elem in newsMap" :key="elem">
          <li style="color: white">{{elem}}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import controller from "@/tools/controller";

export default {
  name: "Main",
  data(){
    return{
      newsMap: [],
      interval: null
    }
  },
  mounted() {
    controller.method.getNews().then(response => {
      this.newsMap = response.data
    });
  },

  methods:{
    startInterval(){
        this.interval = setInterval(() => {
          controller.method.getNews().then(response => {
            this.newsMap = response.data
          })
        }, 3000)
    },

    stopInterval(){
          clearInterval(this.interval);
    }
  }
}
</script>

<style scoped>

</style>