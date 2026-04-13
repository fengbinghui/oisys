<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-2"><h1 class="page-title">角色管理</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box box-primary content-card">
          <div class="box-header card-header">
            <h3 class="box-title card-tools"><router-link to="/role/edit" class="label label-success action-btn action-btn-primary"><span class="glyphicon glyphicon-plus"></span> 新增</router-link></h3>
          </div>
          <div class="box-body no-padding card-body">
            <div class="table-card"><table class="table table-hover table-striped">
              <tbody>
                <tr><th>ID</th><th>角色名</th><th>角色值</th><th>操作</th></tr>
                <tr v-for="r in roleList" :key="r.roleId">
                  <td>{{ r.roleId }}</td><td>{{ r.roleName }}</td><td>{{ r.roleValue }}</td>
                  <td>
                    <router-link :to="`/role/edit/${r.roleId}`" class="label xiugai action-btn action-btn-info"><span class="glyphicon glyphicon-edit"></span> 修改</router-link>
                    <a class="label shanchu action-btn action-btn-danger" @click.prevent="remove(r.roleId)"><span class="glyphicon glyphicon-remove"></span> 删除</a>
                  </td>
                </tr>
              </tbody>
            </table></div>
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
  data () { return { roleList: [], pageInfo: {} }; },
  created () { this.load(); },
  watch: { $route () { this.load(); } },
  methods: {
    load () {
      this.axios.get("roleManage/list", { params: { page: this.pageNum } }).then(res => {
        const p = res.data.roleList;
        this.roleList = p.list || [];
        this.pageInfo = { pages: p.pages, pageNum: p.pageNum, total: p.total, pageSize: p.pageSize, isFirstPage: p.isFirstPage, isLastPage: p.isLastPage, hasPreviosPage: p.hasPreviosPage, hasNextPage: p.hasNextPage };
      });
    },
    remove (id) { if (!confirm("确认删除?")) return; this.axios.delete("roleManage/remove", { params: { roleId: id } }).then(res => { alert(res.data.msg); this.load(); }); }
  }
};
</script>
