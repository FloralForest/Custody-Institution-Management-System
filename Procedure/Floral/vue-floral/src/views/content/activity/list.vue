<template>
  <div class="news-list">
    <div class="news-item" v-for="activity in activitys" :key="activity.id">
      <img :src="activity.activityCover" alt="News Image" class="news-image" />
      <div class="news-content">
        <h3
          class="news-title clickable-title"
          @click="() => goToActivityDetail(activity.id)"
        >
          {{ activity.activityName }}
        </h3>
        <p class="news-date">主办方：{{ activity.organizer }}</p>
        <p class="news-date">
          活动时间：{{ activity.startTime }} 至 {{ activity.endTime }}
        </p>
        <p class="news-description">{{ activity.description }}</p>
      </div>
    </div>
  </div>
  <ActivityDetails ref="activityDrawerRef" />
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import { activityFindAll } from '../../../api/activity'; // 引入API函数
import ActivityDetails from "./details/details.vue";

const router = useRouter();
const loading = ref(true);
const activitys = ref([]); // 存储课程数据的响应式变量
const currentDate = new Date().toDateString();
const activityDrawerRef = ref(null);

// 实现 formatDate 函数来格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];  // 将日期格式化为 YYYY-MM-DD
};

// 在组件挂载后调用API
onMounted(async () => {
  try {
    const response = await activityFindAll();
    activitys.value = response.data.data;
    loading.value = false; // 数据加载完毕，关闭加载状态
  } catch (error) {
    console.error('获取课程列表失败:', error);
    loading.value = false; // 请求失败也应关闭加载状态
  }
});

const goToActivityDetail = async (activityId) => {
  activityDrawerRef.value.fetchActivityDetails(activityId); 
  activityDrawerRef.value.openDrawer();
};
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
.clickable-title {
  cursor: pointer; /* 鼠标光标改变 */
  transition: color 0.3s; /* 添加颜色变化的过渡效果 */
}

.clickable-title:hover {
  color: #3498db; /* 您想要的悬停颜色，这里是一个示例 */
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
