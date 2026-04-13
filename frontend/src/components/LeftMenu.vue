 <template>
  <div>
    <div class="col-lg-2 col-md-2 smallDiv app-sidebar">
      <div class="col-md-12 blue-b app-sidebar-logo-wrap">
        <a href="index" class="navbar-brand app-sidebar-logo">
          <img src="/images/logo.png" />
        </a>
      </div>
      <div class="col-md-12 list-left app-sidebar-content">
        <div class="thistable">
          <div class="app-sidebar-search-wrap">
            <input type="text" placeholder="查找..." class="search-input cha" />
            <span class="app-sidebar-search-btn-wrap">
              <a href="#" class="chazhao"
                ><span class="glyphicon glyphicon-search app-sidebar-search-icon"></span
              ></a>
            </span>
          </div>
          <div class="panel-group app-sidebar-accordion" id="accordion">
            <div class="panel panel-default">
              <div class="panel-heading" v-if="isSuperAdmin">
                <a
                  class="open-menu blue-left collapsed app-menu-item-collapsed"
                  href="#collapse92"
                  data-toggle="collapse"
                  data-parent="#accordion"
                  aria-expanded="false"
                >
                  <span class="glyphicon glyphicon glyphicon-calendar"></span>
                  <span>课程教学管理</span>
                  <span class="glyphicon pull-right glyphicon-menu-left"></span>
                </a>
              </div>
              <div
                id="collapse92"
                class="panel-collapse collapse"
                aria-expanded="false"
              >
                <ul>
                  <li v-if="isSuperAdmin && hasMenu(['course/list', '课程设置'])">
                    <a href="/course/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>课程设置</span>
                    </a>
                  </li>

                  <li v-if="(isSuperAdmin || isTeacher) && hasMenu(['courseManage/list', '教学管理'])">
                    <router-link to="/courseManage/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>教学管理</span>
                    </router-link>
                  </li>
                </ul>
              </div>
              <div class="panel-heading">
                <a
                  class="open-menu blue-left collapsed app-menu-item-collapsed"
                  href="#collapse4"
                  data-toggle="collapse"
                  data-parent="#accordion"
                  aria-expanded="false"
                  v-if="isSuperAdmin || isTeacher"
                >
                  <span class="glyphicon glyphicon-time"></span>
                  <span>考勤管理</span>
                  <span class="glyphicon pull-right glyphicon-menu-left"></span>
                </a>
              </div>
              <div
                id="collapse4"
                class="panel-collapse collapse"
                aria-expanded="false"
                v-if="isSuperAdmin || isTeacher"
              >
                <ul>
                  <li v-if="isSuperAdmin && hasMenu(['attendance/rules', '考勤规则'])">
                    <router-link to="/attendance/rules/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>考勤规则设置</span>
                    </router-link>
                  </li>
                  <li v-if="(isSuperAdmin || isTeacher) && hasMenu(['attendance/monthList', '查询统计'])">
                    <router-link
                      :to="`/attendance/monthList/${new Date().getFullYear()}/${
                        new Date().getMonth() + 1
                      }/1`"
                    >
                      <span class="glyphicon glyphicon-record"></span>
                      <span>查询统计</span>
                    </router-link>
                  </li>
                </ul>
              </div>
              <div class="panel-heading" v-if="isSuperAdmin">
                <a
                  class="open-menu blue-left app-menu-item-active"
                  href="#collapse1"
                  data-toggle="collapse"
                  data-parent="#accordion"
                  aria-expanded="true"
                >
                  <span class="glyphicon glyphicon-cog"></span>
                  <span>系统管理</span>
                  <span class="glyphicon pull-right glyphicon-menu-down"></span>
                </a>
              </div>
              <div
                v-if="isSuperAdmin"
                id="collapse1"
                class="panel-collapse collapse in"
                aria-expanded="true"
              >
                <ul>
                  <li v-if="hasMenu(['user/list', '用户管理'])">
                    <router-link to="/user/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>用户管理</span>
                    </router-link>
                  </li>

                  <li v-if="hasMenu(['role/list', '角色管理'])">
                    <router-link to="/role/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>角色管理</span>
                    </router-link>
                  </li>

                  <li v-if="hasMenu(['menu/list', '菜单管理'])">
                    <router-link to="/menu/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>菜单管理</span>
                    </router-link>
                  </li>

                  <li v-if="hasMenu(['loginRecord/list', '登录记录'])">
                    <router-link to="/loginRecord/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>登录记录</span>
                    </router-link>
                  </li>

                  <li v-if="hasMenu(['student/list', '学生'])">
                    <router-link to="/student/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>学生基本信息管理</span>
                    </router-link>
                  </li>

                  <li v-if="hasMenu(['class/list', '班级'])">
                    <router-link to="/class/list/1">
                      <span class="glyphicon glyphicon-record"></span>
                      <span>班级信息管理</span>
                    </router-link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    roleId: [String, Number],
    superman: [String, Number],
    menus: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    isSuperAdmin () {
      return Number(this.superman) === 1 || Number(this.roleId) === 1;
    },
    isTeacher () {
      return Number(this.roleId) === 2;
    },
    menuAuthEnabled () {
      return Array.isArray(this.menus) && this.menus.length > 0;
    }
  },
  methods: {
    hasMenu (keys) {
      if (!this.menuAuthEnabled) return true;
      return this.menus.some(m => {
        const text = `${m.menuUrl || ""}|${m.menuName || ""}`;
        return keys.some(k => text.indexOf(k) !== -1);
      });
    }
  }
};
</script>

<style scoped>
.app-sidebar {
  display: block;
  margin: 0;
  padding: 0;
}

.app-sidebar-logo-wrap {
  min-height: 52px;
}

.app-sidebar-logo {
  padding: 8px 10px;
}

.app-sidebar-content {
  background: var(--layout-sidebar-bg);
  min-height: calc(100vh - 52px);
}

.app-sidebar-search-wrap {
  margin-top: 20px;
  position: relative;
}

.app-sidebar-search-btn-wrap {
  background: var(--layout-sidebar-input-bg);
  border-bottom-right-radius: 2px;
  border-top-right-radius: 2px;
  display: inline-block;
  height: 35px;
  line-height: 35px;
  position: absolute;
  text-align: center;
  width: 38px;
}

.app-sidebar-search-icon {
  color: #fff;
}

.app-sidebar-accordion {
  margin-left: -12px;
  margin-top: 10px;
}

.app-menu-item-collapsed {
  border-left: 3px solid transparent;
}

.app-menu-item-active {
  border-left: 3px solid var(--primary-color);
}

</style>
 