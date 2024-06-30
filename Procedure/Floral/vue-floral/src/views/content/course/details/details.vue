<template>
  <div class="game-showcase">
    <div class="game-showcase-header">
      <h1>《{{ courseDetails.courseName }}》</h1>
      <el-popover placement="bottom" :width="250" trigger="click">
        <el-table :data="gridData" @row-click="handleRowClick">
          <el-table-column
            width="100"
            property="studentName"
            label="姓名"
          ></el-table-column>
          <el-table-column
            width="60"
            property="gender"
            label="性别"
          ></el-table-column>
          <el-table-column
            width="60"
            property="age"
            label="年龄"
          ></el-table-column>
        </el-table>

        <template #reference>
          <el-button type="text" class="choiceCourse" @click="queryChild">
            <h3>(。・∀・)ノ选择</h3>
          </el-button>
        </template>
      </el-popover>
    </div>
    <div class="game-showcase-body">
      <div class="game-showcase-main">
        <!-- 使用video标签代替img标签 -->
        <video
          preload="auto"
          controls
          muted
          v-if="courseVideoInfo"
          :src="courseVideoInfo.videoUrl"
        ></video>
        <video preload="auto" controls muted v-else></video>
      </div>
      <div class="game-showcase-sidebar">
        <div class="game-showcase-sidebar-image">
          <img :src="courseDetails.cover" alt="Sidebar Image" />
        </div>
        <p>创建时间：</p>
        <p>{{ formatDateTime(courseDetails.createTime) }}</p>
        <p>修改时间：</p>
        <p>{{ formatDateTime(courseDetails.updataTime) }}</p>
        <p>课程价格：{{ courseDetails.courseFees }}元</p>
        <p>课程分类：</p>
        <p>{{ courseDetails.courseClassify }}</p>
      </div>
      <div class="game-showcase-info-body">
        <div class="game-showcase-sidebar-middle">
          <div class="teacher-details-container">
            <div class="teacher-info">
              <p>创建教师：{{ teacherDetails.teacherName }}</p>
              <p>教师资历：{{ teacherDetails.career }}</p>
              <p>
                教师头衔：
                <el-tag
                  v-if="teacherDetails.level === 1"
                  type="success"
                  size="mini"
                  >高级教师</el-tag
                >
                <el-tag v-if="teacherDetails.level === 0" size="mini"
                  >普通教师</el-tag
                >
              </p>
              <p>专业课程：{{ teacherDetails.courseMajor }}</p>
              <p>入职日期：{{ teacherDetails.hireDate }}</p>
            </div>
            <div class="teacher-avatar">
              <img
                :src="teacherDetails.teachersAvatar"
                alt="Teacher's Avatar"
                class="game-showcase-sidebar-teacher"
              />
            </div>
          </div>
          <div class="teacher-intro">
            <p>教师简介：</p>
            <p>{{ teacherDetails.intro }}</p>
          </div>
        </div>
        <div class="game-showcase-lower">
          <p>代课教师：</p>
          <p v-if="teacherProxy">{{ teacherProxy.teacherName }}</p>
          <p v-else>无</p>
          <p>教师头衔：</p>
          <p v-if="teacherProxy">
            <el-tag v-if="teacherProxy.level === 1" type="success" size="mini"
              >高级教师</el-tag
            >
            <el-tag v-else-if="teacherProxy.level === 0" size="mini"
              >普通教师</el-tag
            >
          </p>
          <p v-else>无</p>
          <p>专业课程：</p>
          <p v-if="teacherProxy">{{ teacherProxy.courseMajor }}</p>
          <p v-else>无</p>
        </div>
      </div>
    </div>
    <div class="game-showcase-footer">
      <div class="game-showcase-intro-title">课程简介</div>
      <div class="game-showcase-thumbnails">
        <span>{{ courseDetails.description }}</span>
      </div>
    </div>
  </div>
  <div class="course-evaluation">
    <h3>课程评价</h3>
    <div v-if="hasEvaluations">
      <div class="review" v-for="ealuate in courseEvaluate" :key="ealuate.id">
        <div class="review-header">
          <p>
            {{ ealuate.userName }} 评价时间：{{
              formatDateTime(ealuate.createEvaluateTime)
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
          <el-button type="info" @click="cancelDepict()">取消</el-button>
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
import { ref, onMounted, onUnmounted, nextTick, computed } from "vue";
import { useStore } from "vuex";
import moment from "moment";
import { ElAffix, ElDrawer, ElMessage } from "element-plus";
import { useRoute } from "vue-router";
import {
  courseArray,
  courseVideo,
  getClassify,
  courseProxy,
  activityEvaluate,
  saveEvaluate,
  getEvaluate,
  updataEvaluate,
  addSelectCourse,
} from "../../../../api/course";
import { getTeacherById } from "../../../../api/teacher";
import { getParentById, getChildren } from "../../../../api/parent";
import { getUserById } from "../../../../api/userList";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const route = useRoute();
const courseId = route.params.id; // 获取路由参数id
const courseDetails = ref({}); // 创建一个响应式变量来保存课程详情
const courseVideoInfo = ref({}); // 创建一个响应式变量来保存视频详情
const teacherDetails = ref({}); // 创建一个响应式变量来保存教师详情
const teacherProxy = ref({}); // 保存代课教师详情
const courseEvaluate = ref([]); //课程评价
const addEvaluate = ref({}); //添加课程评价
const isFixed = ref(false); // 控制固定状态的响应式变量
const userData = ref([]); // 存储用户数据的响应式变量
const parentDetails = ref([]); // 选择课程查询家长的响应式变量
const gridData = ref([]); // 选择课程查询孩子的响应式变量

// 时间格式化函数
function formatDateTime(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}
const handleScroll = () => {
  const scrollDistance = 130; // 从页面顶部向下滚动 300px 后开始固定
  if (window.scrollY > scrollDistance) {
    isFixed.value = true;
  } else {
    isFixed.value = false;
  }
};

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

onMounted(async () => {
  try {
    // 先获取用户数据
    const userResponse = await getUserById(user.value.loginUserId);
    userData.value = userResponse.data.data;
    const courseResponse = await courseArray(courseId);
    courseDetails.value = courseResponse.data.data[0]; //课程详情
    const classifyResponse = await getClassify(courseDetails.value.classifyId);
    courseDetails.value.courseClassify =
      classifyResponse.data.data[0].classifyName;
    const teacherResponse = await getTeacherById(courseDetails.value.teacherId);
    teacherDetails.value = teacherResponse.data.data;
    if (userData.value.parentId) {
      const parentResponse = await getParentById(userData.value.parentId); //家长
      parentDetails.value = parentResponse.data.data;
    } else {
      ElMessage.error("注意！您未绑定信息");
    }
    if (courseDetails.value) {
      const videoResponse = await courseVideo(courseId);
      courseVideoInfo.value = videoResponse.data.data;
      const proxyResponse = await courseProxy(courseId);
      teacherProxy.value = proxyResponse.data.data; //代课详情
      const evaluateResponse = await activityEvaluate(courseId); //评价
      if (
        evaluateResponse.data.data &&
        Array.isArray(evaluateResponse.data.data.courseEvaluateDTOList)
      ) {
        courseEvaluate.value = evaluateResponse.data.data.courseEvaluateDTOList;
      } else {
        // 处理 data.courseEvaluateDTOList 不是数组的情况
        courseEvaluate.value = [];
      }
    }
  } catch (error) {
    console.error("获取课程详情失败:", error);
  }
});
const hasEvaluations = computed(() => courseEvaluate.value?.length > 0);

const submitEvaluation = async () => {
  if (!addEvaluate.value.id) {
    try {
      addEvaluate.value.courseId = courseId;
      addEvaluate.value.peopleId = user.value.loginUserId;
      if (!user.value.loginUserId) {
        ElMessage.error("请登录！");
        return;
      }
      const response = await saveEvaluate(addEvaluate.value);
      if (response.data.code === 20000) {
        courseEvaluate.value.push({
          ...addEvaluate.value,
          userName: user.value.loginUserName,
        });
        addEvaluate.value = {
          fraction: 0,
          content: "",
          courseId: courseId,
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
      const index = courseEvaluate.value.findIndex(
        (e) => e.evaluateId === addEvaluate.value.id
      );
      if (index !== -1) {
        courseEvaluate.value[index] = {
          ...courseEvaluate.value[index],
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
const cancelDepict = () => {
  addEvaluate.value = {
    fraction: 0,
    content: "",
  };
};

//选择课程
//查询孩子
const queryChild = async () => {
  if (!user.value.loginUserId) {
    ElMessage.error("请先登录");
    return;
  }
  if (!parentDetails.value.id) {
    ElMessage.error("请先绑定信息叭~");
    return;
  }
  const response = await getChildren(parentDetails.value.id);
  if (response.data.code === 20000) {
    gridData.value = response.data.data;
    if (response.data.data == "") {
      ElMessage.error(`信息获取失败(ノへ￣、): 请先添加子女叭~`);
    } else {
      ElMessage.success("请帮TA选择叭~");
    }
  } else {
    const errorMessage = response.data.message || "请先添加子女叭~";
    ElMessage.error(`内容获取失败(ノへ￣、): ${errorMessage}`);
    return;
  }
};
const handleRowClick = async (row) => {
  const response = await addSelectCourse(row.studentId, courseId);
  if (response.data.code === 20000) {
    ElMessage.success("已经选择~");
  } else {
    const errorMessage = response.data.message || "选择失败-";
    ElMessage.error(`选择失败(ノへ￣、): ${errorMessage}`);
    return;
  }
};
</script>

<style scoped>
.game-showcase {
  font-family: Arial, sans-serif;
  color: #fff;
  /* background-color: #1a1a1a; */
  background: rgba(255, 255, 255, 0.3); /* 半透明白色 */
  max-width: 85%;
  max-height: 900px;
  margin: auto;
  border-radius: 8px;
  overflow: hidden;
}

.game-showcase-header {
  background-color: #2a2a2a;
  padding: 1px 30px;
  width: 100%;
  display: flex; /* 使用Flexbox布局 */
  align-items: center; /* 垂直居中对齐子元素 */
}
.choiceCourse h3 {
  margin: 0px 0px 5px 20px; /* 移除默认的margin */
}

.game-showcase-body {
  display: flex;
}

.game-showcase-main {
  width: 60%;
  height: 460px;
  display: block;
  padding: 0; /* 确保没有内边距 */
  border: none; /* 确保没有边框 */
}
.game-showcase-main video {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 会使视频填充整个容器，可能会裁剪部分视频以保持比例 */
}
.game-showcase-sidebar {
  width: 13.5%;
  padding: 10px;
  background-color: #333;
}
.game-showcase-sidebar-image img {
  width: 150px;
  height: 100px;
}
.game-showcase-sidebar-middle {
  width: 100%;
  background-color: #333;
}

.teacher-details-container {
  width: 100%;
  display: flex;
  align-items: flex-start;
}

.teacher-info {
  width: 100%;
  max-width: calc(100% - 150px); /* 计算除去图片宽度后的最大宽度 */
}

.teacher-avatar {
  padding-top: 15px;
  margin-left: 20px; /* 与教师信息之间的间距 */
}

.game-showcase-sidebar-teacher {
  width: 120px; /* 限制图片宽度 */
  height: auto; /* 保持图片高度自适应 */
  border-radius: 8px; /* 圆角图片 */
}

.teacher-intro {
  height: 200px;
  display: -webkit-box;
  -webkit-line-clamp: 8; /* 限制行 */
  -webkit-box-orient: vertical; /* 设置盒子的方向为垂直 */
  overflow: hidden; /* 隐藏超出的内容 */
  text-overflow: ellipsis; /* 显示省略号 */
  max-height: 13em; /* 行高 x 行数，确保内容不超过n行 */
}
.game-showcase-info-body {
  width: 400px;
  height: 460px;
}
.game-showcase-lower {
  font-size: 10%;
  width: 100%;
  height: 12.2%;
  background-color: #333;
  display: flex; /* 使用 Flexbox 布局 */
  align-items: center; /* 垂直居中对齐子元素 */
  gap: 8px; /* 在元素之间添加一些间距 */
}

.game-showcase-footer {
  background-color: #2a2a2a;
  padding: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.game-showcase-thumbnails {
  padding: 5px;
  display: flex;
  align-items: center;
}

.game-showcase-footer {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 使内容左对齐 */
}

.game-showcase-intro-title {
  margin-bottom: 10px; /* 根据需要调整间距 */
  font-weight: bold;
}
.game-showcase-actions button {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 5px 10px;
  margin-right: 5px;
  cursor: pointer;
}

.game-showcase-actions button:hover {
  background-color: #0056b3;
}

.thumbnail-prev,
.thumbnail-next {
  background: none;
  border: none;
  color: #fff;
  font-size: 20px;
  cursor: pointer;
}

.thumbnail-prev:hover,
.thumbnail-next:hover {
  color: #007bff;
}

.course-evaluation {
  width: 82%;
  background-color: #e3e3e3d6; /* 设置评价区域的背景颜色 */
  padding: 20px; /* 设置内边距 */
  border-radius: 8px; /* 设置圆角 */
  margin: 20px auto; /* 与课程信息部分的间距 */
}
.review {
  border-bottom: 1px solid #78787890; /* 设置评价之间的分割线 */
  padding-bottom: 10px; /* 设置分割线上方的内边距 */
  margin-bottom: 10px; /* 设置分割线下方的外边距 */
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

.course-evaluation-form h3 {
  margin-bottom: 15px; /* 设置标题与表单的间距 */
}

.el-form-item {
  margin-bottom: 15px; /* 设置表单元素之间的间距 */
}

.el-rate {
  margin-bottom: 5px; /* 设置评分组件底部的间距 */
}

.el-button {
  margin-top: 10px; /* 设置按钮与表单元素的间距 */
}

/* 如果需要对 Element Plus 组件进行定制样式，可以使用类似以下代码 */
.el-form-item__label {
  color: #666; /* 自定义标签颜色 */
}

.el-input__inner {
  border-radius: 4px; /* 设置输入框的圆角 */
}

.el-button--primary {
  background-color: #409eff; /* 设置主要按钮的背景颜色 */
  border-color: #409eff; /* 设置主要按钮的边框颜色 */
}

.el-button--primary:hover {
  background-color: #66b1ff; /* 设置主要按钮悬停时的背景颜色 */
  border-color: #66b1ff; /* 设置主要按钮悬停时的边框颜色 */
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

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.review-info {
  flex-grow: 1; /* 允许评价文本占据多余的空间 */
}

.update-evaluate {
  margin-left: auto; /* 将按钮推向右边 */
  white-space: nowrap; /* 防止按钮文本换行 */
}
</style>
