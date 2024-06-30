<template>
  <el-dialog
    v-model="dialogVisible"
    title="公告信息"
    width="900"
    :before-close="handleClose"
  >
    <div class="announcement-container">
      <h1>{{ noticeData.title }}</h1>
      <p class="announcement-number">
        [
        <el-text v-if="noticeData.priority === 1" type="danger" size="small"
          >紧急</el-text
        >
        <el-text v-if="noticeData.priority === 2" type="warning" size="small"
          >重要</el-text
        >
        <el-text v-if="noticeData.priority === 3" type="primary" size="small"
          >普通</el-text
        >
        ]
        <el-text v-if="noticeData.group === 1" type="danger" size="small"
          >教师</el-text
        >
        <el-text v-if="noticeData.group === 2" type="warning" size="small"
          >家长</el-text
        >
        <el-text v-if="noticeData.group === 3" type="success" size="small"
          >全部</el-text
        >
        <br />
        {{ formatDate(noticeData.createTime) }}_{{ noticeData.userName
        }}<br />{{ noticeData.remarks }}
      </p>
      <p class="content">
        {{ noticeData.content }}
      </p>
      <br />
      <div class="attachment-box">
        <p v-if="!noticeData.attachments">附件：无</p>
        <p v-else>
          附件：<a
            :href="noticeData.attachments"
            target="_blank"
            @click.stop=""
            class="news-date-a"
            >点击查看</a
          >
        </p>
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import moment from "moment";
import { ElMessageBox, ElMessage } from "element-plus";
import { noticeGetById, getUnread } from "../../../../api/notice";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const noticeData = ref([]); // 详细信息数据
const dialogVisible = ref(false); //控制窗口开关

// 格式化日期
function formatDate(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}

const hideDialog = () => {
  dialogVisible.value = false;
};

const showDialog = async (noticeId, loginUserId) => {
  const response = await noticeGetById(noticeId, loginUserId);
  const unreadResponse = await getUnread(
    user.value.loginUserId,
    user.value.loginRoles
  );
  user.value.loginUnread = unreadResponse.data; //更新公告计数
  //更新公告计数
  store.dispatch("updateUnread", user.value.loginUnread);
  if (response.data.code === 20000) {
    noticeData.value = response.data.data.tNotices;
    noticeData.value.userName = response.data.data.userName;
  } else {
    const errorMessage = response.data.message || "内容获取失败";
    ElMessage.error(`内容获取失败(ノへ￣、): ${errorMessage}`);
    return;
  }
  dialogVisible.value = true;
};

const handleClose = (done) => {
  ElMessageBox.confirm("确定关闭?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};

defineExpose({
  showDialog,
  hideDialog,
});
</script>

<style scoped>
.announcement-container {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
}

.announcement-container h1 {
  text-align: center;
  font-size: 24px;
  color: #333;
}

.announcement-number,
p {
  color: #666;
  margin-bottom: 20px;
}
.announcement-number {
  text-align: center;
}

.content {
  text-indent: 2em; /* 首行缩进两个字符 */
  text-align: justify; /* 文本两端对齐 */
}

.attachment-box {
  background-color: #e0f4ff;
  padding: 10px;
  margin-top: 20px;
}

.attachment-box a {
  color: #007bff;
  text-decoration: none;
}
</style>
