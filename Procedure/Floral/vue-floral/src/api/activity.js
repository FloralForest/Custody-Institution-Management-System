import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';
export const activityFindAll = () => {
  return apiClient.get(`${api_name}/activity/findAll`);
};
//id查询活动
export const activityGetId = (activityId) => {
  return apiClient.get(`${api_name}/activity/activityArray/${activityId}`);
};
//课程分类
export const getActivityClassify = (id) => {
  return apiClient.get(`${api_name}/activity/activityClassify/${id}`);
};
//选活动
export const addSelectActivity = (studentId, activityId) => {
  return apiClient.post(`${api_name}/students/${studentId}/activity/${activityId}`);
};
//查询学生的选活动
export const activityStudentAll = (studentId) => {
  return apiClient.get(`${api_name}/activityChartStudent/${studentId}`);
};
//取消选活动
export const removeActivityStudent = (activityId, studentId) => {
  return apiClient.delete(`${api_name}/remove/activity/${activityId}/student/${studentId}`);
};