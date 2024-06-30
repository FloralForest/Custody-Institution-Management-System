import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';

//查询全部
export const courseFindAll = () => {
  return apiClient.get(`${api_name}/course/findAll`);
};
//id查询
export const courseArray = (id) => {
  return apiClient.get(`${api_name}/course/courseArray/${id}`);
};
//课程视频
export const courseVideo = (id) => {
  return apiClient.get(`${api_name}/video/getVideo/${id}`);
};
//课程分类
export const getClassify = (id) => {
  return apiClient.get(`${api_name}/classify/courseClassify/${id}`);
};
//查询代课教师
export const courseProxy = (id) => {
  return apiClient.get(`${api_name}/proxy/courseProxy/${id}`);
};
//查询课程评价
export const activityEvaluate = (id) => {
  return apiClient.get(`${api_name}/activityChartEvaluate/${id}`);
};
//添加课程评价
export const saveEvaluate = (addEvaluate) => {
  return apiClient.post(`${api_name}/evaluate/saveEvaluate`, addEvaluate);
};
//修改课程评价
export const getEvaluate = (id) => {
  return apiClient.get(`${api_name}/evaluate/getEvaluate/${id}`);
};
export const updataEvaluate = (addEvaluate) => {
  return apiClient.post(`${api_name}/evaluate/updateEvaluate`, addEvaluate);
};
//选课
export const addSelectCourse = (studentId, courseId) => {
  return apiClient.post(`${api_name}/students/${studentId}/courses/${courseId}`);
};
//查询学生的选课
export const chartStudentAll = (studentId) => {
  return apiClient.get(`${api_name}/chartStudent/${studentId}`);
};
//取消选课
export const removeCourseStudent = (courseId, studentId) => {
  return apiClient.delete(`${api_name}/remove/courses/${courseId}/student/${studentId}`);
};