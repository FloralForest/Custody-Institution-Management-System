<template>
  <el-drawer v-model="drawer" title="I am the title" :with-header="false">
    <span>详细信息</span>
    <p />
    <el-form
      :label-position="labelPosition"
      label-width="auto"
      :model="healthData"
      style="max-width: 600px"
    >
      <el-form-item label="姓名">
        <el-input v-model="healthData.studentName" />
      </el-form-item>
      <el-form-item label="最近一次体检时间">
        <el-date-picker
          v-model="healthData.examinationDate"
          type="date"
          placeholder="选填"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="健康情况">
        <el-input
          v-model="healthData.condition"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="病例情况">
        <el-input
          v-model="healthData.medicalRecord"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="过敏情况">
        <el-input
          v-model="healthData.allergy"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="心理情况">
        <el-input
          v-model="healthData.mentalHealth"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="饮食习惯">
        <el-input
          v-model="healthData.eatingHabits"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="睡眠习惯">
        <el-input
          v-model="healthData.sleepCondition"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item label="其他事项">
        <el-input
          v-model="healthData.other"
          type="textarea"
          placeholder="详细填写或者“无”"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script lang="ts" setup>
import { ref, watch, defineProps, onMounted } from "vue";
import { FormProps, ElMessage } from "element-plus";
import {
  saveHealth,
  getStudentHealth,
  updateHealth,
} from "../../../../api/student";

const props = defineProps({
  studentId: {
    type: Number,
    default: null,
  },
  studentName: null,
});
const drawer = ref(false);
const healthData = ref({
  id: null,
  studentId: props.studentId,
  studentName: props.studentName,
  examinationDate: "",
  condition: "",
  medicalRecord: "",
  allergy: "",
  mentalHealth: "",
  eatingHabits: "",
  sleepCondition: "",
  other: "",
}); //健康信息
//饿了么TS组件，将文字置于上面
const labelPosition = ref<FormProps["labelPosition"]>("top");
// 监听props.studentId的变化，并在变化时加载健康信息
watch(
  () => props.studentId,
  async (newStudentId) => {
    if (newStudentId) {
      healthData.value.studentId = newStudentId;
      try {
        const response = await getStudentHealth(newStudentId);
        if (response.data.data !== null) {
          healthData.value = response.data.data; // 设置健康信息
        } else {
          ElMessage.warning("帮TA完善信息叭");
        }
      } catch (error) {
        console.error("获取健康数据失败:", error);
      }
    }
  }
);
watch(
  () => props.studentName,
  async (newstudentName) => {
    if (newstudentName) {
      healthData.value.studentName = newstudentName; // 设置健康信息
    } else {
      console.error("学生姓名丢失");
    }
  }
);

const openDrawer = async () => {
  resetHealthData();
  drawer.value = true;
  const response = await getStudentHealth(props.studentId);
  if (response.data.data !== null) {
    healthData.value = response.data.data; // 设置健康信息
  } else {
    console.error("没有健康数据");
  }
};
const onSubmit = async () => {
  const response = await getStudentHealth(healthData.value.studentId);
  if (response.data.data === null) {
    const saveHealthResponse = await saveHealth(healthData.value); //提交到后端添加数据
    if (saveHealthResponse.data.code === 20000) {
      ElMessage.success("添加成功");
      drawer.value = false;
      resetHealthData();
    } else {
      const errorMessage = saveHealthResponse.data.message || "添加失败";
      ElMessage.error(`添加失败: ${errorMessage}`);
      return;
    }
  } else {
    const updateHealthResponse = await updateHealth(healthData.value); //提交到后端添加数据
    if (updateHealthResponse.data.code === 20000) {
      ElMessage.success("修改成功");
      drawer.value = false;
      resetHealthData();
    } else {
      const errorMessage = updateHealthResponse.data.message || "修改失败";
      ElMessage.error(`修改失败: ${errorMessage}`);
      return;
    }
  }
};
const cancel = () => {
  drawer.value = false;
  resetHealthData();
};
const resetHealthData = () => {
  // 将 healthData 重置为初始状态或空值
  healthData.value = {
    studentId: props.studentId,
    studentName: props.studentName,
    // 其他所有属性设置为空或默认值
    id: null,
    examinationDate: null,
    condition: "",
    medicalRecord: "",
    allergy: "",
    mentalHealth: "",
    eatingHabits: "",
    sleepCondition: "",
    other: "",
  };
};
// 暴露方法，以便父组件可以调用
defineExpose({
  openDrawer,
});
</script>
