<template>
  <div class="main-body">
    <div class="grid-container">
      <el-card
        style="max-width: 480px"
        v-for="course in courses"
        :key="course.id"
        @click="() => goToCourseDetail(course.id)"
      >
        <div class="image-container">
          <img :src="course.cover" />
        </div>
        <p />
        <div class="courseName">《{{ course.courseName }}》</div>
        <p />
        <div>创建时间：{{ formatDate(course.createTime) }}</div>
        <template #footer
          ><div class="course-description">
            {{ course.description }}
          </div></template
        >
      </el-card>
    </div>
  </div>
</template>

<script lang="js" setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { courseFindAll } from '../../../api/course'; // 引入API函数

const loading = ref(true);
const router = useRouter();
const currentDate = new Date().toDateString();
const courses = ref([]); // 存储课程数据的响应式变量

// 实现 formatDate 函数来格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];  // 将日期格式化为 YYYY-MM-DD
};
// 定义跳转到课程详情页的方法
const goToCourseDetail = (courseId) => {
  router.push({ name: 'courseDetails', params: { id: courseId } });
};
// 在组件挂载后调用API
onMounted(async () => {
  try {
    const response = await courseFindAll();
    courses.value = response.data.data;
    loading.value = false; // 数据加载完毕，关闭加载状态
  } catch (error) {
    console.error('获取课程列表失败:', error);
    loading.value = false; // 请求失败也应关闭加载状态
  }
});
</script>
<style>
.main-body {
  padding: 10px 4rem;
}
.courseName {
  font-weight: 900;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 16px; /* 设置你想要的间隔大小 */
  justify-content: center; /* 卡片居中对齐 */
}
/*图片包装器*/
.image-container {
  width: 100%;
  height: 200px; /* 设置固定高度 */
  overflow: hidden; /* 超出部分隐藏 */
  position: relative; /* 用于定位图片 */
}
.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片的宽高比 */
  position: absolute; /* 绝对定位 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 居中图片 */
}
.course-description {
  display: -webkit-box;
  -webkit-line-clamp: 3; /* 限制在三行 */
  -webkit-box-orient: vertical; /* 设置盒子的方向为垂直 */
  overflow: hidden; /* 隐藏超出的内容 */
  text-overflow: ellipsis; /* 显示省略号 */
  line-height: 1.6; /* 根据需要调整行高 */
  max-height: 4.8em; /* 行高 x 行数，确保内容不超过三行 */
}
</style>
