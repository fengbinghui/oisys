<template>
  <div class="page-shell">
    <div class="page-header">
      <div><h1 class="page-title">用户管理</h1></div>
    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box box-primary content-card">
          <div class="box-header card-header">
            <h3 class="box-title card-tools">
              <router-link to="/user/edit" class="label label-success action-btn action-btn-primary">
                <span class="glyphicon glyphicon-plus"></span> 新增
              </router-link>
            </h3>
            <div class="box-tools">
              <div class="input-group search-group">
                <input class="form-control input-sm" v-model="keywords" placeholder="用户名/姓名" />
                <div class="input-group-btn">
                  <a class="btn btn-sm btn-default" @click.prevent="search"><span class="glyphicon glyphicon-search"></span></a>
                </div>
              </div>
            </div>
          </div>
          <div class="box-body no-padding card-body">
            <div class="table-responsive table-card">
              <table class="table table-hover table-striped">
                <tbody>
                  <tr><th>账号</th><th>姓名</th><th>角色</th><th>状态</th><th>操作</th></tr>
                  <tr v-for="u in userList" :key="u.userId">
                    <td>{{ u.userName }}</td><td>{{ u.realName }}</td><td>{{ u.roleName || u.roleId }}</td>
                    <td>{{ u.isLock == 0 ? "启用" : "禁用" }}</td>
                    <td>
                      <router-link :to="`/user/edit/${u.userId}`" class="label xiugai action-btn action-btn-info"><span class="glyphicon glyphicon-edit"></span> 修改</router-link>
                      <a class="label shanchu action-btn action-btn-danger" @click.prevent="remove(u.userId)"><span class="glyphicon glyphicon-remove"></span> 删除</a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <footer-paging :pageInfo="pageInfo"></footer-paging>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FooterPaging from "../../components/FooterPaging.vue";
export default {
  components: { FooterPaging },
  props: ["pageNum"],
  data () { return { userList: [], pageInfo: {}, keywords: "" }; },
  created () { this.keywords = this.$route.params.keywords || ""; this.load(); },
  watch: { $route () { this.load(); } },
  methods: {
    load () {
      this.axios.get("userManage/list", { params: { page: this.pageNum, searchName: this.keywords } }).then(res => {
        const p = res.data.userList;
        this.userList = p.list || [];
        this.pageInfo = { pages: p.pages, pageNum: p.pageNum, total: p.total, pageSize: p.pageSize, isFirstPage: p.isFirstPage, isLastPage: p.isLastPage, hasPreviosPage: p.hasPreviosPage, hasNextPage: p.hasNextPage };
      });
    },
    search () { this.$router.push(`/user/list/1/${this.keywords || ""}`); },
    remove (id) {
      if (!confirm("确认删除该用户?")) return;
      this.axios.delete("userManage/remove", { params: { userId: id } }).then(res => { alert(res.data.msg); this.load(); });
    }
  }
};
</script>
