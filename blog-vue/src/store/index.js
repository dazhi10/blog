import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const mutations = {
  loadingIndex(state, response) {
    state.loadingIndex = response;
  },
};

/** 状态定义 */
export const state = {
  themeObj: 0, //主题
  keywords: "", //关键词
  baseURL: "http://localhost:7777/",
  scroll_img: "../assets/img/scroll.png",
};

export default new Vuex.Store({
  state,
  mutations,
});
