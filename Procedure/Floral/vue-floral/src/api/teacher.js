import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';

//查询全部
export const teacherList = () => {
  return apiClient.get(`${api_name}/teacher/findAll`);
};
//id查询
export const getTeacherById = (id) => {
  return apiClient.get(`${api_name}/teacher/getTeacher/${id}`);
};
//评价查询
export const teacherEvaluateAll = (id) => {
  return apiClient.get(`${api_name}/activityTeacherEvaluate/${id}`);
};
//添加
export const saveEvaluate = (addEvaluate) => {
  return apiClient.post(`${api_name}/teacher/saveEvaluate`, addEvaluate);
};
//修改
export const getEvaluate = (id) => {
  return apiClient.get(`${api_name}/teacher/getEvaluate/${id}`);
};
export const updataEvaluate = (addEvaluate) => {
  return apiClient.post(`${api_name}/teacher/updateEvaluate`, addEvaluate);
};