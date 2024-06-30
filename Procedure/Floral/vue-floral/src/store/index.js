import { createStore } from "vuex";
//持久化数据
import createPersistedState from 'vuex-persistedstate';

const store = createStore({
  state() {
    return {
      user: {
        token: '',
        avatar: '',
        loginRoles: '',
        loginUserId: '',
        loginUserName: '',
        loginUnread: ''//未读公告
      }
    };
  },
  mutations: {
    setUser(state, { token, avatar, loginRoles, loginUserId, loginUserName, loginUnread }) {
      state.user.token = token;
      state.user.avatar = avatar;
      state.user.loginRoles = loginRoles;
      state.user.loginUserId = loginUserId;
      state.user.loginUserName = loginUserName;
      state.user.loginUnread = loginUnread;
    },
    clearUser(state) {
      state.user.token = '';
      state.user.avatar = '';
      state.user.loginRoles = '';
      state.user.loginUserId = '';
      state.user.loginUserName = '';
      state.user.loginUnread = '';
    },
    updateAvatar(state, avatar) {
      state.user.avatar = avatar;
    },
    updateUnread(state, loginUnread) {
      state.user.loginUnread = loginUnread;
    },
  },
  actions: {
    login({ commit }, { token, avatar, loginRoles, loginUserId, loginUserName, loginUnread }) {
      // 可以添加登录逻辑
      // 然后在登录成功后更新状态
      commit('setUser', { token, avatar, loginRoles, loginUserId, loginUserName, loginUnread });
    },
    updateAvatar({ commit }, avatar) {
      commit('updateAvatar', avatar);
    },
    updateUnread({ commit }, loginUnread) {
      commit('updateUnread', loginUnread);
    },
  },
  getters: {
    isLoggedIn: (state) => {
      return !!state.user.token;
    }
  },
  plugins: [createPersistedState({
    paths: ['user'], // 指定需要持久化的状态
  })],
});

export default store;