import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';

//查询全部
export const noticeFindAll = () => {
  return apiClient.get(`${api_name}/notice/findAll`);
};
//带分页
export const noticeList = (page, limit, searchObj) => {
  return apiClient.get(`${api_name}/notice/findQueryPage/${page}/${limit}`, {params:searchObj});
};
//id查询公告并且设置已读
export const noticeGetById = (id, loginUserId) => {
  return apiClient.get(`${api_name}/notice/getNoticeUserRead/${id}/${loginUserId}`);
};
// //查询未读公告数 需要用户id和角色
export const getUnread = (loginUserId, loginRoles) => {
  return apiClient.get(`${api_name}/notice/getUnread/${loginUserId}/${loginRoles}`);
};