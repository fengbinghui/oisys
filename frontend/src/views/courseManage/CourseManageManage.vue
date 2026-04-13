<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">课程教学管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt;
        <a href="">课程教学管理</a>
      </div>
    </div>

    <div id="courseManageManage"
         class="row">
      <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box box-primary content-card">
          <!--盒子头-->
          <div class="box-header card-header">
            <h3 class="box-title card-tools">
              <router-link to="/courseManage/edit"
                           class="label label-success action-btn action-btn-primary">
                <span class="glyphicon glyphicon-plus"></span>
                新增
              </router-link>
              <a href="#"
                 class="label label-success action-btn action-btn-primary"
                 @click.prevent="refreshList">
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
                      <th scope="col">教学安排编号</th>
                      <th scope="col">课程名称</th>
                      <th scope="col">授课老师</th>
                      <th scope="col">班级</th>
                      <th scope="col">上课时间</th>
                      <th scope="col">地点</th>
                      <th scope="col">操作</th>
                    </tr>
                    <tr v-if="courseManageList.length == 0">
                      <td class="text-empty">对不起，查询不到记录！</td>
                    </tr>
                    <tr v-for="coum in courseManageList"
                        :key="coum.id">
                      <td>
                        <span>{{ coum.courseNo }}</span>
                      </td>
                      <td>
                        <span>{{ coum.courseName }}</span>
                      </td>
                      <td>
                        <span>{{ coum.teacherName }}</span>
                      </td>
                      <td>
                        <span>{{ coum.className }}</span>
                      </td>
                      <td>
                        <span v-for="item in coum.attendClassSettings"
                              :key="item.id">
                          {{ item.attendClassWeek }}<br />
                          {{ item.startTime }}-{{ item.endTime }}
                        </span>
                      </td>
                      <td>
                        <span v-for="item in coum.attendClassSettings"
                              :key="item.id">
                          {{item.address}} <br>
                        </span>
                      </td>
                      <td>
                        <router-link :to="{
                          name: 'courseManageEdit',
                          params: {
                            courseManageId: coum.id
                          },
                        }"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span>
                          查看详情
                        </router-link>
                        <router-link :to="{
                          name: 'courseManageEdit',
                          params: {
                            courseManageId: coum.id
                          },
                        }"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span> 修改
                        </router-link>
                        <a class="label shanchu action-btn action-btn-danger">
                          <span class="glyphicon glyphicon-remove"
                                @click="deleteCourseManage(coum.id)"></span> 删除
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
      pageInfo: {}, //分页信息
      courseManageList: [], //课程列表
      keywords: "", //搜索关键字
    };
  },
  props: ["pageNum"], //接受路由器传递来的params
  methods: {
    getCourseManage () {
      //获得指定页的学生信息
      this.axios //发送请求时提供页号和搜索关键字
        .get("courseManage/list", {
          params: { page: this.pageNum, searchName: this.keywords },
        })
        .then((response) => {
          this.courseManageList = response.data.courseManageList.list;
          this.pageInfo = {
            pages: response.data.courseManageList.pages,
            pageNum: response.data.courseManageList.pageNum,
            total: response.data.courseManageList.total,
            pageSize: response.data.courseManageList.pageSize,
            isFirstPage: response.data.courseManageList.isFirstPage,
            isLastPage: response.data.courseManageList.isLastPage,
            hasPreviosPage: response.data.courseManageList.hasPreviosPage,
            hasNextPage: response.data.courseManageList.hasNextPage,
          };
        })
        .catch((error) => {
          console.log(error);
        });
    },
    search () {
      //搜索
      this.keywords = this.keywords == undefined ? "" : this.keywords;
      this.$router.push(`/courseManage/list/1/${this.keywords}`);//跳转到第一页
    },
    refreshList () {
      this.getCourseManage();
    },
    deleteCourseManage (courseManageId) {
      //按照id删除学生
      if (!confirm("删除该记录将不能恢复，确定要删除吗？")) {
        return; //询问用户，如果用户选择否，返回，什么事都不做
      }
      this.axios
        .delete("courseManage/delete", { params: { id: courseManageId } })
        .then((response) => {
          alert(response.data.msg); //提示消息
          this.getCourseManage();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created () {
    this.keywords = this.$route.params.keywords; //从路由中获取关键字
    this.getCourseManage(); //示例创建，调用获得第一页学生信息
    console.log(this.courseManageList);
  },
  watch: {
    $route () {
      //监听路由变化，路由发送变化再次调用
      this.getCourseManage();
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
