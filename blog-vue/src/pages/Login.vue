<!-- 登录注册 -->
<template>
    <div>
      <div class="container">
          <h1 class="loginTitle">
          </h1>
          <!-- 登录注册 -->
          <div style="height: 500px;">
              <div v-if="login==1" class="loginBox">
                  <div class="lr-title">
                      <h1>登录</h1>
                      <p>
                          新用户<a href="#/Login?login=0" class="tcolors">注册</a>
                      </p>
                  </div>
                  
                  <el-input
                      type="text"
                      placeholder="邮箱"
                      v-model="username">
                  </el-input>

                  <el-input
                          type="password"
                        placeholder="密码"
                          @keyup.enter.native="loginEnterFun"
                        v-model="password">
                  </el-input>

                  <h3><a href="">忘记密码？</a></h3>
                  <div class="lr-btn tcolors-bg" @click="gotoHome">登录</div>
              </div>
              <div v-else class="registerBox">
                  <div class="lr-title">
                      <h1>注册</h1>
                      <p>
                          已有账号<a href="#/Login?login=1" class="tcolors">登录</a>
                      </p>
                  </div>
                  
                  <el-input
                      type="text"
                      placeholder="邮箱"
                      v-model="nusername">
                  </el-input>

                  <el-input
                        type="password"
                        placeholder="密码:6-12位英文、数字、下划线"
                        v-model="npassword">
                  </el-input>
                  
                  <el-input
                          type="password"
                        placeholder="确认密码"
                          @keyup.enter.native="registerEnterFun"
                        v-model="npassword2">
                  </el-input>
                  
                  <div class="lr-btn tcolors-bg" @click="newRegister">注册</div>
              </div>
          </div>
      </div>
    </div>
</template>

<script>
import { userLogin, userRegister } from "../api/user.js";
import { setToken } from "../utils/auth.js";
export default {
  name: "Login",
  data() {
    //选项 / 数据
    return {
      username: "", //用户名
      password: "", //密码
      nusername: "", //新用户注册名
      npassword: "", //新用户注册密码
      npassword2: "", //新用户注册重复密码
      login: 0, //是否已经登录
      loginErr: false, //登录错误
      loginTitle: "用户名或密码错误",
      urlstate: 0 //重新注册
    };
  },
  methods: {
    //事件处理器
    routeChange: function() {
      var that = this;
      that.login =
        that.$route.query.login == undefined
          ? 1
          : parseInt(that.$route.query.login); //获取传参的login
      that.urlstate =
        that.$route.query.urlstate == undefined
          ? 0
          : that.$route.query.urlstate; //获取传参的usrlstate状态码
    },

    loginEnterFun: function(e) {
      var keyCode = window.event ? e.keyCode : e.which;
      if (keyCode == 13) {
        this.gotoHome();
      }
    },

    gotoHome: function() {
      //用户登录
      userLogin(this.username, this.password).then(response => {
        // 登录成功记录token和用户信息，登录失败给对应提示
        setToken(response.token);
        // 存储用户信息
        localStorage.setItem("userInfo", JSON.stringify(response.userInfo));
        this.$router.push("/Home");
      });
    },
    registerEnterFun: function(e) {
      var keyCode = window.event ? e.keyCode : e.which;
      if (keyCode == 13) {
        this.newRegister();
        userRegister();
      }
    },
    newRegister: function() {
      if (this.npassword === this.npassword2) {
        userRegister(this.nusername, this.npassword).then(response => {
          this.$message({
            message: response,
            type: "success"
          });

          //去登录
          this.$router.push({ path: "/Login?login=1" });
        });
      } else {
        this.$message({
          message: "两次密码不相同!",
          type: "error"
        });
        return;
      }
    },
    goLogin: function() {
      //去登录
      this.$router.push({ path: "/Login?login=1" });
    },
    goRegister: function() {
      //去注册
      this.$router.push({ path: "/Login?login=0" });
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    $route: "routeChange"
  },
  created() {
    //生命周期函数
    var that = this;
    that.routeChange();
  }
};
</script>

<style>
/*登录注册标题*/
.loginTitle {
  text-align: center;
  font-size: 26px;
  padding-top: 50px;
  margin-bottom: 100px;
}
.loginBox,
.registerBox {
  background: #fff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  padding: 40px;
  max-width: 320px;

  margin: 0 auto;
}
.loginBox {
  padding-bottom: 0;
  height: 300px;
}
.lr-title {
  position: relative;
  height: 32px;
  line-height: 32px;
  margin-bottom: 20px;
}
.lr-title h1 {
  font-size: 24px;
  color: #666;
  font-weight: bold;
  /*width:50%;*/
}
.lr-title p {
  font-size: 12px;
  color: #999;
  position: absolute;
  right: 0;
  top: 0;
}
.lr-btn {
  color: #fff;
  text-align: center;
  letter-spacing: 5px;
  padding: 8px;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 30px;
}
.loginBox .el-input,
.registerBox .el-input {
  margin-bottom: 20px;
}
.loginBox .el-alert,
.registerBox .el-alert {
  top: -18px;
  background-color: #888;
}
.loginBox .el-input input,
.registerBox .el-input input {
  border-radius: 4px;
}
.loginBox h3,
.registerBox h3 {
  text-align: right;
  margin-bottom: 20px;
}
.loginBox h3 a,
.registerBox h3 a {
  font-size: 13px;
  color: #999;
}
.loginBox .otherLogin {
  max-width: 320px;
  padding: 30px 40px;
  background: #ddd;
  text-align: center;
  margin-left: -40px;
  margin-right: -40px;
  visibility: hidden;
}
.loginBox .otherLogin p {
  margin-bottom: 20px;
  font-size: 16px;
}
.loginBox .otherLogin a i {
  display: inline-block;
  width: 42px;
  height: 42px;
  line-height: 42px;
  font-size: 18px;
  border-radius: 50%;
  color: #fff;
  margin: 0 10px;
}
.loginBox .otherLogin a i.fa-wechat {
  background: #7bc549;
}
.loginBox .otherLogin a i.fa-qq {
  background: #56b6e7;
}
.loginBox .otherLogin a i.fa-weibo {
  background: #ff763b;
}

/*登录成功*/
.registerSuc {
  padding: 40px;
  margin: 0 auto;
}
.registerSuc .sucIcon {
  text-align: center;
  margin-bottom: 30px;
  padding-left: 60px;
}
.registerSuc .sucContent {
  line-height: 1.5;
  font-size: 15px;
  text-align: center;
}
.registerSuc .sucContent p {
  margin-top: 10px;
  font-size: 13px;
  color: #999;
}
.registerSuc .sucContent .lastbtn {
  display: inline-block;
  font-size: 14px;
  padding: 3px 10px;
  border-radius: 5px;
  color: #fff;
  cursor: pointer;
}
.registerSuc .sucContent .el-icon-close {
  fong-size: 13px;
}
</style>
