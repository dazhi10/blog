import Vue from "vue";
import Vuex from "vuex";
// import * as getters from './getters.js'

Vue.use(Vuex);

const mutations = {
  loadingIndex(state, response) {
    state.loadingIndex = response;
  }
};

/** 状态定义 */
export const state = {
  themeObj: 0, //主题
  keywords: "", //关键词
  //errorImg: 'this.onerror=null;this.src="' + require('../../static/img/tou.jpg') + '"',
  loadingIndex: false, //编辑文章后是否重新提交文章索引
  baseURL: "http://localhost:7777/"
};

export default new Vuex.Store({
  state,
  mutations
});
