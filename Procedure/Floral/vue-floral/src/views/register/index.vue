<template>
  <div class="dark-theme-container">
    <div class="form-container">
      <h1>用户注册</h1>
      <form @submit.prevent="handleRegister">
        <div class="input-group">
          <label for="username">输入用户名</label>
          <input type="text" id="username" v-model="formData.username" />
        </div>
        <div class="input-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="formData.password" />
        </div>
        <div class="input-group">
          <label for="confirmPassword">确认密码</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="formData.confirmPassword"
          />
        </div>
        <div class="input-group">
          <label for="phone">手机号</label>
          <input type="phone" id="phone" v-model="formData.phone" />
        </div>
        <div class="rules">
          <p>安全声明和规则:</p>
          <ul>
            <li>请妥善保管账号信息，防止他人盗用</li>
            <li>切勿将账号借予他人使用</li>
            <li>将账号借予他人使用出现问题者，机构概不负责</li>
          </ul>
        </div>
        <button type="submit">立即加入</button>
      </form>
      <p class="captcha-note">
        本网站受Floral and Forest保护，且严格遵循
        <a href="https://www.baidu.com" target="_blank">隐私政策</a>
        和
        <a
          href="https://baike.baidu.com/item/%E4%BF%A1%E6%81%AF%E6%9C%8D%E5%8A%A1%E6%9D%A1%E6%AC%BE/53292296"
          target="_blank"
          >服务条款</a
        >
        创建账号将默认您同意。
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { register } from "../../api/user"; // 引入API函数

const router = useRouter();
const formData = ref({
  username: "",
  password: "",
  phone: "",
  role: 2, //前台注册角色始终为家长
  confirmPassword: "",
});
// 验证手机号的函数
const validatePhone = (phone) => {
  const phoneRegex = /^(13|15|16|17|18|19)\d{9}$/;
  return phoneRegex.test(phone);
};

// 验证表单的函数
const validateForm = () => {
  if (!formData.value.username || !formData.value.password) {
    ElMessage.error("请输入用户名和密码");
    return false;
  }
  if (formData.value.password.length < 6) {
    ElMessage.error("密码不能小于6位");
    return false;
  }
  if (formData.value.password !== formData.value.confirmPassword) {
    ElMessage.error("两次输入的密码不一致");
    return false;
  }
  if (!validatePhone(formData.value.phone)) {
    ElMessage.error("请输入正确的手机号");
    return false;
  }
  return true;
};

const handleRegister = async () => {
  if (!validateForm()) {
    return;
  }
  try {
    // 发送注册请求
    const { success, message } = await register(formData.value);
    if (success) {
      ElMessage.success("注册成功");
      router.push({ path: "/login" });
    } else {
      // 失败，显示返回的错误消息
      ElMessage.error(message);
    }
  } catch (error) {
    // 显示通用错误信息
    ElMessage.error(`其他错误: ${error.message}`);
  }
  // 清空表单
  // formData.value.username = "";
  // formData.value.phone = "";
  // formData.value.password = "";
  // formData.value.confirmPassword = "";
};
</script>

<style scoped>
.dark-theme-container {
  background-image: url("https://ggkt-1304728012.cos.ap-nanjing.myqcloud.com/2024/04/tu/7270403.gif");
  background-size: cover;
  color: white;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.form-container {
  background: rgba(0, 0, 0, 0.8);
  padding: 20px;
  border-radius: 8px;
  width: 100%;
  max-width: 400px;
  margin: auto;
}

h1 {
  margin-bottom: 1rem;
}

.input-group {
  margin-bottom: 1rem;
  width: 95%;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="text"],
input[type="password"],
input[type="phone"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #555;
  border-radius: 4px;
  background: #333;
  color: white;
}

.rules ul {
  list-style: inside;
}

button {
  background-color: #e53e3e;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.captcha-note {
  font-size: 0.8rem;
  margin-top: 1rem;
  opacity: 0.7;
}
.captcha-note a{
  color: #f23f3ffd;
}
</style>
