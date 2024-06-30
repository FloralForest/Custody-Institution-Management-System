import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});

const api_name = '/admin/vod';

//id查询家长
export const getParentById = (id) => {
  return apiClient.get(`${api_name}/parent/getParent/${id}`);
};

//修改家长信息
export const updateParent = (parent) => {
  return apiClient.post(`${api_name}/parent/updateParent`, parent);
};

//查询孩子
export const getChildren = (id) => {
  return apiClient.get(`${api_name}/parent/getChildren/${id}`);
};