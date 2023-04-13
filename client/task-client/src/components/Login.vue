<template>

  <div class="container-fluid" style="background-color: blue">
    <div class="row" style="background-color: orange; justify-content: center">
      <div class="col-md-10 col-xl-6 " style="height: 100vh; background-color: palegreen">
        <div class="d-flex justify-content-center align-items-center" style="background-color: blueviolet; width: 100%; height: 100%">
          <div class="login_form d-flex flex-column align-items-center justify-content-around">
            <div class="login_icon">
            </div>
            <div class="inputs_element d-flex flex-column justify-content-around">
              <form class="w-100 h-50 d-flex flex-column align-content-around justify-content-around" style="background-color: orange">
                <div class="form-group align-items-center">
                  <label class="label_text" for="email_form">Введите почту</label>
                  <input class = "form-control" type="email" id="email_form">
                </div>
                <div class="form-group align-items-center">
                  <label class="label_text" for="password_form">Введите пароль</label>
                  <input class = "form-control" type="password" id="password_form">
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

export default {
  name: "Login",
  beforeCreate() {
    import("../css/customEl.scss");
  }
}
</script>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Dancing+Script&family=IM+Fell+Double+Pica:ital@1&family=Open+Sans&family=Oswald&family=PT+Serif:ital@1&family=Raleway:wght@500&display=swap');
.login_form{
   background-color: blue;
   width: 80%;
   height: 70%;
 }
 .login_icon{
   background-color: brown;
   width: 15vh;
   height: 20%;
   margin-top: 5%;
   margin-bottom: 5%;
 }
 .inputs_element{
   width: 80%;
   height: 60%;
   background-color: aqua;
   margin-bottom: 10%;
 }
 .label_text{
   font-size: 2vh;
   font-family: "Da";
 }

</style>