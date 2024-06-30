<template>
  <el-form
    :model="formStudent"
    label-width="auto"
    style="max-width: 600px; margin: 0 auto"
  >
    <el-form-item
      label="学生姓名"
      prop="studentName"
      :rules="[{ required: true, message: '请填写姓名' }]"
    >
      <el-input v-model="formStudent.studentName" />
    </el-form-item>
    <el-form-item
      label="学生性别"
      prop="gender"
      :rules="[{ required: true, message: '请填写性别' }]"
    >
      <el-select v-model="formStudent.gender" placeholder="学生性别">
        <el-option label="男" value="男" />
        <el-option label="女" value="女" />
      </el-select>
    </el-form-item>
    <el-form-item
      label="出生日期"
      prop="birthday"
      :rules="[{ required: true, message: '请填写日期' }]"
    >
      <el-input v-model="formStudent.birthday" placeholder="年-月" />
    </el-form-item>
    <el-form-item
      label="学生年龄"
      prop="age"
      :rules="[
        { required: true, message: '请填写年龄' },
        { type: 'number', message: '年龄必须为数字' },
      ]"
    >
      <el-input
        v-model.number="formStudent.age"
        type="text"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item
      label="与你关系"
      prop="relationship"
      :rules="[{ required: true, message: '请选择关系' }]"
    >
      <el-select v-model="formStudent.relationship" placeholder="与监护人关系">
        <el-option label="父子" value="父子" />
        <el-option label="父女" value="父女" />
        <el-option label="母子" value="母子" />
        <el-option label="母女" value="母女" />
        <el-option label="其他" value="其他" />
      </el-select>
    </el-form-item>
    <el-form-item
      label="家庭地址"
      prop="address"
      :rules="[{ required: true, message: '请填写地址' }]"
    >
      <el-input v-model="formStudent.address" />
    </el-form-item>
    <el-form-item
      label="联系电话"
      prop="phoneNumber"
      :rules="[{ required: true, message: '请填写电话' }]"
    >
      <el-input v-model="formStudent.phoneNumber" />
    </el-form-item>
    <el-form-item
      label="登记时间"
      prop="enrollmentDate"
      :rules="[{ required: true, message: '请填写时间' }]"
    >
      <el-date-picker
        v-model="formStudent.enrollmentDate"
        type="date"
        placeholder="报名时间"
        style="width: 100%"
      />
    </el-form-item>
    <el-form-item label="学生图片" :label-width="formLabelWidth">
      <el-upload
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :on-error="handleAvatarError"
        :action="uploadAction"
        class="avatar-uploader"
      >
        <img
          v-if="formStudent.photo"
          :src="formStudent.photo"
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
import { useRoute, useRouter } from "vue-router";
import { getUserById } from "../../../../api/userList";
import { getParentById } from "../../../../api/parent";
import {
  saveStudent,
  getStudentById,
  updateParent,
} from "../../../../api/student";

const store = useStore();
const user = computed(() => store.state.user);
const router = useRouter();
const route = useRoute();
const studentId = route.params.id; // 获取路由参数id
const loading = ref(true);
const userData = ref({}); // 存储用户数据的响应式变量
const parentData = ref({}); // 存储家长数据的响应式变量
const formStudent = ref({});
// 在组件挂载后调用API
onMounted(async () => {
  if (studentId !== "") {
    const studentResponse = await getStudentById(studentId);
    formStudent.value = studentResponse.data.data.student;
    loading.value = false; // 关闭加载状态
  } else {
    if (user.value.loginUserId) {
      try {
        // 先获取用户数据
        const userResponse = await getUserById(user.value.loginUserId);
        userData.value = userResponse.data.data;

        // 检查是否获取了parentId
        if (userData.value && userData.value.parentId) {
          // 使用parentId获取家长数据 和 学生数据
          const parentResponse = await getParentById(userData.value.parentId);
          parentData.value = parentResponse.data.data;
        } else {
          console.error("parentId未定义或无效");
        }
        formStudent.value.address = parentData.value.address;
        formStudent.value.phoneNumber = userData.value.phone;
      } catch (error) {
        console.error("获取数据失败:", error);
      } finally {
        loading.value = false; // 关闭加载状态
      }
    } else {
      console.error("用户ID未定义");
      loading.value = false;
    }
  }
});

// 上传操作的 URL
const uploadAction = "http://localhost:8301/admin/vod/file/upload";
// 上传成功回调
const handleAvatarSuccess = async (res, file) => {
  if (res.code == 20000) {
    formStudent.value.photo = res.data; // 直接更新 parentData 的响应式状态
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
 // 检查 formStudent 对象中除了 photo 以外的属性是否有空值
 const requiredFields = {
    studentName: '学生姓名',
    gender: '学生性别',
    birthday: '出生日期',
    age: '学生年龄',
    relationship: '与监护人关系',
    address: '家庭地址',
    phoneNumber: '联系电话',
    enrollmentDate: '登记时间'
  };
const emptyFieldKey = Object.keys(requiredFields).find(key => !formStudent.value[key]);
  // 表单不完整，显示错误消息并返回
  if (emptyFieldKey) {
    ElMessage.error(`请填写:${requiredFields[emptyFieldKey]}`);
    return;
  }
  if (studentId !== "") {
    const studentResponse = await updateParent(formStudent.value); //提交到后端添加数据
    if (studentResponse.data.code === 20000) {
      ElMessage.success("修改成功");
      router.push({ path: "/information" });
    } else {
      const errorMessage = studentResponse.data.message || "修改失败";
      ElMessage.error(`修改失败: ${errorMessage}`);
      return;
    }
  } else {
    const studentResponse = await saveStudent(
      parentData.value.id,
      formStudent.value
    ); //提交到后端添加数据
    if (studentResponse.data.code === 20000) {
      ElMessage.success("添加成功");
      router.push({ path: "/information" });
    } else {
      const errorMessage = studentResponse.data.message || "添加失败";
      ElMessage.error(`添加失败: ${errorMessage}`);
      return;
    }
  }
};
const cancel = () => {
  router.push({ path: "/information" });
};
</script>
