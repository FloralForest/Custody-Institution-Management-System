<template>
  <div class="news-list">
    <div class="news-item" v-for="recipe in recipe" :key="recipe.id">
      <div class="news-content">
        <h3 class="news-title">
          <a
            :href="`/recipe/recipeDetails/${recipe.id}`"
            target="_blank"
            class="knowledge-link"
            >{{ recipe.recipeName }}</a
          >
        </h3>
        <p class="news-date">
          分类：{{ recipe.recipeClassify }} <br/>
          日期：{{ recipe.recipeDate }} <br/>
          时段：{{ recipe.timeSpan }} 
        </p>
        <p class="news-date">
          发布时间：{{ formatDate(recipe.createTime) }} &gt;更新时间：{{
            formatDate(recipe.updateTime)
          }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import moment from "moment";
import { useRouter } from "vue-router";
import { recipeFindAll } from "../../../api/knowledge"; // 引入API函数

const router = useRouter();
const loading = ref(true);
const recipe = ref([]); // 存储课程数据的响应式变量

// 格式化日期
function formatDate(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}

// 在组件挂载后调用API
onMounted(async () => {
  try {
    const response = await recipeFindAll();
    recipe.value = response.data.data;
    loading.value = false; // 数据加载完毕，关闭加载状态
  } catch (error) {
    console.error("获取列表失败:", error);
    loading.value = false; // 请求失败也应关闭加载状态
  }
});
</script>

<style scoped>
.news-list {
  max-width: 1111px;
  margin: 0 auto;
}
.news-item {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 20px;
}
.news-image {
  width: 200px;
  height: 120px;
  object-fit: cover;
  margin-right: 20px;
}
.news-content {
  flex: 1;
}
.news-title {
  font-size: 18px;
  color: #333;
  margin: 0 0 10px 0;
}

.knowledge-link {
  text-decoration: none; /* 去除下划线 */
  color: #5c5c5c; /* 设置字体颜色 */
}

/* 当鼠标悬停在链接上时的样式 */
.knowledge-link:hover {
  color: #3498db; /* 鼠标悬停时的字体颜色 */
}

.news-date {
  font-size: 14px;
  color: #666;
  margin: 0 0 10px 0;
}
.news-description {
  font-size: 14px;
  color: #333;
}
</style>
