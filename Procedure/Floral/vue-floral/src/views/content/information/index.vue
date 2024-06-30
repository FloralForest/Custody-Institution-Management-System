<template>
  <div class="user-profile">
    <div class="user-info">
      <el-form-item>
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-error="handleAvatarError"
          :action="uploadAction"
          class="avatar-uploader"
        >
          <img v-if="userData.avatar" :src="userData.avatar" class="avatar" />
          <span v-else class="avatarNull">+头像</span>
        </el-upload>
      </el-form-item>
      <div class="user-details">
        <div class="user-name-container">
          <span class="user-name"
            >{{ userData.username }}
            <el-button
              type="warning"
              link
              v-if="userData.parentId === null"
              @click="bindingInfo"
              >去绑定</el-button
            >
          </span>
          <el-button type="text" @click="updataUserInfo">修改</el-button>
        </div>
        <div class="user-stats">
          <span class="user-stat">{{ userData.phone }}</span>
          <span class="user-stat">{{ parentData.email }}</span>
          <span class="user-stat">{{ parentData.parentName }}</span>
          <span class="user-stat">{{ userData.sex }}</span>
          <span class="user-stat">备注：{{ parentData.remark }}</span>
        </div>
        <div class="user-stats">
          <span class="user-stat">地址：{{ parentData.address }}</span>
        </div>
        <div class="user-stats-photo">
          <img
            v-if="parentData.parentAvatar"
            :src="parentData.parentAvatar"
            alt="图片"
            class="photo"
          />
          <div v-else class="photo-placeholder">图片</div>
        </div>
      </div>
    </div>
    <div class="user-tasks">
      <div class="user-actions">
        <button class="action-button" v-if="user.loginRoles !== 2">
          📖我的课程
        </button>
        <button
          class="action-button"
          v-if="userData.parentId !== null"
          @click="addChildren"
        >
          😊添加子女
        </button>
      </div>
      <el-table
        :data="studentData"
        @row-click="handleRowClick"
        style="width: 100%"
        v-if="userData.parentId !== null"
      >
        <el-table-column label="图片" width="200" align="center">
          <template v-slot="scope">
            <img :src="scope.row.photo" alt="图片" class="studentAvatar" />
          </template>
        </el-table-column>
        <el-table-column prop="studentName" label="姓名" width="120" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="birthday" label="出生日期" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="relationship" label="与你的关系" />
        <el-table-column prop="enrollmentDate" label="登记时间" />
        <el-table-column>
          <template v-slot="scope">
            <div
              class="buttons-container"
              style="display: flex; flex-direction: column; align-items: center"
            >
              <el-button
                type="text"
                @click.stop="health(scope.row.studentId, scope.row.studentName)"
                >健康信息></el-button
              >
              <el-button
                type="text"
                class="xuankehd"
                @click.stop="counselling(scope.row.studentId)"
                >辅导信息></el-button
              >
              <el-button
                type="text"
                class="xuankehd"
                @click.stop="courseInfo(scope.row.studentId)"
                >选课信息></el-button
              >
              <el-button
                type="text"
                class="xuankehd"
                @click.stop="activityInfo(scope.row.studentId)"
                >活动信息></el-button
              >
              <el-popconfirm
                confirm-button-text="Yes"
                cancel-button-text="No"
                title="确定需要删除吗?"
                @confirm="removeStudents(scope.row.studentId)"
              >
                <template #reference>
                  <!-- @click.stop="() => {}" 阻止事件冒泡 -->
                  <el-button type="text" class="xuankehd" @click.stop="() => {}"
                    >删除</el-button
                  >
                </template>
              </el-popconfirm>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <UpdataDrawer ref="userDrawerRef" />
  <Condition
    ref="conditionRef"
    :studentId="currentStudentId"
    :studentName="currentStudentName"
  />
  <Counselling ref="CounsellingRef" :studentId="counsellingStudentId" />
  <CourseInfo ref="CourseInfoRef" :studentId="currentCourseStudentId" />
  <ActivityInfo ref="ActivityInfoRef" :studentId="currentActivityStudentId" />
</template>
<script setup>
import { ref, onMounted, computed, nextTick } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { getUserById, updateUserAvatar } from "../../../api/userList";
import { getParentById, getChildren } from "../../../api/parent";
import { removeStudent } from "../../../api/student";
import UpdataDrawer from "./drawer/UpdataDrawer.vue";
import Condition from "./drawer/Condition.vue";
import Counselling from "./choose/Counselling.vue";
import CourseInfo from "./choose/CourseInfo.vue";
import ActivityInfo from "./choose/ActivityInfo.vue";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const router = useRouter();
const loading = ref(true);
const userData = ref([]); // 存储用户数据的响应式变量
const parentData = ref([]); // 存储家长数据的响应式变量
const studentData = ref([]); // 存储学生数据的响应式变量
const currentStudentId = ref(null); // 定义响应式变量来存储当前行的 id
// 定义响应式变量来存储当前行的 id，目的为防止id的变化导致相互影响
const counsellingStudentId  = ref(null); 
const currentCourseStudentId  = ref(null); 
const currentActivityStudentId  = ref(null); 
const currentStudentName = ref(null); // 定义响应式变量来存储当前行
//控制抽屉
const userDrawerRef = ref(null);
const conditionRef = ref(null);
const CounsellingRef = ref(null);
const CourseInfoRef = ref(null);
const ActivityInfoRef = ref(null);

const surfaceData = async () => {
  if (user.value.loginUserId) {
    try {
      // 先获取用户数据
      const userResponse = await getUserById(user.value.loginUserId);
      userData.value = userResponse.data.data;

      // 检查是否获取了parentId
      if (userData.value && userData.value.parentId) {
        // 使用parentId获取家长数据 和 学生数据
        const parentResponse = await getParentById(userData.value.parentId);
        const studentResponse = await getChildren(userData.value.parentId);
        parentData.value = parentResponse.data.data;
        studentData.value = studentResponse.data.data;
      } else {
        console.error("parentId未定义或无效");
      }
    } catch (error) {
      console.error("获取数据失败:", error);
    } finally {
      loading.value = false; // 关闭加载状态
    }
  } else {
    console.error("用户ID未定义");
    loading.value = false;
  }
};
// 在组件挂载后调用API
onMounted(async () => {
  surfaceData();
});

//头像
// 上传操作的 URL
const uploadAction = "http://localhost:8301/admin/vod/file/upload";
// 上传成功回调
const handleAvatarSuccess = async (res, file) => {
  if (res.code == 20000) {
    userData.value.avatar = res.data; // 直接更新 userData 的响应式状态
    const parentResponse = await updateUserAvatar(userData.value); //提交到后端添加数据
    if (parentResponse.data.code === 20000) {
      ElMessage.success("上传成功");
    }
  } else {
    ElMessage.error("上传失败 (非0)");
  }
};
// 错误处理
const handleAvatarError = (err, file, fileList) => {
  console.error("上传失败(http失败)", err);
  ElMessage.error("上传失败(http失败)");
};
// 上传校验
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isLt7M = file.size / 1024 / 1024 < 7;

  if (user.value.loginUserId === "") {
    ElMessage.error("请先登录！");
    return false;
  }
  if (!isJPG) {
    ElMessage.error("上传图片只能是 JPG 格式!");
  }
  if (!isLt7M) {
    ElMessage.error("上传图片大小不能超过 7MB!");
  }
  return isJPG && isLt7M;
};

//修改信息打开抽屉
const updataUserInfo = () => {
  userDrawerRef.value.openDrawer();
};
const bindingInfo = () => {
  router.push({ path: "/bindingInfor" });
};

//添加子女
const addChildren = () => {
  router.push({ path: "/StudentForm" });
};
const handleRowClick = (row) => {
  router.push({ path: "/StudentForm/" + row.studentId });
};
//健康
const health = async (studentId, studentName) => {
  currentStudentId.value = studentId;
  currentStudentName.value = studentName;
  await nextTick(); //需要等待Vue完成DOM更新
  conditionRef.value.openDrawer();
};
//作业辅导
const counselling = async (studentId) => {
  counsellingStudentId.value = studentId;
  await nextTick(); //需要等待Vue完成DOM更新
  CounsellingRef.value.showDialog();
};
//选课
const courseInfo = async (studentId) => {
  currentCourseStudentId.value = studentId;
  await nextTick(); //需要等待Vue完成DOM更新
  CourseInfoRef.value.showDialog();
};
//活动
const activityInfo = async (studentId) => {
  currentActivityStudentId.value = studentId;
  await nextTick(); //需要等待Vue完成DOM更新
  ActivityInfoRef.value.showDialog();
};
//删除学生
const removeStudents = async (studentId) => {
  const removeResponse = await removeStudent(studentId);
  if (removeResponse.data.code === 20000) {
    ElMessage.success("删除成功");
    // 更新 studentData 数组
    //.filter 方法创建一个新数组，如把所有id不等于1的学生创建新数组赋值
    studentData.value = studentData.value.filter(
      (student) => student.studentId !== studentId
    );
  }
};
</script>

<style scoped>
.user-profile {
  font-family: Arial, sans-serif;
  max-width: 1111px;
  margin: 0 auto;
  background: #fff;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.user-name-container {
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: space-between; /* 将子元素分散对齐到两端 */
  align-items: center; /* 垂直居中对齐 */
}
.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}
.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 15px;
  margin-top: -150px;
}
.avatarNull {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 15px;
  margin-top: -230px;
  background-color: #f0f0f0;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #5dcff2;
}
.studentAvatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 15px;
}
.user-stats-photo {
  width: 100px;
  height: 100px;
  margin: 10px 0;
}
.photo {
  width: 100px;
  height: 100px;
}
.photo-placeholder {
  width: 100px;
  height: 100px;
  background-color: #fdfafa3b;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #d3d3d3;
}
.user-details {
  flex-grow: 1;
  margin-left: 20px;
}
.user-name {
  display: block;
  font-size: 1.2em;
  font-weight: bold;
}
.user-stats {
  margin: 10px 0;
}
.user-stat {
  margin-right: 10px;
}
.user-actions {
  text-align: center;
  margin-bottom: 20px;
}
.action-button {
  background: #00a1d6;
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin: 0 5px;
  border-radius: 20px;
  cursor: pointer;
}
.user-tasks {
  border-top: 1px solid #e0e0e0;
  padding-top: 20px;
}
.xuankehd {
  margin: 0px;
}
</style>
