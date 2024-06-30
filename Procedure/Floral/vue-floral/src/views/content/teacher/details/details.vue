<template>
  <div class="profile-card">
    <div class="profile-image">
      <img :src="teacherData.teachersAvatar" alt="Profile Image" />
    </div>
    <div class="profile-info">
      <h1>{{ teacherData.teacherName }}</h1>
      <p>
        <el-tag v-if="teacherData.level === 1" type="success" size="mini"
          >高级教师</el-tag
        >
        <el-tag v-else-if="teacherData.level === 0" size="mini"
          >普通教师</el-tag
        >
      </p>
      <div class="profile-contact">
        <div class="contact-item">
          <span>专业课程：</span>
          <span>{{ teacherData.courseMajor }}</span>
        </div>
        <div class="contact-item">
          <span>性别：</span>
          <span>{{ userData.sex }}</span>
        </div>
        <div class="contact-item">
          <span>电话：</span>
          <span>{{ userData.phone }}</span>
        </div>
        <div class="contact-item">
          <span>入职日期：</span>
          <span>{{ teacherData.hireDate }}</span>
        </div>
      </div>
      <div class="profile-contact">
        <div class="contact-item">
          <span>教师资历：</span>
          <span>{{ teacherData.career }}</span>
        </div>
      </div>
      <div class="profile-contact">
        <div class="contact-item-synopsis">
          <span class="synopsis-span">简介：</span>
          <p class="synopsis-P">{{ teacherData.intro }}</p>
        </div>
      </div>
    </div>
  </div>
  <div class="course-evaluation">
    <h3>评价</h3>
    <div v-if="hasEvaluations">
      <div class="review" v-for="ealuate in teacherEvaluate" :key="ealuate.id">
        <div class="review-header">
          <p>
            {{ ealuate.userName }} 评价时间：{{
              formatDateTime(ealuate.createTime)
            }}
          </p>
          <el-button
            v-if="ealuate.peopleId === user.loginUserId"
            type="text"
            @click="updataUserEvaluate(ealuate.evaluateId)"
            class="updataEvaluate"
            >修改</el-button
          >
        </div>
        <p>
          评分：<el-rate
            v-model="ealuate.fraction"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value} 分"
          />
        </p>
        <p>{{ ealuate.content }}</p>
      </div>
    </div>
    <p v-else>暂无评价</p>
  </div>
  <el-affix v-if="isFixed" position="bottom" :offset="30">
    <div class="course-evaluation-form">
      <h3>发布您的评价</h3>
      <el-form @submit.prevent="submitEvaluation">
        <el-form-item label="评分">
          <el-rate
            v-model="addEvaluate.fraction"
            allow-half
            show-score
            text-color="#ff9900"
          />
        </el-form-item>
        <el-form-item label="评价内容">
          <el-input
            type="textarea"
            v-model="addEvaluate.content"
            placeholder="请输入评价内容"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">发布评价</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-affix>
  <el-backtop
    :right="100"
    :bottom="100"
    style="margin-right: -75px; z-index: 100"
  />
</template>

<script setup>
import { ref, onUnmounted, onMounted, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import moment from "moment";
import {
  getTeacherById,
  teacherEvaluateAll,
  saveEvaluate,
  getEvaluate,
  updataEvaluate,
} from "../../../../api/teacher";
import { getUserById } from "../../../../api/userList";
import { ElMessage } from "element-plus";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const teacherData = ref([]);
const userData = ref({});
const route = useRoute();
const teacherId = route.params.id; // 获取路由参数id
const teacherEvaluate = ref([]); //评价
const isFixed = ref(false); // 控制固定状态的响应式变量
const addEvaluate = ref({}); //添加评价

// 时间格式化函数
function formatDateTime(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}
const handleScroll = () => {
  const scrollDistance = 5; // 从页面顶部向下滚动后开始固定
  if (window.scrollY > scrollDistance) {
    isFixed.value = true;
  } else {
    isFixed.value = false;
  }
};

onUnmounted(() => {
  if (teacherEvaluate.value.length > 1) {
    window.removeEventListener("scroll", handleScroll);
  }
});

onMounted(async () => {
  const teacherResponse = await getTeacherById(teacherId);
  if (teacherResponse === 20001) {
    const errorMessage = teacherResponse.data.message || "获取教师失败";
    ElMessage.error(`获取教师失败: ${errorMessage}`);
    return;
  }
  teacherData.value = teacherResponse.data.data;
  if (teacherData.value && teacherData.value.userId) {
    const userResponse = await getUserById(teacherData.value.userId);
    userData.value = userResponse.data.data;
    const evaluateResponse = await teacherEvaluateAll(teacherId); //评价
    if (
      evaluateResponse.data.data &&
      Array.isArray(evaluateResponse.data.data.teacherEvaluateDTOList)
    ) {
      teacherEvaluate.value = evaluateResponse.data.data.teacherEvaluateDTOList;
      if (teacherEvaluate.value.length > 1) {
        // 如果有评价，则添加滚动监听
        window.addEventListener("scroll", handleScroll);
        // 并且立即执行一次滚动处理函数以设置初始状态
        handleScroll();
      } else {
        // 如果没有评价，则设置 isFixed 为 false
        isFixed.value = true;
      }
    } else {
      // 处理不是数组的情况
      teacherEvaluate.value = [];
    }
  } else {
    ElMessage.error("后台添加教师，未完善信息，无法评价");
  }
});
const hasEvaluations = computed(() => teacherEvaluate.value?.length > 0);

const submitEvaluation = async () => {
  if (!addEvaluate.value.id) {
    try {
      addEvaluate.value.teacherId = teacherId;
      addEvaluate.value.peopleId = user.value.loginUserId;
      if (!user.value.loginUserId) {
        ElMessage.error("请登录！");
        return;
      }
      const response = await saveEvaluate(addEvaluate.value);
      if (response.data.code === 20000) {
        teacherEvaluate.value.push({
          ...addEvaluate.value,
          userName: user.value.loginUserName,
        });
        addEvaluate.value = {
          fraction: 0,
          content: "",
          teacherId: teacherId,
          peopleId: user.value.loginUserId,
        };
        ElMessage.success("评价成功");
      } else {
        const errorMessage = response.data.message || "评价失败";
        ElMessage.error(`评价失败: ${errorMessage}`);
        return;
      }
    } catch (error) {
      console.error("评价提交失败:", error);
    }
  } else {
    const response = await updataEvaluate(addEvaluate.value);
    if (response.data.code === 20000) {
      // 找到并更新数组中的评价对象
      const index = teacherEvaluate.value.findIndex(
        (e) => e.evaluateId === addEvaluate.value.id
      );
      if (index !== -1) {
        teacherEvaluate.value[index] = {
          ...teacherEvaluate.value[index],
          ...addEvaluate.value,
          //   userName: user.value.loginUserName, // 更新用户名
        };
        addEvaluate.value = {
          fraction: 0,
          content: "",
        };
      }
      ElMessage.success("评价修改成功");
    } else {
      const errorMessage = response.data.message || "评价修改失败";
      ElMessage.error(`评价修改失败: ${errorMessage}`);
      return;
    }
  }
};

//评价修改(查询选中的评价)
const updataUserEvaluate = async (id) => {
  try {
    const response = await getEvaluate(id);
    if (response.data.code === 20000) {
      addEvaluate.value = response.data.data;
      ElMessage.success("内容回来咯~快去修改叭");
    } else {
      const errorMessage = response.data.message || "内容获取失败";
      ElMessage.error(`内容获取失败(ノへ￣、): ${errorMessage}`);
      return;
    }
  } catch {
    ElMessage.error("获取失败，请刷新页面再尝试(*￣3￣)╭");
  }
};
</script>

<style>
.profile-card {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
}
.profile-image img {
  width: 120px; /* 限制图片宽度 */
  height: auto; /* 保持图片高度自适应 */
  border-radius: 8px; /* 圆角图片 */
}
.profile-info {
  margin-left: 20px;
}
.profile-info h1 {
  margin: 0;
  color: #333;
}
.profile-info p {
  color: #666;
  margin: 5px 0;
}
.profile-contact {
  display: flex;
  margin-top: 10px;
}
.contact-item {
  display: flex;
  align-items: center;
  margin-right: 15px;
}
.contact-item-synopsis {
  display: flex;
}
.contact-item-synopsis {
  display: flex;
  flex-direction: column;
}

.synopsis-span {
  width: 50px;
}

.synopsis-P {
  margin-left: 50px;
  text-align: justify;
}

.course-evaluation {
  width: 97%;
  background-color: #e3e3e3d6; /* 设置评价区域的背景颜色 */
  padding: 20px; /* 设置内边距 */
  border-radius: 8px; /* 设置圆角 */
  margin: 50px auto; /* 与课程信息部分的间距 */
}
.review {
  border-bottom: 1px solid #78787890; /* 设置评价之间的分割线 */
  padding-bottom: 10px; /* 设置分割线上方的内边距 */
  margin-bottom: 10px; /* 设置分割线下方的外边距 */
}
.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.review:last-child {
  border-bottom: none; /* 最后一个评价不需要分割线 */
}

.course-evaluation-form {
  width: 90%;
  background-color: #f8f8f856; /* 设置表单区域的背景颜色 */
  padding: 20px; /* 设置内边距 */
  border-radius: 8px; /* 设置圆角 */
  margin: 20px auto 20px auto; /* 与评价展示部分的间距 */
  position: sticky;
  bottom: 0;
  z-index: 10; /* 确保表单在其他内容之上 */
}
.el-form-item {
  margin-bottom: 15px; /* 设置表单元素之间的间距 */
}
.course-evaluation-form h3 {
  margin-bottom: 15px; /* 设置标题与表单的间距 */
}
/* 适应移动设备和小屏幕 */
@media (max-width: 768px) {
  .course-evaluation-form {
    padding: 15px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }
}
</style>
