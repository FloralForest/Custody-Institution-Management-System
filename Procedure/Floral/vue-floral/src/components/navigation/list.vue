<template>
  <el-affix :offset="0">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
    >
      <el-menu-item index="0">
        
      </el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="/course">
        <router-link to="/course">课程</router-link>
      </el-menu-item>
      <el-menu-item index="/activity">
        <router-link to="/activity">活动</router-link>
      </el-menu-item>
      <el-menu-item index="/teacher">
        <router-link to="/teacher">教师</router-link>
      </el-menu-item>
      <el-menu-item index="/knowledge">
        <router-link to="/knowledge">一些知识</router-link>
      </el-menu-item>
      <el-menu-item index="/recipe">
        <router-link to="/recipe">食谱信息</router-link>
      </el-menu-item>
      <div class="flex-grow"></div>
      <!-- 这个 div 将占据剩余的空间 -->
      <div class="right-menu">
        <div v-if="!isLoggedIn">
          <!-- 用户未登录时显示登录按钮 -->
          <el-button type="text" class="user-login">
            <a href="/login">登录/注册</a>
          </el-button>
        </div>
        <div v-else>
          <router-link to="/notice">
            <el-badge
              :value="user.loginUnread"
              :max="99"
              class="item custom-badge"
            >
              <el-button type="text">
                <el-icon :size="22">
                  <BellFilled />
                </el-icon>
              </el-button>
            </el-badge>
          </router-link>
          <el-dropdown class="avatar-container" trigger="click">
            <div class="avatar-wrapper">
              <img
                :src="user.avatar + '?imageView2/1/w/80/h/80'"
                class="user-avatar"
              />
              <i class="el-icon-caret-bottom" />
            </div>
            <template #dropdown>
              <el-dropdown-menu class="user-dropdown">
                <el-dropdown-item class="dropdown-link">
                  <router-link to="/course">主页</router-link>
                </el-dropdown-item>
                <el-dropdown-item class="dropdown-link">
                  <router-link to="/information">个人中心</router-link>
                </el-dropdown-item>
                <el-dropdown-item class="dropdown-link">
                  <a target="_blank" href="https://www.baidu.com/">百度一下</a>
                </el-dropdown-item>
                <el-dropdown-item divided @click="logout">
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-menu>
  </el-affix>
</template>

<script lang="ts" setup>
import { ref, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
// 引入所需的图标
import { BellFilled } from "@element-plus/icons-vue";

// Vuex登录状态
const store = useStore();
const user = computed(() => store.state.user);
const isLoggedIn = computed(() => store.getters.isLoggedIn);

const route = useRoute();
const router = useRouter();
const activeIndex = ref(route.path);
// 监听路由变化来更新 activeIndex
watch(
  () => route.path,
  (newPath) => {
    activeIndex.value = newPath;
  }
);
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const logout = () => {
  router.push({ path: "/course" });
  store.commit("clearUser"); // 使用 mutation 清除用户信息
  localStorage.removeItem("vuex"); // 清除 localStorage 中的 Vuex 状态
  //清除所有的 localStorage 数据：
  localStorage.clear();
};
</script>

<style>
/* 去除下划线 */
.el-menu-demo .el-menu-item a {
  text-decoration: none;
}
/* 修正 flex-grow 产生的影响 */
.flex-grow {
  flex: 1;
}
.flex-grow {
  flex-grow: 1;
}
.right-menu {
  float: right;
  height: 100%;
  line-height: 50px;
}

.right-menu:focus {
  outline: none;
}

.right-menu .right-menu-item {
  display: inline-block;
  padding: 0 8px;
  height: 100%;
  font-size: 18px;
  vertical-align: text-bottom;
}

.right-menu .right-menu-item.hover-effect {
  cursor: pointer;
  transition: background 0.3s;
}

.right-menu .right-menu-item.hover-effect:hover {
  background: rgba(0, 0, 0, 0.025);
}
.avatar-container {
  margin-top: 5px;
}
.right-menu .avatar-container {
  margin-right: 30px;
}

.item {
  margin: 5px 45px 0 0;
}
.custom-badge .el-badge__content {
  top: auto; /* 取消默认的顶部位置 */
  right: auto; /* 取消默认的右部位置 */
  bottom: 80%; /* 将徽章移动到元素的底部 */
  left: 35%; /* 将徽章移动到元素的右侧 */
  transform: translateY(50%); /* 通过变换下移徽章的一半高度 */
}
.right-menu .avatar-container .avatar-wrapper {
  margin: 5px -15px;
  position: relative;
}

.right-menu .avatar-container .avatar-wrapper .user-avatar {
  cursor: pointer;
  width: 40px;
  height: 40px;
  border-radius: 10px;
}

.right-menu .avatar-container .avatar-wrapper .el-icon-caret-bottom {
  cursor: pointer;
  position: absolute;
  right: -20px;
  top: 25px;
  font-size: 12px;
}

.dropdown-link a {
  text-decoration: none; /* 移除下划线 */
  color: black; /* 字体颜色 */
}

/* 确保 <router-link> 也没有下划线 */
.dropdown-link a.router-link-active,
.dropdown-link a.router-link {
  text-decoration: none; /* 移除下划线 */
  color: black; /* 字体颜色 */
}
.user-login {
  margin-top: 8px;
  margin-right: 10px;
}
.user-login a {
  text-decoration: none; /* 移除下划线 */
  color: black; /* 字体颜色 */
}
</style>
