<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-3"><h1 class="page-title">登录记录</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box box-primary content-card">
          <div class="box-header card-header">
            <div class="box-tools">
              <div class="input-group search-group">
                <input class="form-control input-sm" v-model="keywords" placeholder="姓名/账号" />
                <div class="input-group-btn"><a class="btn btn-sm btn-default" @click.prevent="search"><span class="glyphicon glyphicon-search"></span></a></div>
              </div>
            </div>
          </div>
          <div class="box-body no-padding card-body">
            <div class="table-card"><table class="table table-hover table-striped"><tbody>
              <tr><th>用户</th><th>IP</th><th>浏览器</th><th>登录时间</th></tr>
              <tr v-for="r in recordList" :key="r.recordId"><td>{{ r.realName || r.userId }}</td><td>{{ r.ipAddr }}</td><td>{{ r.browser }}</td><td>{{ r.loginTime }}</td></tr>
            </tbody></table></div>
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
  data () { return { keywords: "", recordList: [], pageInfo: {} }; },
  created () { this.keywords = this.$route.params.keywords || ""; this.load(); },
  watch: { $route () { this.load(); } },
  methods: {
    load () {
      this.axios.get("userLoginRecord/list", { params: { page: this.pageNum, searchName: this.keywords } }).then(res => {
        const p = res.data.recordList;
        this.recordList = p.list || [];
        this.pageInfo = { pages: p.pages, pageNum: p.pageNum, total: p.total, pageSize: p.pageSize, isFirstPage: p.isFirstPage, isLastPage: p.isLastPage, hasPreviosPage: p.hasPreviosPage, hasNextPage: p.hasNextPage };
      });
    },
    search () { this.$router.push(`/loginRecord/list/1/${this.keywords || ""}`); }
  }
};
</script>
