<template>
  <el-drawer
    v-model="drawer"
    title="活动信息"
    size="75%"
    @after-leave="afterDrawerClosed"
  >
    <div class="drawer-content">
      <div
        v-if="activityDetails.activityCover"
        class="activity-background"
        :style="backgroundStyle"
      ></div>
      <div class="content">
        <p class="artistic-font">
          {{ activityDetails.activityName }}
        </p>
        <p class="activityTime-font">活动时间</p>
        <div class="activityCard">
          <div class="card">
            <div class="card-header">活动开始</div>
            <div class="card-body">
              <div class="card-date">
                {{ formatDateTime(activityDetails.startTime) }}
              </div>
              <div class="card-time">00:00</div>
            </div>
          </div>
          <div class="card-between">></div>
          <div class="card-right">
            <div class="card-header">活动结束</div>
            <div class="card-body">
              <div class="card-date">
                {{ formatDateTime(activityDetails.endTime) }}
              </div>
              <div class="card-time">23:59</div>
            </div>
          </div>
        </div>
        <p class="activity-Introduce">&gt;活动简介&lt;</p>
        <div class="press-releases">
          <div class="card-synopsis">
            <div class="card-header-synopsis">
              <h2>
                {{ activityDetails.description }}
              </h2>
              <h4>费用：{{ activityDetails.activityFee }}</h4>
              <div class="card-meta-synopsis">
                <span class="author"
                  >主办方：{{ activityDetails.organizer }}</span
                >
                <span class="date"
                  >分类：{{ activityDetails.activityClassifyName }}</span
                >
              </div>
            </div>
            <p>
              {{ activityDetails.location }}
            </p>
          </div>
          <div class="card-synopsis">
            <div class="card-header-synopsis">
              <div class="card-meta-synopsis">
                <span class="author"
                  >活动教师：{{ teacherDetails.teacherName }}</span
                >
                <span class="date"
                  >教师头衔：
                  <el-tag
                    v-if="teacherDetails.level === 1"
                    type="success"
                    size="mini"
                    >高级教师</el-tag
                  >
                  <el-tag v-if="teacherDetails.level === 0" size="mini"
                    >普通教师</el-tag
                  >
                </span>
                <span class="date"
                  >入职日期：{{ teacherDetails.hireDate }}</span
                >
              </div>
            </div>
            <p>
              {{ teacherDetails.career }}
            </p>
          </div>
        </div>
        <p />
        <div class="activityCard-enroll">
          <div class="card-enroll">
            <div class="card-header-enroll">报名开始</div>
            <div class="card-body-enroll">
              <div class="card-date-enroll">
                {{ formatDateTime(activityDetails.registrationTime) }} 00:00
              </div>
            </div>
          </div>
          <div class="card-between-enroll">》》》》</div>
          <div class="card-right-enroll">
            <div class="card-header-enroll">报名结束</div>
            <div class="card-body-enroll">
              <div class="card-date-enroll">
                {{ formatDateTime(activityDetails.expirationTime) }} 23:59
              </div>
            </div>
          </div>
        </div>
        <p class="updateTime">
          修改时间：{{ dateTime(activityDetails.updateTime) }}
        </p>
        <div class="activity-bottom">
          <el-popover placement="top" :width="250" trigger="click">
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
              <el-button class="activity-button" @click="participateActivity"
                >点击立即参与</el-button
              >
            </template>
          </el-popover>
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script setup>
import {
  ref,
  computed,
  defineProps,
  watch,
  onBeforeUnmount,
  nextTick,
} from "vue";
import { useStore } from "vuex";
import moment from "moment";
import { ElMessage } from "element-plus";
import { getUserById } from "../../../../api/userList";
import { activityGetId, getActivityClassify } from "../../../../api/activity";
import { getTeacherById } from "../../../../api/teacher";
import { getChildren } from "../../../../api/parent";
import { addSelectActivity } from "../../../../api/activity";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const userData = ref([]);
const activityDetails = ref({});
const teacherDetails = ref({});
const drawer = ref(false);
const gridData = ref([]);
const activity = ref({
  activityId: {
    type: Number,
    default: null,
  },
});

const backgroundStyle = computed(() => ({
  backgroundImage: activityDetails.value.activityCover
    ? `url(${activityDetails.value.activityCover})`
    : "",
}));

// 时间格式化函数
function formatDateTime(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD");
}
function dateTime(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}
const fetchActivityDetails = async (activityId) => {
  try {
    activity.value.activityId = activityId;
    const response = await activityGetId(activityId);
    if (response.data.code === 20000) {
      activityDetails.value = response.data.data[0];
      const activityResponse = await getActivityClassify(
        activityDetails.value.activityClassifyId
      );
      activityDetails.value.activityClassifyName =
        activityResponse.data.data.classifyName;
      const teacherResponse = await getTeacherById(
        activityDetails.value.activityAgent
      );
      teacherDetails.value = teacherResponse.data.data;
    } else {
      throw new Error(response.data.message || "查询失败");
    }
  } catch (error) {
    ElMessage.error(`查询失败: ${error.message}`);
  }
};
const participateActivity = async () => {
  if (user.value.loginUserId) {
    const userResponse = await getUserById(user.value.loginUserId);
    userData.value = userResponse.data.data;
    if (!userData.value) {
      return;
    }
    const response = await getChildren(userData.value.parentId);
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
  }
};

const handleRowClick = async (row) => {
  const response = await addSelectActivity(
    row.studentId,
    activity.value.activityId
  );
  if (response.data.code === 20000) {
    ElMessage.success("已经选择~");
  } else {
    const errorMessage = response.data.message || "选择失败-";
    ElMessage.error(`选择失败(ノへ￣、): ${errorMessage}`);
    return;
  }
};
const openDrawer = () => {
  drawer.value = true;
};

const afterDrawerClosed = () => {
  nextTick(() => {
    activityDetails.value = {};
  });
};

// 当组件卸载前重置数据
onBeforeUnmount(() => {
  activityDetails.value = {};
});

defineExpose({
  fetchActivityDetails,
  openDrawer,
});
</script>

<style scoped>
.drawer-content {
  width: 100%;
  height: 100%;
  position: relative;
  overflow-y: auto;
  z-index: 0; /* 设置为基础层级 */
}

.activity-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* 确保宽度填满抽屉 */
  height: 100%; /* 设置高度为抽屉的高度 */
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  z-index: -1; /* 将背景设置在内容之下 */
}

.content {
  position: relative;
  z-index: 5; /* 确保内容在背景之上 */
  padding: 20px; /* 示例内边距 */
  background: rgba(255, 255, 255, 0.3);
  overflow-y: auto; /* 添加滚动条到内容层 */
  height: 93.8%; /* 设置内容层高度 */
}

.artistic-font {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  font-family: "Long Cang", cursive;
  font-style: normal;
  font-size: 120px;
  color: hsl(180, 39%, 94%);
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 添加阴影效果 */
}
.activityTime-font {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  font-family: "ZCOOL XiaoWei", sans-serif;
  font-style: normal;
  font-size: 80px;
  color: hsl(0, 0%, 100%);
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 添加阴影效果 */
}
.activity-Introduce {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  font-family: "ZCOOL XiaoWei", sans-serif;
  font-style: normal;
  font-size: 80px;
  color: hsl(44, 57%, 91%);
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* 添加阴影效果 */
}
.activityCard {
  display: flex; /* 使用 Flexbox 布局 */
  align-items: center; /* 垂直居中对齐子元素 */
}

.card {
  margin: 0 0 0 280px;
  width: 15%;
  background-color: #f7f4f4;
  padding: 15px;
  border-radius: 5px;
}
.card-between {
  width: 15%;
  font-size: 66px;
  text-align: center;
  color: hsl(180, 16%, 86%);
}
.card-right {
  width: 15%;
  background-color: #f7f4f4;
  padding: 15px;
  border-radius: 5px;
}
.card-header {
  font-size: 16px;
  text-align: center;
  font-weight: bold;
  border-bottom: 1px solid #b97d7d;
  padding-bottom: 10px;
  margin-bottom: 10px;
}
.card-body {
  text-align: center;
}
.card-date {
  font-size: 20px;
  color: rgb(73, 63, 63);
  margin-bottom: 5px;
}
.card-time {
  font-size: 18px;
  color: #aaa7a7;
}

.press-releases {
  font-family: Arial, sans-serif;
  color: #333;
  max-width: 950px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 6px;
}

.card-synopsis {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}
.card-header-synopsis {
  margin-bottom: 15px;
}

.card-synopsis h2 {
  margin: 5px 0;
  font-size: 20px;
  color: #333;
}
.card-meta-synopsis {
  font-size: 14px;
  color: #666;
  text-align: center;
}
.card-meta-synopsis span {
  font-size: 22px;
  margin-right: 10px;
}

.activityCard-enroll {
  margin-top: 6%;
  display: flex; /* 使用 Flexbox 布局 */
  align-items: center; /* 垂直居中对齐子元素 */
}

.card-enroll {
  margin: 0 0 0 110px;
  width: 25%;
  background-color: #f8dbcfcc;
  padding: 15px;
  border-radius: 5px;
}
.card-between-enroll {
  width: 25%;
  font-size: 66px;
  text-align: center;
  color: hsl(180, 16%, 86%);
}
.card-right-enroll {
  width: 25%;
  background-color: #f8dbcfcc;
  padding: 15px;
  border-radius: 5px;
}
.card-header-enroll {
  font-size: 16px;
  text-align: center;
  font-weight: bold;
  border-bottom: 1px solid #b97d7d;
  padding-bottom: 10px;
  margin-bottom: 10px;
}
.card-body-enroll {
  text-align: center;
}
.card-date-enroll {
  font-size: 20px;
  color: rgb(73, 63, 63);
  margin-bottom: 5px;
}
.updateTime {
  margin-top: 5%;
  text-align: center;
  color: #ebf3f3;
}

.activity-bottom {
  text-align: center;
}

.activity-button {
  width: 25%;
  background-color: rgba(150, 149, 148, 0.571);
  font-size: 20px;
  color: #fbe6c7;
}
</style>
