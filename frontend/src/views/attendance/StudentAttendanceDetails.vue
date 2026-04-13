<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-5">
        <h1 class="page-title">
          {{ userId }}同学{{ year }}年{{ month }}月考勤详情
        </h1>
      </div>
      <div class="col-md-7 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> >
        <a>考勤详情</a>
      </div>
    </div>

    <div id="attendList"
         class="row">
      <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box box-primary content-card">
          <!--盒子头-->
          <div class="box-header card-header">
            <h3 class="box-title card-tools">
              <router-link :to="`/attendance/monthList/${year}/${month}/${pageNum || 1}`"
                           class="label label-default action-btn">
                <i class="glyphicon glyphicon-chevron-left"></i>
                <span>返回</span>
              </router-link>
            </h3>
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
                      <th scope="col">授课老师</th>
                      <th scope="col">班级</th>
                      <th scope="col">打卡类型</th>
                      <th scope="col">打卡时间</th>
                      <th scope="col">打卡方式</th>
                      <th scope="col">地点</th>
                      <th scope="col">状态</th>
                    </tr>

                    <tr v-if="detailList.length === 0">
                      <td colspan="9" class="text-empty">暂无考勤明细</td>
                    </tr>
                    <tr v-for="item in detailList"
                        :key="`${item.attendsTime}-${item.courseNo}`">
                      <td>
                        <span>{{ item.courseNo || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.courseName || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.teacherName || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.className || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.typeName || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.attendsTime || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.clockTypeName || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.clockAddress || "-" }}</span>
                      </td>
                      <td>
                        <span>{{ item.statusName || "-" }}</span>
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
  props: ["userId", "year", "month", "pageNum"],
  components: {
    FooterPaging,
  },
  data () {
    return {
      pageInfo: {}, //分页信息
      detailList: [],
    };
  },
  created () {
    this.fetchDetails();
  },
  methods: {
    fetchDetails () {
      this.axios
        .get("attendance/details", {
          params: {
            userName: this.userId,
            searchDate: `${this.year}-${String(this.month).padStart(2, "0")}`,
            page: this.pageNum || 1,
          },
        })
        .then((response) => {
          this.pageInfo = response.data.detailList || {};
          this.detailList = (response.data.detailList && response.data.detailList.list) || [];
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  watch: {
    $route () {
      this.fetchDetails();
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
.ired {
  color: red;
}
.iblack {
  color: black;
}
</style>
