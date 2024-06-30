// api.js  登录和注册
import axios from 'axios';
import store from '../store/index'; //  Vuex store 的实际路径

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/',
});

export const userLogin = async (loginForm) => {
  try {
    const response = await apiClient.post('/admin/vod/user/userLogin', loginForm, 
    { headers: {
        'Content-Type': 'application/json',
      }
    });
    // 检查后端传来的状态码
    if (response.data.code === 20000) {
      const { data } = response.data;
      store.dispatch('login', {
        token: data.loginToken,
        avatar: data.loginAvatar,
        loginRoles: data.loginRoles,
        loginUserId: data.loginUserId,
        loginUserName: data.loginUserName,
        loginUnread: data.loginUnread,
      }); // 更新全局状态
      return { success: true, data };
    } else {
      // 状态码不是 20000，返回错误信息
      return { success: false, message: response.data.message || '登录失败' };
    }
  } catch (error) {
    // 返回错误信息供调用者捕获
    return { success: false, message: error.response?.data?.message || error.message || '登录失败' };
  }
};
export const register = async (formData) => {
    const response = await apiClient.post('/admin/vod/user/saveUser', formData);
    if (response.data.code === 20000) return { success: true, data: response.data };
    else return { success: false, message: response.data.message || '注册失败' }; 
};