<template>
  <el-form
    :model="formParent"
    label-width="auto"
    style="max-width: 600px; margin: 0 auto"
  >
    <el-form-item
      label="姓名"
      prop="parentName"
      :rules="[{ required: true, message: '请填写姓名' }]"
    >
      <el-input v-model="formParent.parentName" />
    </el-form-item>
    <el-form-item
      label="电子邮箱"
      prop="email"
      :rules="[{ message: '请填写邮箱' }]"
    >
      <el-input v-model="formParent.email" />
    </el-form-item>
    <el-form-item
      label="地址"
      prop="address"
      :rules="[{ required: true, message: '请填写地址' }]"
    >
      <el-input v-model="formParent.address" />
    </el-form-item>

    <el-form-item label="备注" prop="remark" :rules="[{ message: '备注' }]">
      <el-input v-model="formParent.remark" />
    </el-form-item>

    <el-form-item label="图片" :label-width="formLabelWidth">
      <el-upload
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :on-error="handleAvatarError"
        :action="uploadAction"
        class="avatar-uploader"
      >
        <img
          v-if="formParent.parentAvatar"
          :src="formParent.parentAvatar"
          alt="图片"
          class="photo"
        />
        <div v-else class="photo-placeholder">图片</div>
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button @click="cancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { ElDrawer, ElMessage } from "element-plus";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { bindParent } from "../../../../api/userList";

const store = useStore();
const user = computed(() => store.state.user);
const router = useRouter();
const formParent = ref({});

// 上传操作的 URL
const uploadAction = "http://localhost:8301/admin/vod/file/upload";
// 上传成功回调
const handleAvatarSuccess = async (res, file) => {
  if (res.code == 20000) {
    formParent.value.parentAvatar = res.data; // 直接更新 parentData 的响应式状态
    ElMessage.success("上传成功");
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

const onSubmit = async () => {
  const requiredFields = {
    parentName: "姓名",
    address: "地址",
  };
  const emptyFieldKey = Object.keys(requiredFields).find(
    (key) => !formParent.value[key]
  );
  // 表单不完整，显示错误消息并返回
  if (emptyFieldKey) {
    ElMessage.error(`请填写:${requiredFields[emptyFieldKey]}`);
    return;
  }
  if (user.value.loginUserId !== null && user.value.loginUserId !== '') {
    const formParentResponse = await bindParent(
      user.value.loginUserId,
      formParent.value
    ); //提交到后端添加数据
    if (formParentResponse.data.code === 20000) {
      ElMessage.success("绑定成功");
      router.push({ path: "/information" });
    } else {
      const errorMessage = formParentResponse.data.message || "绑定失败";
      ElMessage.error(`绑定失败: ${errorMessage}`);
      return;
    }
  }else{
    ElMessage.error("未登录，请先登录")
    router.push({ path: "/login"})
  }
};
const cancel = () => {
  router.push({ path: "/information" });
};
</script>
