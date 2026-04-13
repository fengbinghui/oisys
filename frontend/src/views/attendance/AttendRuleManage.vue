<template>
  <div class="page-shell">
    <div class="page-header"><div class="col-md-2"><h1 class="page-title">考勤规则设置</h1></div></div>
    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box box-primary content-card">
          <div class="box-header card-header">
            <h3 class="box-title card-tools"><a class="label label-success action-btn action-btn-primary" @click.prevent="openEdit()"><span class="glyphicon glyphicon-plus"></span> 新增</a></h3>
          </div>
          <div class="box-body no-padding card-body">
            <div class="table-card"><table class="table table-hover table-striped"><tbody>
              <tr><th>ID</th><th>课程安排ID</th><th>星期</th><th>时间</th><th>地点</th><th>操作</th></tr>
              <tr v-for="s in settingList" :key="s.id">
                <td>{{ s.id }}</td><td>{{ s.courseId }}</td><td>{{ s.attendClassWeek }}</td><td>{{ s.startTime }} - {{ s.endTime }}</td><td>{{ s.address }}</td>
                <td>
                  <a class="label xiugai action-btn action-btn-info" @click.prevent="openEdit(s)"><span class="glyphicon glyphicon-edit"></span> 修改</a>
                  <a class="label shanchu action-btn action-btn-danger" @click.prevent="remove(s.id)"><span class="glyphicon glyphicon-remove"></span> 删除</a>
                </td>
              </tr>
            </tbody></table></div>
            <footer-paging :pageInfo="pageInfo"></footer-paging>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade in" id="editModal" data-backdrop="static">
      <div class="modal-dialog"><div class="modal-content"><div class="modal-body">
        <div class="form-group"><label>课程安排</label><select class="form-control" v-model="editing.courseId"><option v-for="c in courseManages" :key="c.id" :value="c.id">{{ c.courseName }}-{{ c.className }}</option></select></div>
        <div class="form-group"><label>上课周期</label><input class="form-control" v-model="editing.attendClassWeek"></div>
        <div class="form-group"><label>开始时间</label><input class="form-control" v-model="editing.startTime"></div>
        <div class="form-group"><label>结束时间</label><input class="form-control" v-model="editing.endTime"></div>
        <div class="form-group"><label>地点</label><input class="form-control" v-model="editing.address"></div>
        <button class="btn btn-primary" @click="save">保存</button>
      </div></div></div>
    </div>
  </div>
</template>
<script>
import FooterPaging from "../../components/FooterPaging.vue";
export default {
  components: { FooterPaging },
  props: ["pageNum"],
  data () { return { settingList: [], pageInfo: {}, courseManages: [], editing: {} }; },
  created () { this.load(); },
  watch: { $route () { this.load(); } },
  methods: {
    load () {
      this.axios.get("attendClassSetting/list", { params: { page: this.pageNum } }).then(res => {
        const p = res.data.settingList;
        this.settingList = p.list || [];
        this.pageInfo = { pages: p.pages, pageNum: p.pageNum, total: p.total, pageSize: p.pageSize, isFirstPage: p.isFirstPage, isLastPage: p.isLastPage, hasPreviosPage: p.hasPreviosPage, hasNextPage: p.hasNextPage };
      });
    },
    openEdit (row) {
      this.editing = row ? { ...row } : { delFlag: 0 };
      this.axios.get("attendClassSetting/edit", { params: { id: row ? row.id : null } }).then(res => {
        this.courseManages = res.data.courseManages || [];
        if (res.data.setting) this.editing = res.data.setting;
        window.$("#editModal").modal("show");
      });
    },
    save () {
      const fd = new FormData();
      Object.keys(this.editing).forEach(k => fd.append(k, this.editing[k]));
      this.axios.post("attendClassSetting/save", fd).then(res => { alert(res.data.msg); if (res.data.code === 0) { window.$("#editModal").modal("hide"); this.load(); } });
    },
    remove (id) { if (!confirm("确认删除?")) return; this.axios.delete("attendClassSetting/remove", { params: { id } }).then(res => { alert(res.data.msg); this.load(); }); }
  }
};
</script>
