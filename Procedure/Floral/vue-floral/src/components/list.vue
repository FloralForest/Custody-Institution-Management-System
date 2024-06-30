<template>
  <div class="common-layout">
    <el-container>
      <el-header v-if="!route.meta.hideNavbar"><navigation/></el-header>
      <el-main><router-view/></el-main>
    </el-container>
  </div>
</template>


<script setup>
import  navigation  from '../components/navigation/list.vue'
import { useRoute } from 'vue-router';
const route = useRoute(); // 获取当前路由对象
</script>
<style>
body {
  background-image: url('../assets/picture/Duviri-texture.jpg');
  background-size: auto; /* 使用图片的原始尺寸 */
  background-position: top left; /* 从左上角开始显示 */
  background-repeat: repeat; /* 让背景图片在水平和垂直方向重复 */
}
</style>
