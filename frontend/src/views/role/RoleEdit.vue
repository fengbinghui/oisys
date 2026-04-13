<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-2"><h1 class="page-title">角色编辑</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box content-card">
          <form @submit.prevent="save">
            <div class="box-body card-body">
              <div class="row">
                <div class="col-md-6 form-group"><label>角色名</label><input class="form-control" v-model="role.roleName"></div>
                <div class="col-md-6 form-group"><label>角色值</label><input class="form-control" v-model="role.roleValue"></div>
              </div>
              <div class="row" v-if="role.roleId">
                <div class="col-md-12 form-group">
                  <label>菜单权限</label>
                  <div>
                    <label v-for="m in allMenus" :key="m.menuId" class="menu-check-item">
                      <input type="checkbox" :value="m.menuId" v-model="menuIds"> {{ m.menuName }}
                    </label>
                  </div>
                </div>
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
  props: ["roleId"],
  data () { return { role: {}, allMenus: [], menuIds: [] }; },
  created () { this.load(); },
  methods: {
    load () {
      this.axios.get("roleManage/edit", { params: { roleId: this.roleId } }).then(res => {
        this.role = res.data.role || {};
        if (this.roleId) {
          this.axios.get("roleManage/menus", { params: { roleId: this.roleId } }).then(resp => {
            this.allMenus = resp.data.allMenus || [];
            this.menuIds = (resp.data.selectedMenus || []).map(x => x.menuId);
          });
        }
      });
    },
    save () {
      const fd = new FormData();
      if (this.role.roleId) fd.append("roleId", this.role.roleId);
      fd.append("roleName", this.role.roleName || "");
      fd.append("roleValue", this.role.roleValue || "");
      this.axios.post("roleManage/save", fd).then(res => {
        if (res.data.code !== 0) return alert(res.data.msg);
        const roleId = this.role.roleId || res.data.roleId || null;
        if (roleId) {
          this.role.roleId = roleId;
          const fd2 = new FormData();
          fd2.append("roleId", roleId);
          this.menuIds.forEach(id => fd2.append("menuIds", id));
          this.axios.post("roleManage/saveMenus", fd2).then(resp => { alert(resp.data.msg); this.$router.push("/role/list/1"); });
        } else {
          alert(res.data.msg);
          this.$router.push("/role/list/1");
        }
      });
    }
  }
};
</script>
