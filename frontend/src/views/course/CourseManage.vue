<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">课程设置</h1>
      </div>
      <div class="col-md-10 text-right">
        <a><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt;
        <a href="">课程设置</a>
      </div>
    </div>

    <div id="courseManage"
         class="row">
      <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box box-primary content-card">
          <!--盒子头-->
          <div class="box-header card-header">
            <h3 class="box-title card-tools">
              <router-link to="/course/edit"
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
                       placeholder="请输入课程名" 
                       v-model="keywords"/>
                <div class="input-group-btn">
                  <a class="btn btn-sm btn-default"
                     @click="search">
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
                      <th scope="col">课程编号</th>
                      <th scope="col">课程名称</th>
                      <th scope="col">操作</th>
                    </tr>
                    <tr v-if="courseList.length == 0">
                      <td class="text-empty">对不起，查询不到记录！</td>
                    </tr>
                    <tr v-for="cou in courseList"
                        :key="cou.id">
                      <td>
                        <span>{{ cou.courseNo }}</span>
                      </td>
                      <td>
                        <span>{{ cou.courseName }}</span>
                      </td>
                      <td>
                        <router-link :to="{
                          name: 'courseEdit',
                          params: {
                            courseId: cou.id
                          },
                        }"
                                     class="label xinangqing action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span>
                          查看详情
                        </router-link>
                        <router-link :to="{
                          name: 'courseEdit',
                          params: {
                            courseId: cou.id
                          },
                        }"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span> 修改
                        </router-link>
                        <a class="label shanchu action-btn action-btn-danger"
                           @click="deleteCourse(cou.id)">
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
      courseList: [], //课程列表
      pageInfo: {}, //分页信息，传递给分页组件
      keywords: "", //查询关键字
    };
  },
  props: ["pageNum"],//接受路由器传递来的params
  methods: {
    getCourseList () {
      //获得指定页的学生信息
      this.axios //发送请求时提供页号和搜索关键字
        .get("courseMan/list", {
          params: { page: this.pageNum, searchName: this.keywords },
        })
        .then((response) => {
          this.courseList = response.data.courseList.list;

          this.pageInfo = {
            pages: response.data.courseList.pages,
            pageNum: response.data.courseList.pageNum,
            total: response.data.courseList.total,
            pageSize: response.data.courseList.pageSize,
            isFirstPage: response.data.courseList.isFirstPage,
            isLastPage: response.data.courseList.isLastPage,
            hasPreviousPage: response.data.courseList.hasPreviousPage,
            hasNextPage: response.data.courseList.hasNextPage,
          };
        })
        .catch((error) => {
          console.log(error);
        });
    },
    search () {
      //搜索
      this.keywords = this.keywords == undefined ? "" : this.keywords;
      this.$router.push(`/course/list/1/${this.keywords}`);//跳转到第一页
    },
    deleteCourse (courseId) {
      //按照id删除课程
      if (!confirm("删除记录将永远不能恢复,确认删除吗？")) {
        return;
      }
      this.axios.delete("courseMan/delete", { params: { id: courseId } })
        .then(res => {
          alert(res.data.msg)//删除课程后弹出提示
          this.getCourseList();//重新获取课程列表
        })
        .catch(err => {
          console.log(err);//打印错误信息
        });
    },
  },
  created () {
    this.keywords = this.$route.params.keywords; //从路由中获取关键字
    this.getCourseList(); //示例创建，调用获得第一页学生信息
    console.log(this.courseList);
  },
  watch: {
    $route () {
      //监听路由变化，路由发送变化再次调用
      this.getCourseList();
      console.log(this.courseList);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
a {
  cursor: pointer;
}
</style>
