<template>
  <el-dialog
    v-model="dialogVisible"
    title="选择活动信息"
    width="900"
    :before-close="handleClose"
  >
    <el-table :data="activityStudentData" style="width: 100%">
      <el-table-column label="图片" width="200" align="center">
        <template v-slot="scope">
          <img
            :src="scope.row.activityCover"
            alt="图片"
            class="studentAvatar"
          />
        </template>
      </el-table-column>
      <el-table-column prop="activityName" label="活动名称" width="120" />
      <el-table-column prop="classifyName" label="活动类别" width="120" />
      <el-table-column prop="activityFee" label="活动费用" width="120">
        <template v-slot="scope">
          <el-tag v-if="scope.row.activityFee == 0" type="success" size="mini"
            >免费</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="organizer" label="主办方" width="120" />
      <el-table-column prop="teacherName" label="活动教师" width="120" />
      <el-table-column label="教师头衔" width="120">
        <template v-slot="scope">
          <el-tag v-if="scope.row.level === 1" type="success" size="mini"
            >高级教师</el-tag
          >
          <el-tag v-if="scope.row.level === 0" size="mini">普通教师</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="报名开始时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.registrationTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="报名结束时间" width="160">
        <template v-slot="scope">
          {{ formatDateTime(scope.row.expirationTime) }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template v-slot="scope">
          <el-button
            link
            type="primary"
            size="small"
            @click="handleClick(scope.row.id)"
            >取消选择</el-button
          >
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
import { ElMessageBox, ElMessage } from "element-plus";
import {
  activityStudentAll,
  removeActivityStudent,
} from "../../../../api/activity";

const activityStudentData = ref([]); // 选择活动
const dialogVisible = ref(false);
// 时间格式
const formatDateTime = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split("T")[0]; // 将日期格式化为 YYYY-MM-DD
};

const showDialog = async () => {
  const response = await activityStudentAll(props.studentId);
  if (response.data.code === 20000) {
    activityStudentData.value = response.data.data.studentActivityIntelDTOList;
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

const handleClick = async (id) => {
  const response = await removeActivityStudent(id, props.studentId);
  if (response.data.code === 20000) {
    ElMessage.success("取消成功！");
    showDialog();
  } else {
    const errorMessage = response.data.message || "取消失败";
    ElMessage.error(`取消失败(ノへ￣、): ${errorMessage}`);
    return;
  }
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
.studentAvatar {
  width: 125px;
  height: 100px;
}
</style>
