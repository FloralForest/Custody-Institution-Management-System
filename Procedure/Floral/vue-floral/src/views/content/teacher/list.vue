<template>
  <div class="main-body">
    <div class="grid-container">
      <el-card
        style="max-width: 200px"
        v-for="teacher in teachers"
        :key="teacher.id"
        @click="() => goToTeacherDetail(teacher.id)"
      >
        <div class="image-container">
          <img :src="teacher.teachersAvatar" />
        </div>
        <p />
        <template #footer>
          <div class="teacherName">{{ teacher.teacherName }}</div>
        </template>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { teacherList } from "../../../api/teacher";

const loading = ref(true);
const router = useRouter();
const teachers = ref([]);

onMounted(async () => {
  try {
    const response = await teacherList();
    teachers.value = response.data.data;
    loading.value = false; // 数据加载完毕，关闭加载状态
  } catch (error) {
    console.error("获取教师列表失败:", error);
    loading.value = false; // 请求失败也应关闭加载状态
  }
});

const goToTeacherDetail = (teacherId) => {
  router.push({ name: 'teacherDetails', params: { id: teacherId } });
};
</script>

<style>
.main-body {
  padding: 10px 4rem;
}
.teacherName {
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
</style>
