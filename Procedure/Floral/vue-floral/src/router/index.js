import { createRouter, createWebHistory } from 'vue-router'
import login from '../views/login/index.vue';
import course from '../views/content/course/list.vue'
import activity from '../views/content/activity/list.vue'
import teacher from '../views/content/teacher/list.vue'
import register from '../views/register/index.vue'
import information from '../views/content/information/index.vue'
import StudentForm from '../views/content/information/form/StudentForm.vue'
import bindingInfor from '../views/content/information/form/bindingInformation.vue'
import courseDetails from '../views/content/course/details/details.vue'
import teacherDetails from '../views/content/teacher/details/details.vue'
import knowledge from '../views/content/knowledge/list.vue'
import kwedetails from '../views/content/knowledge/details/details.vue'
import notice from '../views/content/notice/list.vue'
import recipe from '../views/content/recipe/list.vue'
import recipeDetails from '../views/content/recipe/details/details.vue'
// ...导入其他内容组件

//,  meta: { hideNavbar: true }不显示导航栏
const routes = [
  { path: '/login', name: 'login', component: login},
  { path: '/register', name: 'register', component: register},
  { path: '/course', name: 'course', component: course },
  { path: '/courseDetails/:id', name: 'courseDetails', component: courseDetails },
  { path: '/activity', name: 'activity', component: activity },
  { path: '/information', name: 'information', component: information },
  { path: '/teacher', name: 'teacher', component: teacher },
  { path: '/teacherDetails/:id', name: 'teacherDetails', component: teacherDetails },
  { path: '/StudentForm/:id?', name: 'StudentForm', component: StudentForm },
  { path: '/bindingInfor', name: 'bindingInfor', component: bindingInfor },
  { path: '/knowledge', name: 'knowledge', component: knowledge },
  { path: '/knowledge/kwedetails/:id', name: 'kwedetails', component: kwedetails },
  { path: '/notice', name: 'notice', component: notice },
  { path: '/recipe', name: 'recipe', component: recipe },
  { path: '/recipe/recipeDetails/:id', name: 'recipeDetails', component: recipeDetails },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router