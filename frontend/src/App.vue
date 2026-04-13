<template>
  <div class="app-shell">
    <div class="app-loading-container">
      <div class="progress">
        <div class="progress-bar">
          <div class="progress-shadow"></div>
        </div>
      </div>
    </div>

    <div class="main">
      <div class="container-fluid">
        <div class="row">
          <left-menu :roleId="loginUser.roleId" :superman="loginUser.superman" :menus="loginUser.menus"></left-menu>

          <!--顶层右侧的导航栏，栅格系统分10份-->
          <div class="col-md-10 moredeep app-main-column">
            <!-- 导航栏 -->
            <div class="col-md-12 blue app-topbar">
              <ul class="nav navbar-nav navbar-right moredeep app-topbar-right">
                <li class="dropdown">
                  <!--设置导航栏头像面板-->
                  <a href="#"
                     class="white blue-none"
                     data-toggle="dropdown">
                    <img src="/images/oasys.jpg"
                         class="user-image" />
                    <span>欢迎您，{{ loginUser.realName }}</span>
                  </a>
                  <!--设置点击按钮弹出用户面板层-->
                  <ul class="dropdown-menu app-user-dropdown">
                    <li class="user-header blue">
                      <img :src="`${this.axios.defaults.baseURL}/face/${loginUser.imgPath}`"
                           class="img-circle app-user-avatar" />
                      <p class="white"
                         style="">
                        <span>{{ loginUser.className }} </span><br />
                        <small>{{ getRoleName(loginUser.roleId) }}</small>
                      </p>
                    </li>
                    <li class="user-footer">
                      <div class="pull-left">
                        <a href="#"
                           class="btn btn-default"
                           @click.prevent="openUserPanel">用户面板</a>
                      </div>
                      <div class="pull-right">
                        <a href="#"
                           class="btn btn-danger"
                           @click.prevent="logout">退出登录</a>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </div>

            <div class="col-md-12 list-right app-content-wrapper">
              <router-view></router-view>

              <div class="col-md-12 app-footer-bar">
                <div class="pull-right currt-time app-current-time">
                  {{ currentDateText }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LeftMenu from "./components/LeftMenu.vue";
export default {
  name: "App",
  components: {
    LeftMenu,
  },
  data () {
    return {
      loginUser: JSON.parse(sessionStorage.getItem("user")),
      currentDateText: "",
    };
  },
  created () {
    if (this.loginUser == null) {
      self.location.href = "/login.html";
    }
    this.backfillCurrentUser();
    this.refreshCurrentDate();
  },
  methods: {
    backfillCurrentUser () {
      if (this.loginUser == null) return;
      const needBackfill = this.loginUser.superman == null || !Array.isArray(this.loginUser.menus);
      if (!needBackfill) return;
      this.axios.get("/currentUser").then(res => {
        if (res.data.code !== 0) return;
        this.loginUser.superman = res.data.superman;
        this.loginUser.roleId = res.data.roleId;
        this.loginUser.menus = res.data.menus || [];
        sessionStorage.setItem("user", JSON.stringify(this.loginUser));
      }).catch(() => {
        // 兼容老会话，接口失败时不阻断页面
      });
    },
    refreshCurrentDate () {
      const now = new Date();
      const weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
      this.currentDateText = `${now.getFullYear()}年${now.getMonth() + 1}月${now.getDate()}日,${weeks[now.getDay()]}`;
    },
    openUserPanel () {
      if (this.loginUser && this.loginUser.userId) {
        this.$router.push(`/user/edit/${this.loginUser.userId}`);
      }
    },
    getRoleName (roleId) {
      let roleName = "";
      switch (roleId) {
        case 1:
          roleName = "超级管理员";
          break;
        case 2:
          roleName = "老师";
          break;
        case 3:
          roleName = "主任";
          break;
        case 4:
          roleName = "学生";
          break;
      }
      return roleName;
    },
    logout () {
      this.axios.get("/logout").then(respond => {
        sessionStorage.clear();//清空sessionStorage
        alert(respond.data.msg);//成功退出登录提示
        self.location.href = "/login.html";//跳转到登录页面
      })
    }
  },
};
</script>
<style scoped>
.app-shell {
  min-height: 100vh;
}

.container-fluid {
  padding-right: 0px;
  padding-left: 0px;
  margin-right: 0px;
  margin-left: 0px;
}

.app-loading-container {
  left: 50%;
  position: absolute;
  top: 100px;
  transform: translate(-50%, -50%);
}

.app-main-column {
  margin: 0;
  padding: 0;
}

.app-topbar {
  min-height: 52px;
}

.app-topbar-right {
  padding-right: 12px;
}

.app-user-dropdown {
  padding: 0;
}

.app-user-avatar {
  height: 100px;
  width: 100px;
}

.app-content-wrapper {
  background: var(--layout-body-bg);
  min-height: calc(100vh - 52px);
  padding: 0 0 50px;
  position: relative;
}

.app-footer-bar {
  background: var(--surface-color);
  border-top: 1px solid var(--border-color);
  height: 50px;
  padding: 15px;
  position: static;
}

.app-current-time {
  color: var(--text-secondary);
  display: inline-block;
  margin-right: 20px;
}
</style>
 