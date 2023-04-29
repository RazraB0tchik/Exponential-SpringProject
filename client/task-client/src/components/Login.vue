<template>

  <div class="container-fluid">
    <div class="row" style="background-color: #4A4A4A; justify-content: center">
      <div id="top-alert" :class="error_flag ? 'd-block alert alert-warning alert-dismissible fade show' : 'd-none'" style="position: absolute; font-family: Base, sans-serif; font-size: 1.5vh">
        Пользователь не найден!
      </div>
      <div class="col-md-10 col-xl-6 " style="height: 100vh">
        <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100%">
          <div :class="this.flag ? 'login_form_forgot d-flex flex-column align-items-center justify-content-around' : 'login_form_forgot d-none'">
            <div class="inputs_element_forgot d-flex flex-column align-items-center justify-content-around">
              <div class="form-header ">Password recovery</div>
              <div class="form-header-description mt-3 mb-3">На указанную почту будет выслан новый пароль!</div>
              <form class="w-100 d-flex flex-column justify-content-around" style="height: 40%">
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="email_form_recover">Введите почту</label>
                  <input class = "form-control" type="email" id="email_form_recover">
                </div>
                <div class="form-group align-items-center">
                  <button type="submit" class="button_forgot_login">Восстановить</button>
                </div>
                <div class="form-group align-items-center">
                  <button type="submit" class="button_forgot_login" @click="showForgotWindow(this.$event)">Отмена</button>
                </div>
              </form>
            </div>
          </div>


          <div :class="this.flag ? 'login_form d-none' : 'login_form d-flex flex-column align-items-center justify-content-around'">
            <LoginAnimateElem model="model/login2.gltf" fov=7 />
            <div class="inputs_element d-flex flex-column align-items-center justify-content-around">
              <div class="form-header">Login</div>
              <form class="w-100 d-flex flex-column justify-content-around" style="height: 80%">
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="email_form">Введите логин</label>
                  <input v-model="login" class = "form-control" type="email" id="email_form">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="password_form">Введите пароль</label>
                  <input v-model="passwd" class = "form-control" type="password" id="password_form">
                </div>
                <div class="form-group align-items-center">
                <button type="button" class="button_login" @click="loginUser(this.login, this.passwd)">Войти</button>
                </div>
                <div class="form-group align-items-center">
                  <div class="forgot_pass text-lg-center text-sm-center" @click="showForgotWindow(this.$event)">Забыли пароль</div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import LoginAnimateElem from "@/components/LoginAnimateElem";
import Main from "@/components/Main";
import controller from "../tools/controller.js"
import axios from "axios";
import router from "@/rout/router";
export default {
  name: "Login",
  beforeCreate() {
    import("../css/customLogin.scss");
    import ("@/components/LoginAnimateElem");
  },
  data(){
    return {
      flag: false,
      login: "",
      passwd: "",
      error_flag: false,
      mapResponse: [],
      mapUserEl: []
    }
  },
  components: {
    LoginAnimateElem
  },
  methods: {

    async loginUser(login, passwd){
      axios.post("http://localhost:8000/auth/api/login", {
        "username": login,
        "password": passwd
      }).then(response => {
        this.mapUserEl = response.data;
        localStorage.username = this.mapUserEl.username;
        localStorage.token = this.mapUserEl.tokenLogin;
        localStorage.role = this.mapUserEl.role;
        if(response.status === 200){
          console.log(localStorage.token);
          Main.data().startPage = true;
          router.push("/main");
        }
      }).catch(error => {
            console.log(error.response.status)
            if(error.response.status === 401){
              this.error_flag=true;
              setTimeout(()=>{this.error_flag = false}, 10000);
            }
          })
  },

    showForgotWindow(e){
      this.flag = !this.flag;
    },
    }
  }
</script>

<style lang="scss">
@import "../fonts/fonts.css";

.forgot_pass{
  color: #FF0DFF;
  font-family: "Base", sans-serif;
  cursor: pointer;

}

</style>