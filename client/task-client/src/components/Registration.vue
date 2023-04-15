<template>
  <div class="container-fluid">
    <div class="row" style="background-color: #4A4A4A; justify-content: center">
      <div id="top-alert" :class="error_flag ? 'd-block alert alert-warning alert-dismissible fade show' : 'd-none'" style="position: absolute; font-family: Base, sans-serif; font-size: 1.5vh">
        Введенные пароли неверны!
      </div>
      <div class="col-md-10 col-xl-6 align-items-center" style="height: 100vh">
        <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 100%">
          <div class="login_form d-flex flex-column align-items-center">
            <LoginAnimateElem model="model/blocknot.gltf" fov=12 />
            <div class="inputs_element mt-2 d-flex flex-column align-items-center">
              <div class="form-header-registrate mb-3">Registration</div>
              <form class="w-100 d-flex flex-column justify-content-around" style="height: 100%;">
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="login_form">Введите логин</label>
                  <input class = "form-control" v-model="login" type="text" id="login_form" placeholder="Apelsin">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="email_form">Введите почту</label>
                  <input v-model="email" class = "form-control" type="email" id="email_form" placeholder="mail@bk.ru">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="password_form">Введите пароль</label>
                  <input v-model="password1" class = "form-control" type="password" id="password_form">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="confim_password_form">Подтвердите пароль</label>
                  <input v-model="password2" class = "form-control" type="password" id="confim_password_form">
                </div>
                <div class="form-group align-items-center">
                  <button type="button" class="button-registration" @click="checkValidPasswrd">Зарегестрироваться</button>
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
import controller from "../tools/requests.js"
import router from "@/rout/router";
export default {
  name: "Regirtsation",
  beforeCreate() {
    import("../css/customRegistr.scss");
    import ("@/components/LoginAnimateElem");
  },
  components: {
    LoginAnimateElem
  },
  data(){
  return {
      password1: "",
      password2: "",
      login: "",
      email: "",
      error_flag: false
  }},
  methods: {
    checkValidPasswrd(){
      if(this.password1 !== this.password2){
        this.error_flag = true;
        setTimeout(()=>{this.error_flag = false;}, 10000)
      }
      else{
        controller.methods.registrateUser(this.login, this.email, this.password1);
        router.push("/login");
      }
    }

  }
}
</script>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Dancing+Script&family=IM+Fell+Double+Pica:ital@1&family=Open+Sans&family=Oswald&family=PT+Serif:ital@1&family=Raleway:wght@500&display=swap');
@import "../fonts/fonts.css";

.forgot_pass{
  color: #FF0DFF;
  font-family: "Base", sans-serif;
  cursor: pointer;

}
</style>