<template>
  <el-dialog
    v-model="dialogVisible"
    title="作业辅导信息"
    width="900"
    :before-close="handleClose"
  >
    <el-table :data="taskData" style="width: 100%">
      <el-table-column prop="curriculum" label="课目名称" width="120" />
      <el-table-column prop="state" label="状态" width="120" />
      <el-table-column prop="teacherName" label="辅导教师" width="120" />
      <el-table-column label="教师头衔" width="120">
        <template v-slot="scope">
          <el-tag v-if="scope.row.level === 1" type="success" size="mini"
            >高级教师</el-tag
          >
          <el-tag v-if="scope.row.level === 0" size="mini">普通教师</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="taskContent" label="作业内容" width="120" />
      <el-table-column prop="counsellingContent" label="辅导内容" width="120" />
      <el-table-column prop="startTime" label="开始时间" width="160">
        <template v-slot="scope">
          {{ momentDateTime(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="完成时间" width="160">
        <template v-slot="scope">
          {{ momentDateTime(scope.row.endTime) }}
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import moment from "moment";
import { ElMessageBox, ElMessage } from "element-plus";
import { studentTaskAll } from "../../../../api/studentTask";

const taskData = ref([]); //辅导信息
const dialogVisible = ref(false);
// 时间格式
const formatDateTime = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split("T")[0]; // 将日期格式化为 YYYY-MM-DD
};
// 时间格式化函数
function momentDateTime(dateTime) {
  return moment(dateTime).format("YYYY年MM月DD HH:mm");
}

const showDialog = async () => {
  const response = await studentTaskAll(props.studentId);
  if (response.data.code === 20000) {
    taskData.value = response.data.data.studentTaskList;
  } else {
    const errorMessage = response.data.message || "内容获取失败";
    ElMessage.error(`内容获取失败(ノへ￣、): ${errorMessage}`);
    return;
  }
  dialogVisible.value = true;
};
const hideDialog = () => {
  dialogVisible.value = false;
};

const props = defineProps({
  studentId: {
    type: Number,
    default: null,
  },
});

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
.studentAvatar {
  width: 125px;
  height: 100px;
}
</style>
