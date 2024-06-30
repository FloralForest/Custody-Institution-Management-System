//用户信息查询/修改
import axios from 'axios';
import store from '../store/index'; //  Vuex store 的实际路径

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});

const api_name = '/admin/vod';

//id查询用户
export const getUserById = (id) => {
  return apiClient.get(`${api_name}/user/getUser/${id}`);
};
//修改信息
export const updateUser = (user) => {
  return apiClient.post(`${api_name}/user/updateUser`, user);
};
//修改用户头像
export const updateUserAvatar = async (user) => {
  try {
    const response = await apiClient.post(`${api_name}/user/updateUser`, user);
    // 检查后端传来的状态码
    if (response.data.code === 20000) {
      // 更新 Vuex 中的用户头像
      store.dispatch('updateAvatar', user.avatar);
      return { success: true, data: response.data };
    } else {
      return { success: false, message: response.data.message || '更新头像失败' };
    }
  } catch (error) {
    return { success: false, message: error.response?.data?.message || error.message || '更新失败' };
  }
};
//绑定家长
export const bindParent = (id, parent) => {
  return apiClient.post(`${api_name}/parent/saveParent/${id}`, parent);
};