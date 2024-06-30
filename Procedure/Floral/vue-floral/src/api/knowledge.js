import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8301/', // 后端API基础URL
});
const api_name = '/admin/vod';

//查询全部
export const knowledgeFindAll = () => {
  return apiClient.get(`${api_name}/knowledge/findAll`);
};
export const knowledgeGetById = (id) => {
  return apiClient.get(`${api_name}/knowledge/getKnowledge/${id}`);
};
//食谱信息
export const recipeFindAll = () => {
  return apiClient.get(`${api_name}/recipe/findAll`);
};
export const recipeGetById = (id) => {
  return apiClient.get(`${api_name}/recipe/getRecipe/${id}`);
};