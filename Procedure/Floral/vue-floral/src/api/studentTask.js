import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';

//查询学生的选课
export const studentTaskAll = (studentId) => {
  return apiClient.get(`${api_name}/studentTask/${studentId}`);
};
