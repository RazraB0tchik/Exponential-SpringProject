<template>
  <div class="login_icon">
  </div>
</template>

<script>
import "../css/customLogin.scss"
import {
  AmbientLight,
  Color,
  DirectionalLight,
  HemisphereLight,
  PerspectiveCamera,
  Scene,
  WebGLRenderer
} from "three";
import {GLTFLoader} from "three/addons/loaders/GLTFLoader";

export default {
  name: "LoginAnimateElem",
  props: {
    model: String,
    fov: Number
  },
  async mounted() {
  const container = document.querySelector(".login_icon");
  const scene = new Scene();
  scene.background = new Color("#3D3E42");
  const fov = this.fov;
  const aspect = container.clientWidth/container.clientHeight;
  const near = 2.1;
  const far = 100;
  const light = new DirectionalLight('white', 3);
  const ambient = new AmbientLight('black', 6);
  const hemisphere = new HemisphereLight('pink', 'darkslategrey', 0)

    light.position.set(10, 10, 10);
    scene.add(light, hemisphere, ambient);

  const camera = new PerspectiveCamera(fov, aspect, near, far);
  camera.position.set(0, 5, 100);
  const renderer = new WebGLRenderer();
    let element = null;
    renderer.setSize(container.clientWidth, container.clientHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    container.append(renderer
        .domElement); //добавляем на страницу созданный элемент
    const loader = new GLTFLoader();
    loader.load(this.model, (gltf) => {
      element = gltf.scene;
      scene.add(element);
      element.position.set(0, 0, 10);
      renderer.render(scene, camera);
      animate();
    });

    function setSize() { //функция перерисовки canvas
      camera.aspect = container.clientWidth / container.clientHeight;
      camera.updateProjectionMatrix(); //обновляем матрицу мира, тем самым меняем камеру

      renderer.setSize(container.clientWidth, container.clientHeight); //обновим рендер (а именно канвас)
    }

    window.addEventListener("resize", () => {
      setSize();
      console.log(window.innerWidth);
    })

    function animate() {
      requestAnimationFrame(animate);
      renderer.render(scene, camera); //говорим отобрази все что на сцене и добавь свет
      element.rotation.y += 0.01;
    }
}
}
</script>

<style scoped>

</style>