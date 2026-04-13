<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">学生信息管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a href=""><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt; <a disabled="disabled">学生信息管理</a>
      </div>
    </div>

    <div id="stuManage"
         class="row">
      <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box box-primary content-card">
          <!--盒子头-->
          <div class="box-header card-header">
            <h3 class="box-title card-tools">
              <router-link to="/student/edit"
                           class="label label-success action-btn action-btn-primary">
                <span class="glyphicon glyphicon-plus"></span>
                新增
              </router-link>
              <a href=""
                 class="label label-success action-btn action-btn-primary">
                <span class="glyphicon glyphicon-refresh"></span> 刷新
              </a>
            </h3>
            <div class="box-tools">
              <div class="input-group search-group">
                <input type="text"
                       class="form-control input-sm search"
                       id="search"
                       placeholder="请输入学生姓名"
                       v-model="keywords" />
                <div class="input-group-btn">
                  <a class="btn btn-sm btn-default"
                     @click.prevent="search">
                    <span class="glyphicon glyphicon-search usersearchgo"></span>
                  </a>
                </div>
              </div>
            </div>
          </div>
          <!--盒子身体-->
          <div class="box-body no-padding thistable card-body">
            <div class="box-body no-padding">
              <div class="table-responsive table-card">
                <table class="table table-hover table-striped">
                  <tbody>
                    <tr>
                      <th scope="col">学号</th>
                      <th scope="col">姓名</th>
                      <th scope="col">性别</th>
                      <th scope="col">年级</th>
                      <th scope="col">院系</th>
                      <th scope="col">班级</th>
                      <th scope="col">入学日期</th>
                      <th scope="col">学籍状态</th>
                      <th scope="col">操作</th>
                    </tr>
                    <tr v-if="studentList.length == 0">
                      <td class="text-empty">对不起，查询不到记录！</td>
                    </tr>
                    <tr v-for="stu in studentList"
                        :key="stu.userId">
                      <td>
                        <span>{{ stu.userName }}</span>
                      </td>
                      <td>
                        <span>{{ stu.realName }}</span>
                      </td>
                      <td>
                        <span>{{ stu.sex }}</span>
                      </td>
                      <td>
                        <span>{{ stu.oiClass.grade }}</span>
                      </td>
                      <td>
                        <span>{{ stu.oiClass.faculty }}</span>
                      </td>
                      <td>
                        <span>{{ stu.oiClass.className }}</span>
                      </td>
                      <td>
                        <span>{{ stu.entranceDate }}</span>
                      </td>
                      <td>
                        <span class="ml-sm"
                              :class="status(stu)">
                          {{ stu.studentStatus == 0 ? "在籍" : "不在籍" }}</span>
                      </td>
                      <td>
                        <router-link :to="{
                            name: 'studentEdit',
                            params: {
                              studentId: stu.userId,
                            },
                          }"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span> 修改
                        </router-link>
                        <a class="label shanchu action-btn action-btn-danger"
                           @click.prevent="deleteStudent(stu.userId)">
                          <span class="glyphicon glyphicon-remove"></span> 删除
                        </a>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
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
  components: {
    FooterPaging,
  },
  data () {
    return {
      studentList: [], //学生列表
      pageInfo: {}, //分页信息，传递给分页组件
      keywords: "", //查询关键字
    };
  },
  props: ["pageNum"], //接受路由器传递来的params
  methods: {
    getStudentList () {
      //获得指定页的学生信息
      this.axios //发送请求时提供页号和搜索关键字
        .get("studentManage/list", {
          params: { page: this.pageNum, searchName: this.keywords },
        })
        .then((response) => {
          this.studentList = response.data.studentList.list;

          this.pageInfo = {
            pages: response.data.studentList.pages,
            pageNum: response.data.studentList.pageNum,
            total: response.data.studentList.total,
            pageSize: response.data.studentList.pageSize,
            isFirstPage: response.data.studentList.isFirstPage,
            isLastPage: response.data.studentList.isLastPage,
            hasPreviosPage: response.data.studentList.hasPreviosPage,
            hasNextPage: response.data.studentList.hasNextPage,
          };
        })
        .catch((error) => {
          console.log(error);
        });
    },
    //根据学生在籍不在籍返回不同的class值
    status (stu) {
      return stu.studentStatus == 0 ? "label label-info" : "label label-danger";
    },
    search () {
      //搜索
      this.keywords = this.keywords == undefined ? "" : this.keywords;
      this.$router.push(`/student/list/1/${this.keywords}`);
    },
    deleteStudent (studentId) {
      //按照id删除学生
      if (!confirm("删除该记录将不能恢复，确定要删除吗？")) {
        return; //询问用户，如果用户选择否，返回，什么事都不做
      }
      this.axios
        .delete("studentManage/delete", { params: { userId: studentId } })
        .then((response) => {
          alert(response.data.msg); //提示消息
          //this.$router.push("/student/list/1");跳转回学生列表
          this.getStudentList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created () {
    this.keywords = this.$route.params.keywords; //从路由中获取关键字
    this.getStudentList(); //示例创建，调用获得第一页学生信息
  },
  watch: {
    $route () {
      //监听路由变化，路由发送变化再次调用
      this.getStudentList();
    },
  },
};
</script>

<style scoped>
#thismodal .modal-dialog {
  width: 500px;
  top: 20%;
}

#thismodal .modal-body .icon {
  height: 80px;
  width: 80px;
  margin: 20px auto;
  border-radius: 50%;
  color: #aad6aa;
  border: 3px solid #d9ead9;
  text-align: center;
  font-size: 44px;
}

#thismodal .modal-body .icon .glyphicon {
  font-size: 46px;
  top: 14px;
}

#thismodal .modal-p {
  margin: 20px auto;
}

#thismodal .modal-body .modal-p h2 {
  text-align: center;
}

#thismodal .modal-body .modal-p p {
  text-align: center;
  color: #666;
  font-size: 16px;
  padding-top: 8px;
  font-weight: 300;
}

#thismodal .modal-p .btn {
  margin-left: 40%;
  width: 100px;
  height: 40px;
}

#thismodal .modal-error .icon {
  color: #f27474;
  border: 3px solid #f27474;
}

a {
  color: black;
}

a:hover {
  text-decoration: none;
}

.bgc-w {
  background-color: #fff;
}
</style>
