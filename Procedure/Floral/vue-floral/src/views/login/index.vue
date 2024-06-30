<template>
  <div class="container">
    <div class="split-image-container">
      <div
        class="split-image-left"
        :class="{
          'move-right': isLeftActive,
          'move-right-large': isLeftActiveBig,
        }"
        :style="imageStyles[0]"
        @click="() => rotateImages('left')"
      ></div>
      <div
        class="split-image-reset"
        :class="{
          'move-right-small': isResetActive,
          'move-right-small-large': isResetActiveBig,
          'split-image-reset-x': isSplitImageResetX,
        }"
        :style="imageStyles[1]"
        @click="() => rotateImages('reset')"
      ></div>
      <div
        class="split-image-right"
        :class="{
          'move-left': isRightActive,
          'split-image-right-x': isSplitImageRightX,
        }"
        :style="imageStyles[2]"
        @click="() => rotateImages('right')"
      ></div>
      <div class="login-container">
        <el-form @submit.prevent="handleLogin">
          <el-form-item>
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <span v-if="loginFormErrors.username">{{
            loginFormErrors.username
          }}</span>
          <el-form-item>
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
            ></el-input>
          </el-form-item>
          <span v-if="loginFormErrors.password">{{
            loginFormErrors.password
          }}</span>
          <el-form-item>
            <el-button class="buttonLogin" type="primary" native-type="submit"
              >登录</el-button
            >
            <el-button type="text" style="margin-left: auto" @click="register()"
              >去注册？</el-button
            >
          </el-form-item>
        </el-form>
      </div>
      <div class="footer">© 2024 花與樹，保留所有权利。</div>
    </div>
  </div>
</template>

<script setup>
import {
  ref,
  reactive,
  watch,
  computed,
  onMounted,
  onBeforeUnmount,
} from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useStore } from "vuex";
import { ElMessage } from "element-plus";
import { userLogin } from "../../api/user"; // 引入API函数

const store = useStore();
const router = useRouter();
const loginForm = reactive({
  username: "",
  password: "",
});
// 表单验证状态
const loginFormErrors = reactive({
  username: null,
  password: null,
});
// 单独的验证函数
const validateUsername = () => {
  loginFormErrors.username = loginForm.username ? null : "用户名不能为空";
};
const validatePassword = () => {
  loginFormErrors.password =
    loginForm.password.length >= 6 ? null : "密码至少6位数";
};

const isLeftActive = ref(false);
const isLeftActiveBig = ref(false); // 左边图片将要移动大距离
const isResetActiveBig = ref(false); // 中间图片将要移动大距离
const isResetActive = ref(false);
const isRightActive = ref(false);
const shouldReset = ref(false); //引用 shouldReset 来追踪是否需要重置图片位置
const isSplitImageResetX = ref(false); // 斜切动画
const isSplitImageRightX = ref(false); // 斜切动画
const activeIndex = ref(0); // 默认激活左边的图片
const imageUrls = [
  // 图片 URLs
  "https://p6.itc.cn/q_70/images01/20210718/0780f9b33a49496599ead64193c4251c.jpeg",
  "https://img0.baidu.com/it/u=1645248723,4075833353&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500",
  "https://img2.baidu.com/it/u=2375881377,1509803487&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
];
const imageStyles = computed(() =>
  imageUrls.map((url, index) => {
    let clipPath;
    // 当图片是激活状态时，显示图片
    if (index === activeIndex.value) {
      clipPath = "polygon(8% 0, 100% 0, 90% 100%, 0 100%)";
    } else {
      // 当图片不是激活状态时，显示斜切的样式
      clipPath = "polygon(30% 0, 50% 0, 40% 100%, 20% 100%)";
    }
    return {
      backgroundImage: `url('${url}')`,
      clipPath: clipPath,
      transition: "all 1s",
    };
  })
);
function rotateImages(direction) {
  // 重置所有状态
  isLeftActive.value = false;
  isResetActive.value = false;
  isRightActive.value = false;
  if (direction === "left") {
    if (shouldReset.value) {
      // 如果需要重置，则重置所有图片位置
      isRightActive.value = true;
      isLeftActive.value = false;
      isLeftActiveBig.value = false;
      shouldReset.value = false;
    } else {
      isResetActiveBig.value = true; // 中间图片将要移动大距离
      isRightActive.value = true; // 右边图片将要移动
    }
    activeIndex.value = 0;
  } else if (direction === "reset") {
    if (shouldReset.value) {
      // 如果需要重置，则重置所有图片位置
      isLeftActive.value = true;
      isResetActive.value = false;
      isLeftActiveBig.value = false;
      shouldReset.value = false;
    } else {
      isLeftActive.value = true; // 左边图片将要移动
    }
    activeIndex.value = 1;
  } else if (direction === "right") {
    isLeftActiveBig.value = true; // 左边图片将要移动大距离
    isResetActive.value = true; // 中间图片将要移动
    shouldReset.value = true;
    activeIndex.value = 2;
  }
}
// 定义一个自动轮换图片的函数
let autoRotateIntervalId;
function startAutoRotate() {
  // 根据 rotateIndex 调用 rotateImages 与不同的参数
  let directions = ["left", "reset", "right"];
  let rotateIndex = 0;
  autoRotateIntervalId = setInterval(() => {
    rotateImages(directions[rotateIndex]);
    // 更新 rotateIndex 以便下次调用不同的方向
    rotateIndex = (rotateIndex + 1) % directions.length;
  }, 5000); // 设置轮换的时间间隔， 4000 毫秒
}
// 在组件挂载后设置初始动画状态
onMounted(() => {
  // 设置初始状态以触发动画效果
  isSplitImageResetX.value = true;
  isSplitImageRightX.value = true;
  isResetActiveBig.value = false;
  isRightActive.value = false;
  //设置一个延迟来移除这些状态，以便后续操作不受影响
  setTimeout(() => {
    isSplitImageResetX.value = false;
    isSplitImageRightX.value = false;
  }, 600);
  setTimeout(() => {
    isResetActiveBig.value = true; // 让中间图片移动
    isRightActive.value = true; // 右边图片将要移动
  }, 1000); // 延迟时间应与 CSS 中的 transition 时间相匹配
  startAutoRotate();
});
//清除定时器，避免内存泄漏
onBeforeUnmount(() => {
  clearInterval(autoRotateIntervalId);
});

// 监视 loginForm 中的字段并在变化时执行验证
watch(() => loginForm.username, validateUsername);
watch(() => loginForm.password, validatePassword);
const handleLogin = async () => {
  if (loginFormErrors.username || loginFormErrors.password) {
    ElMessage.error("请正确输入用户名和密码");
    return;
  } else if (
    loginForm.username === "" ||
    loginForm.password === "" ||
    loginForm.username === null ||
    loginForm.password === null
  ) {
    ElMessage.error("请输入用户名和密码");
    return;
  }
  try {
    const { success, data, message } = await userLogin(loginForm);
    if (success) {
      // 登录成功，显示成功消息并跳转到活动页面
      ElMessage.success("登录成功");
      router.push({ path: "/course" });
      ElNotification({
        type: 'success',
        title: "小贴士",
        message: `有<span style="color: red;">${data.loginUnread}条</span>未读公告，点击头像左边小铃铛查看`,
        // dangerouslyUseHTMLString 属性告诉 Element UI 该消息是一个 HTML 字符串会按照 HTML 来解析消息内容。
        dangerouslyUseHTMLString: true,
        offset: 60,
        duration: 0,
      });
    } else {
      // 登录失败，显示返回的错误消息
      ElMessage.error(message);
    }
  } catch (error) {
    // 显示通用错误信息
    ElMessage.error(`登录错误: ${error.message}`);
  }
};
function register() {
  router.push({ name: "register" });
}
</script>

<style scoped>
.container {
  display: flex;
  align-items: center; /* 垂直居中 */
  min-height: 70vh; /* 至少占满整个视口高度 */
  overflow: hidden;
}

.split-image-container {
  display: flex;
  width: 100%;
  height: 450px;
  overflow: hidden;
}

.split-image,
.split-image-left,
.split-image-reset,
.split-image-right {
  position: relative;
  flex: 1;
  background-size: cover;
  background-position: center;
  margin: 0; /* 移除默认边距 */
}
.split-image-left {
  transform: translateX(155px);
}
.split-image-reset {
  transform: translateX(155px);
}
.split-image-reset-x {
  /* 应用动画效果 */
  animation: slideInReset 0.6s ease-in-out forwards;
}
.split-image-right {
  transform: translateX(-155px);
}
.split-image-right-x {
  /* 应用动画效果 */
  animation: slideInRight 0.6s ease-in-out forwards;
}
/* 点击左边时右边向左移动 */
.move-left {
  transform: translateX(-415px);
}
/* 点击中间时左边向右移动 */
.move-right {
  transform: translateX(310px);
}
/* 点击左边时中间向左移动 */
.move-right-small-large {
  transform: translateX(0px);
}
/* 点击右边时中间向右移动 */
.move-right-small {
  transform: translateX(155px);
}
/* 点击右边时左边向右大距离移动 */
.move-right-large {
  transform: translateX(570px);
}
/* 添加过渡效果 */
.split-image-left,
.split-image-reset,
.split-image-right {
  transition: transform 1s ease;
}

.login-container {
  position: absolute;
  top: 49%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(255, 255, 255, 0.3); /* 半透明白色 */
  padding: 20px;
  border-radius: 10px;
  z-index: 2; /* 确保登录表单在图片之上 */
  width: 350px; /* 增加登录组件的宽度 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /*添加阴影效果 */
  /* 添加动画效果 */
  animation: fadeIn 0.6s ease-in-out;
}

.login-container input,
.login-container button {
  margin-bottom: 10px;
  padding: 10px;
}

/* 使用 ::v-deep 选择器覆盖 Element Plus 组件的样式 */
::v-deep.login-container input {
  color: rgba(239, 195, 65, 0.993);
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.login-container .buttonLogin {
  width: 100%;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

@media (max-width: 768px) {
  .split-image-container {
    flex-direction: column;
    height: auto;
  }

  .split-image {
    height: 200px;
    clip-path: none; /* 移动设备上不使用倾斜分割线 */
  }
}
/* 页脚样式 */
.footer {
  text-align: center;
  padding: 10px;
  position: absolute;
  bottom: 0;
  width: 97%;
  font-size: 0.8em;
}
/* 动画关键帧 */
@keyframes slideInReset {
  from {
    opacity: 0;
    transform: translate(0, 155px) skewX(0);
  }
  to {
    opacity: 1;
    transform: translateX(155px) skewX(0);
  }
}

@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translate(0%, -155px) skewX(0);
  }
  to {
    opacity: 1;
    transform: translateX(-155px) skewX(0);
  }
}
/* 动画关键帧 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translate(-50%, -50%) translateY(80px);
  }
  to {
    opacity: 1;
    transform: translate(-50%, -50%) translateY(0);
  }
}
</style>
