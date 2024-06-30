<template>
  <div class="container">
    <header class="header">
      <h1>{{ recipeData.recipeName }}</h1>
    </header>
    <div class="content-box">
      <p>
        分类:{{ recipeData.recipeClassify }}<br/>
        日期:{{ recipeData.recipeDate }}<br/>
        时段：{{ recipeData.timeSpan }}
      </p>
      <p>使用原料：{{ recipeData.ingredient }}</p>
      <div v-if="recipeData.recipeCover">
        <div v-if="isImage(recipeData.recipeCover)">
          <img
            :src="recipeData.recipeCover"
            alt="附件"
            width="750"
            height="auto"
          />
        </div>
        <div v-else>
          <a :href="recipeData.recipeCover" target="_blank">查看文件</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ElMessage } from "element-plus";
import moment from "moment";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { recipeGetById } from "../../../../api/knowledge";

const route = useRoute();
const recipeId = route.params.id; // 获取路由参数id
const recipeData = ref({}); // 存储数据的响应式变量

// 格式化日期
function formatDate(dateTime) {
  return moment(dateTime).format("YYYY-MM-DD HH:mm");
}
// 在组件挂载后调用API
onMounted(async () => {
  if (recipeId !== "") {
    const recipeResponse = await recipeGetById(recipeId);
    recipeData.value = recipeResponse.data.data;
  } else {
    ElMessage.error("请重试");
  }
});

//图片检查
function isImage(url) {
  // 作为图片处理的文件扩展名
  const imageExtensions = ["png", "jpg", "jpeg", "gif"];
  // 获取URL的扩展名
  const extension = url.split(".").pop().toLowerCase();
  // 检查扩展名是否在以上列表中有对应值
  return imageExtensions.includes(extension);
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 40px auto;
  font-family: Arial, sans-serif;
  color: #333;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.header h1 {
  font-size: 24px;
}

.content-box {
  background-color: #f0f0f09e;
  padding: 20px;
  border-radius: 8px;
}

.content-box h2 {
  font-size: 18px;
  margin-bottom: 10px;
}

.content-box h3 {
  font-size: 16px;
  margin-bottom: 10px;
}

.content-box p,
.content-box ul {
  margin-bottom: 20px;
}

.content-box ul {
  padding-left: 20px;
}
</style>
