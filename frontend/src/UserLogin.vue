<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-brand">智能考勤管理系统</div>
      <div class="login-card">
        <h2 class="login-title">账号密码登录</h2>
        <div v-if="error" class="alert alert-danger login-alert" role="alert">
          {{ errorMessage }}
        </div>
        <input
          type="text"
          placeholder="请输入账户名"
          autofocus="autofocus"
          name="userName"
          class="form-control login-input"
          v-model="userName"
        />
        <input
          type="password"
          placeholder="请输入密码"
          name="password"
          class="form-control login-input"
          v-model="password"
        />
        <button type="button" class="btn login-submit-btn" @click="login">
          登录
        </button>
      </div>
      <p class="login-slogan">欢迎使用智能考勤管理系统</p>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      userName: "",//用户名
      password: "",//密码
      error: false,//是否有错误
      errorMessage: "",//错误提示
    };
  },
  // name: "UserLogin",
  methods: {
    login () {
      if (this.userName.trim() == "" || this.password.trim() == "") {
        this.error = true;
        this.errorMessage = "请输入用户名或密码";
        return;
      }
      this.axios.post("login", { userName: this.userName, password: this.password }, {
        headers: { post: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8", }, },
      })
        .then((respond) => {
          if (respond.data.code == 0) {//登录成功
            sessionStorage.setItem("user", JSON.stringify({
              userId: respond.data.userId,
              realName: respond.data.realName,
              className: respond.data.className,
              roleId: respond.data.roleId,
              superman: respond.data.superman,
              imgPath: respond.data.imgPath,
              menus: respond.data.menus || [],
            }
            ));
            self.location.href = "/index.html";//跳转到首页
          } else {//如果登录失败,设置error为true,显示错误提示
            this.error = true;
            this.errorMessage = respond.data.msg;
          }
        });
    }
  },
};
</script>
<style scoped>
.login-alert {
  margin-bottom: 14px;
  text-align: left;
}
</style>
 