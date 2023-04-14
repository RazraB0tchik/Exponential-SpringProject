<template>

  <div class="container-fluid">
    <div class="row" style="background-color: #4A4A4A; justify-content: center">
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
                  <label class="label_text text-lg-center text-sm-center" for="email_form">Введите почту</label>
                  <input class = "form-control" type="email" id="email_form">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text text-lg-center text-sm-center" for="password_form">Введите пароль</label>
                  <input class = "form-control" type="password" id="password_form">
                </div>
                <div class="form-group align-items-center">
                <button type="submit" class="button_login">Войти</button>
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

export default {
  name: "Login",
  beforeCreate() {
    import("../css/customLogin.scss");
    import ("@/components/LoginAnimateElem");
  },
  data(){
    return {
      flag: false
    }
  },
  components: {
    LoginAnimateElem
  },
  methods: {
    showForgotWindow(e){
      this.flag = !this.flag;
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