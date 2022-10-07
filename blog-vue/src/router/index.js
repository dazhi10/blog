import Vue from "vue";
import Router from "vue-router";
Vue.use(Router);

const router = new Router({
  routes: [
    { path: "/", redirect: "/Home" },
    {
      path: "/Home",
      component: resolve => require(["../pages/Home.vue"], resolve),
      meta: {
        auth: true
      },
      name: "Home"
    }, //首页
    {
      path: "/Share",
      component: resolve => require(["../pages/Share.vue"], resolve),
      meta: {
        auth: true
      },
      name: "Share"
    }, //分类
    {
      path: "/DetailArticle",
      component: resolve => require(["../pages/DetailArticle.vue"], resolve),
      meta: {
        auth: true
      },
      name: "DetailArticle"
    }, //分享详情
    {
      path: "/FriendsLink",
      component: resolve => require(["../pages/FriendsLink.vue"], resolve),
      meta: {
        auth: true
      },
      name: "FriendsLink"
    }, //友链
    {
      path: "/Login",
      component: resolve => require(["../pages/Login.vue"], resolve),
      meta: {
        auth: false
      },
      name: "Login"
    }, //注册登录
    {
      path: "/UserInfo",
      component: resolve => require(["../pages/UserInfo.vue"], resolve),
      meta: {
        auth: true
      },
      name: "UserInfo"
    } //用户个人中心
  ]
});

//路由前置守卫
router.beforeEach((to, from, next) => {
  if (to.path == "/UserInfo") {
    if (localStorage.getItem("userInfo")) {
      next();
    } else {
      next("/Home");
    }
  } else {
    next();
  }
});

export default router;
