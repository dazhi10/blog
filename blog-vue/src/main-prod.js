import Vue from "vue";
import App from "./App";
import router from "./router";
import store from "./store";

import "./assets/css/style.less";

// import "highlight.js/styles/vs2015.css"; //样式

Vue.config.productionTip = false;

Vue.use(MavonEditor);

Vue.directive("highlight", function (el) {
  let blocks = el.querySelectorAll("pre code");
  blocks.forEach((block) => {
    hljs.highlightBlock(block);
  });
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
