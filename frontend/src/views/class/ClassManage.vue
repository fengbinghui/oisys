<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">班级信息管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a href=""><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt; <a disabled="disabled">班级信息管理</a>
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
              <router-link to="/class/edit"
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
                       placeholder="请输入班级编号或名称"
                       v-model="keywords" />
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
                      <th scope="col">班级编号</th>
                      <th scope="col">班级名称</th>
                      <th scope="col">所在院系</th>
                      <th scope="col">名称</th>
                      <th scope="col">专业</th>
                      <th scope="col">操作</th>
                    </tr>
                    <tr v-if="classList.length == 0">
                      <td class="text-empty">
                        对不起，查询不到记录！
                      </td>
                    </tr>
                    <tr v-else v-for="stu in classList"
                        :key="stu.classId">
                      <td>
                        <span>{{ stu.classNo }}</span>
                      </td>
                      <td>
                        <span>{{ stu.className }}</span>
                      </td>
                      <td>
                        <span>{{ stu.faculty }}</span>
                      </td>
                      <td>
                        <span>{{ stu.grade }}</span>
                      </td>
                      <td>
                        <span>{{ stu.major }}</span>
                      </td>
                      <td>
                        <router-link :to="{
                          name: 'classEdit',
                          params: {
                            classId: stu.classId,
                          },
                        }"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span> 修改
                        </router-link>
                        <a class="label shanchu action-btn action-btn-danger"
                           @click.prevent="deleteClass(stu.classId)">
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
      classList: [],//班级列表
      pageInfo: {}, //分页信息，传递给分页组件
      keywords: "",//查询关键字
    };
  },
  props: ["pageNum"],//接受路由器传递来的params
  methods: {
    getClassList () {//获得指定页的学生信息
      this.axios//发送请求时提供页号和搜索关键字
        .get("classManage/list", {
          params: { page: this.pageNum, searchName: this.keywords },
        })
        .then((response) => {
          this.classList = response.data.classList.list;

          this.pageInfo = {
            pages: response.data.classList.pages,
            pageNum: response.data.classList.pageNum,
            total: response.data.classList.total,
            pageSize: response.data.classList.pageSize,
            isFirstPage: response.data.classList.isFirstPage,
            isLastPage: response.data.classList.isLastPage,
            hasPreviosPage: response.data.classList.hasPreviosPage,
            hasNextPage: response.data.classList.hasNextPage,
          };
        })
        .catch((error) => {
          console.log(error);
        });
    },
    search () {//搜索
      this.keywords = this.keywords == undefined ? "" : this.keywords;
      this.$router.push(`/class/list/1/${this.keywords}`);
    },
    deleteClass (classId) { //按照id删除班级信息
      if (!confirm("删除该记录将不能恢复，确定要删除吗？")) {
        return;//询问用户，如果用户选择否，返回，什么事都不做
      }
      this.axios
        .delete("classManage/remove", { params: { classId: classId } })
        .then((response) => {
          alert(response.data.msg);//提示消息
          this.getClassList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created () {
    this.keywords = this.$route.params.keywords;//从路由中获取关键字
    this.getClassList();//示例创建，调用获得第一页学生信息
  },
  watch: {
    $route () {
      //监听路由变化，路由发送变化再次调用
      this.getClassList();
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
