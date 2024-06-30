import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});

const api_name = '/admin/vod';

//家长添加学生
export const saveStudent = (id, student) => {
    return apiClient.post(`${api_name}/student/saveStudent/${id}`, student);
};

//id查询学生
export const getStudentById = (id) => {
    return apiClient.get(`${api_name}/student/getStudent/${id}`);
};

//修改学生信息
export const updateParent = (student) => {
    return apiClient.post(`${api_name}/student/updateStudent`, student);
};

//学生健康情况
export const saveHealth = (health) => {
    return apiClient.post(`${api_name}/health/saveHealth`, health);
};
export const getStudentHealth = (id) => {
    return apiClient.get(`${api_name}/health/getStudentHealth/${id}`);
};
export const updateHealth = (health) => {
    return apiClient.post(`${api_name}/health/updateHealth`, health);
};
//删除
export const removeStudent = (studentId) => {
    return apiClient.delete(`${api_name}/student/remove/${studentId}`);
};
