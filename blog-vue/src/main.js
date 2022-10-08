import Vue from "vue";
import App from "./App";
import router from "./router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/style.less";
import store from "./store";
import MavonEditor from "mavon-editor";
import hljs from "highlight.js";
import "highlight.js/styles/vs2015.css"; //样式

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(MavonEditor);
Vue.directive("highlight", function(el) {
  let blocks = el.querySelectorAll("pre code");
  blocks.forEach(block => {
    hljs.highlightBlock(block);
  });
});

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
  store
});
