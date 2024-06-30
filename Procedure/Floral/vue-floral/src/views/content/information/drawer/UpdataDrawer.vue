<template>
  <el-drawer
    v-model="dialog"
    title="修改用户信息"
    :before-close="handleClose"
    direction="rtl"
    class="demo-drawer"
  >
    <div class="demo-drawer__content">
      <el-form :model="userData">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="userData.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input
            v-model="userData.password"
            type="password"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select v-model="userData.sex" placeholder="下拉框">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="userData.phone" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div class="textSpan" v-if="userData.parentId !== null">
        <span>绑定信息(家长)</span>
      </div><br/>
      <el-form :model="parentData" v-if="userData.parentId !== null">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="parentData.parentName" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="parentData.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="parentData.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="parentData.remark" autocomplete="off" />
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
              v-if="parentData.parentAvatar"
              :src="parentData.parentAvatar"
              alt="图片"
              class="photo"
            />
            <div v-else class="photo-placeholder">图片</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div class="demo-drawer__footer">
        <el-button @click="cancelForm">关闭</el-button>
        <el-button type="primary" :loading="loading" @click="onClick">{{
          loading ? "修改中 ..." : "修改"
        }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script setup>
import { reactive, ref, onMounted, computed } from "vue";
import { ElDrawer, ElMessageBox } from "element-plus";
import { useStore } from "vuex";
import { getUserById, updateUser } from "../../../../api/userList";
import { getParentById, updateParent } from "../../../../api/parent";

const store = useStore();
const user = computed(() => store.state.user);
const userData = ref({}); // 存储用户数据的响应式变量
const parentData = ref({}); // 存储家长数据的响应式变量
// 在组件挂载后调用API
onMounted(async () => {
  if (user.value.loginUserId) {
    try {
      // 先获取用户数据
      const userResponse = await getUserById(user.value.loginUserId);
      userData.value = userResponse.data.data;
      loading.value = false; // 关闭加载状态
      // 检查是否获取了parentId
      if (userData.value && userData.value.parentId) {
        // 使用parentId获取家长数据 和 学生数据
        const parentResponse = await getParentById(userData.value.parentId);
        parentData.value = parentResponse.data.data;
        loading.value = false; // 关闭加载状态
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
});

const formLabelWidth = "80px";
let timer;
const dialog = ref(false);
const openDrawer = () => {
  dialog.value = true;
};
const loading = ref(false);

// 上传操作的 URL
const uploadAction = "http://localhost:8301/admin/vod/file/upload";
// 上传成功回调
const handleAvatarSuccess = async (res, file) => {
  if (res.code == 20000) {
    parentData.value.parentAvatar = res.data; // 直接更新 parentData 的响应式状态
    const parentResponse = await updateParent(parentData.value); //提交到后端添加数据
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

const onClick = async () => {
  const userResponse = await updateUser(userData.value); //提交到后端添加数据
  if (userResponse.data.code === 20000) {
    ElMessage.success("账号~~");
  } else {
    const errorMessage = userResponse.data.message || "账号--";
    ElMessage.error(`账号--: ${errorMessage}`);
    return;
  }
  let parentResponse;
  if (userData.value.parentId) {
    //有家长id提交
    parentResponse = await updateParent(parentData.value); //提交到后端添加数据
    if (
      userResponse.data.code === 20000 &&
      parentResponse.data.code === 20000
    ) {
      ElMessage.success("信息修改成功");
    } else {
      const errorMessage =
        userResponse.data.message || parentResponse.data.message || "信息修改失败";
      ElMessage.error(`信息修改失败: ${errorMessage}`);
      return;
    }
  }
  dialog.value = false;
};

const handleClose = (done) => {
  if (loading.value) {
    return;
  }
  ElMessageBox.confirm("需要提交修改吗？")
    .then(async () => {
      loading.value = true;
      const userResponse = await updateUser(userData.value); //提交到后端添加数据
      if (userResponse.data.code === 20000) {
        timer = setTimeout(() => {
          done();
          // 动画关闭需要一定的时间
          setTimeout(() => {
            loading.value = false;
          }, 400);
          ElMessage.success("账号~~");
        }, 1000);
      } else {
        const errorMessage = userResponse.data.message || "账号--";
        ElMessage.error(`账号--: ${errorMessage}`);
        loading.value = false;
        return;
      }
      let parentResponse;
      if (userData.value.parentId) {
        //有家长id提交
        parentResponse = await updateParent(parentData.value); //提交到后端添加数据
        if (
          userResponse.data.code === 20000 &&
          parentResponse.data.code === 20000
        ) {
          timer = setTimeout(() => {
            done();
            // 动画关闭需要一定的时间
            setTimeout(() => {
              loading.value = false;
            }, 400);
            ElMessage.success("信息修改成功");
          }, 1000);
        } else {
          const errorMessage =
            userResponse.data.message ||
            parentResponse.data.message ||
            "信息修改失败";
          ElMessage.error(`信息修改失败: ${errorMessage}`);
          loading.value = false;
          return;
        }
      }
    })
    .catch(() => {
      // catch error
    });
};

const cancelForm = () => {
  loading.value = false;
  dialog.value = false;
  clearTimeout(timer);
};
// 暴露方法，以便父组件可以调用
defineExpose({
  openDrawer,
});
</script>
<style>
.textSpan {
  text-align: center;
  margin: auto;
}
.photo {
  width: 130px;
  height: 130px;
}
.photo-placeholder {
  width: 130px;
  height: 130px;
  background-color: #f0f0f0;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #d3d3d3;
}
</style>
