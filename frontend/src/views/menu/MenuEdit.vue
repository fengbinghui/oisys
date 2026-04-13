<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-2"><h1 class="page-title">菜单编辑</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box content-card">
          <form @submit.prevent="save">
            <div class="box-body card-body">
              <div class="row">
                <div class="col-md-6 form-group"><label>菜单名称</label><input class="form-control" v-model="menu.menuName"></div>
                <div class="col-md-6 form-group"><label>菜单URL</label><input class="form-control" v-model="menu.menuUrl"></div>
                <div class="col-md-6 form-group"><label>图标</label><input class="form-control" v-model="menu.menuIcon"></div>
                <div class="col-md-6 form-group"><label>层级</label><input class="form-control" v-model="menu.menuGrade"></div>
                <div class="col-md-6 form-group"><label>父级ID</label><input class="form-control" v-model="menu.parentId"></div>
                <div class="col-md-6 form-group"><label>排序</label><input class="form-control" v-model="menu.sortId"></div>
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
  props: ["menuId"],
  data () { return { menu: { isShow: 1, menuGrade: 1, sortId: 1 } }; },
  created () {
    this.axios.get("menuManage/edit", { params: { menuId: this.menuId } }).then(res => {
      if (res.data.menu) this.menu = res.data.menu;
    });
  },
  methods: {
    save () {
      const fd = new FormData();
      if (this.menu.menuId) fd.append("menuId", this.menu.menuId);
      ["menuName", "menuUrl", "menuIcon", "menuGrade", "parentId", "sortId", "isShow"].forEach(k => fd.append(k, this.menu[k] == null ? "" : this.menu[k]));
      this.axios.post("menuManage/save", fd).then(res => {
        alert(res.data.msg);
        if (res.data.code === 0) this.$router.push("/menu/list/1");
      });
    }
  }
};
</script>
