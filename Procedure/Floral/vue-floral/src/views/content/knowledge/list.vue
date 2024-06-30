<template>
  <div class="news-list">
    <div class="news-item" v-for="knowledge in knowledge" :key="knowledge.id">
      <div class="news-content">
        <h3 class="news-title">
          <a
            :href="`/knowledge/kwedetails/${knowledge.id}`"
            target="_blank"
            class="knowledge-link"
            >{{ knowledge.knowledgeTitle }}</a
          >
        </h3>
        <p class="news-date">来源：{{ knowledge.source }}</p>
        <p class="news-date">
          发布时间：{{ formatDate(knowledge.createTime) }} &gt;更新时间：{{
            formatDate(knowledge.updateTime)
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
import { knowledgeFindAll } from "../../../api/knowledge"; // 引入API函数

const router = useRouter();
const loading = ref(true);
const knowledge = ref([]); // 存储课程数据的响应式变量

// 格式化日期
function formatDate(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}

// 在组件挂载后调用API
onMounted(async () => {
  try {
    const response = await knowledgeFindAll();
    knowledge.value = response.data.data;
    loading.value = false; // 数据加载完毕，关闭加载状态
  } catch (error) {
    console.error("获取课程列表失败:", error);
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
