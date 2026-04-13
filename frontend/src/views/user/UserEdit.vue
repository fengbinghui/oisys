<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-2"><h1 class="page-title">用户编辑</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box content-card">
          <form @submit.prevent="save">
            <div class="box-header card-header">
              <h3 class="box-title card-tools"><a href="javascript:history.back();" class="label label-default action-btn"><i class="glyphicon glyphicon-chevron-left"></i><span>返回</span></a></h3>
            </div>
            <div class="box-body card-body">
              <div class="row">
                <div class="col-md-6 form-group"><label>账号</label><input class="form-control" v-model="user.userName" :readonly="user.userId" /></div>
                <div class="col-md-6 form-group"><label>姓名</label><input class="form-control" v-model="user.realName" /></div>
                <div class="col-md-6 form-group"><label>角色</label><select class="form-control" v-model="user.roleId"><option v-for="r in roles" :key="r.roleId" :value="r.roleId">{{ r.roleName }}</option></select></div>
                <div class="col-md-6 form-group"><label>重置密码</label><br><input type="checkbox" v-model="resetPassword" /></div>
              </div>
            </div>
            <div class="box-footer form-card-footer"><input class="btn btn-primary" type="submit" value="保存"><input class="btn btn-default" type="button" value="取消" onclick="history.back()"></div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["userId"],
  data () { return { user: { roleId: 4 }, roles: [], resetPassword: false }; },
  created () {
    this.axios.get("userManage/edit", { params: { userId: this.userId } }).then(res => {
      this.roles = res.data.roles || [];
      if (res.data.user) this.user = res.data.user;
    });
  },
  methods: {
    save () {
      const fd = new FormData();
      if (this.user.userId) fd.append("userId", this.user.userId);
      fd.append("userName", this.user.userName || "");
      fd.append("realName", this.user.realName || "");
      fd.append("roleId", this.user.roleId || 4);
      fd.append("resetPassword", this.resetPassword);
      this.axios.post("userManage/save", fd).then(res => {
        alert(res.data.msg);
        if (res.data.code === 0) this.$router.push("/user/list/1");
      });
    }
  }
};
</script>
