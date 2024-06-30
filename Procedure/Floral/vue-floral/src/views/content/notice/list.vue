<template>
  <div class="news-list">
    <div class="news-item" v-for="notice in notice" :key="notice.id">
      <div class="news-content">
        <h3
          class="news-title knowledge-link"
          @click="() => noticeInfo(notice.id)"
        >
          {{ notice.title }}
        </h3>
        <p class="news-date">
          [
          <el-text v-if="notice.priority === 1" type="danger" size="small"
            >紧急</el-text
          >
          <el-text v-if="notice.priority === 2" type="warning" size="small"
            >重要</el-text
          >
          <el-text v-if="notice.priority === 3" type="primary" size="small"
            >普通</el-text
          >
          ] 查看：
          <el-text v-if="notice.group === 1" type="danger" size="small"
            >教师</el-text
          >
          <el-text v-if="notice.group === 2" type="warning" size="small"
            >家长</el-text
          >
          <el-text v-if="notice.group === 3" type="primary" size="success"
            >全部</el-text
          >
          —发布人：{{ notice.param.usersName }}
          <span v-if="notice.param.teacherId === 0">(管理员)</span>
          <span
            v-else-if="
              notice.param.teacherId === null || notice.param.teacherId === ''
            "
            >(用户无绑定)</span
          >
          <span v-else>({{ notice.param.teacherName }})</span>
        </p>
        <p class="news-date" v-if="!notice.attachments">附件：无</p>
        <p class="news-date" v-else>
          附件：<a
            :href="notice.attachments"
            target="_blank"
            @click.stop=""
            class="news-date-a"
            >点击查看文件</a
          >
        </p>
        <p class="news-date">
          发布时间：{{ formatDate(notice.createTime) }} &gt;更新时间：{{
            formatDate(notice.updateTime)
          }}
        </p>
      </div>
    </div>
  </div>
  <Details ref="DetailsRef" :noticeId="noticeId" />
</template>

<script setup>
import { ref, onMounted, nextTick, computed } from "vue";
import { useStore } from "vuex";
import moment from "moment";
import { useRouter } from "vue-router";
import { noticeFindAll, noticeList, getUnread } from "../../../api/notice"; // 引入API函数
import Details from "./details/details.vue"; //详细信息组件

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const router = useRouter();
const loading = ref(true);
const notice = ref([]); // 存储课程数据的响应式变量
const searchObj = ref({}); // 查询条件
const DetailsRef = ref(null);

// 格式化日期
function formatDate(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}

// 在组件挂载后调用API
onMounted(async () => {
  try {
    if (user.value) {
      searchObj.value.role = user.value.loginRoles;
      const response = await noticeList(1, -1, searchObj.value);
      // 赋值之前对响应数据进行排序 并 保留数组的原始顺序
      const sortedNotices = [...response.data.data.records].sort(
        (a, b) => a.priority - b.priority
      );
      notice.value = sortedNotices;
      const unreadResponse = await getUnread(
        user.value.loginUserId,
        user.value.loginRoles
      );
      user.value.loginUnread = unreadResponse.data; //更新公告计数
      //更新公告计数
      store.dispatch("updateUnread", user.value.loginUnread);
      loading.value = false; // 数据加载完毕，关闭加载状态
    }
  } catch (error) {
    console.error("获取列表失败:", error);
    loading.value = false; // 请求失败也应关闭加载状态
  }
});

//查看公告信息
const noticeInfo = async (noticeId) => {
  DetailsRef.value.showDialog(noticeId, user.value.loginUserId);
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
  background-color: rgba(254, 254, 254, 0.3);
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
  cursor: pointer; /* 鼠标光标改变 */
  transition: color 0.3s; /* 添加颜色变化的过渡效果 */
}

/* 当鼠标悬停在链接上时的样式 */
.knowledge-link:hover {
  color: #3498db; /* 悬停颜色*/
}

.news-date {
  font-size: 14px;
  color: #666;
  margin: 0 0 10px 0;
}
.news-date-a {
  text-decoration: none; /* 去除下划线 */
  color: #6ad1ee; /* 悬停颜色*/
}
.news-description {
  font-size: 14px;
  color: #333;
}
</style>
