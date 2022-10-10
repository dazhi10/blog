import Vue from "vue";

import "normalize.css/normalize.css"; // A modern alternative to CSS resets

import directive from "./directive"; // directive
import "@/styles/index.scss"; // global css

import App from "./App";
import store from "./store";
import router from "./router";
import plugins from "./plugins"; // plugins
import "./assets/icons"; // icon
import "@/permission"; // permission control
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
} from "@/utils/sg";

import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
// 全局方法挂载
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.selectDictLabels = selectDictLabels;
Vue.prototype.handleTree = handleTree;

Vue.use(directive);

Vue.use(plugins);
Vue.use(mavonEditor);
Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
