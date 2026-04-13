<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-4">
        <h1 class="page-title">
          考勤月报表 {{ year }}年{{ month }}月
        </h1>
      </div>
      <div class="col-md-8 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> >
        <a>考勥月报表</a>
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
              <a class="label label-success action-btn btn-month-switch"
                 @click="goToPreviousMonth">
                <span class="glyphicon glyphicon-chevron-left"></span>
                上一月
              </a>
              <a class="label label-success action-btn btn-month-switch"
                 @click="goToNextMonth">
                <span class="glyphicon glyphicon-chevron-right"></span>
                下一月
              </a>

              <span class="month-indicator">
                当前月份：{{ year }}-{{ month }}
              </span>
            </h3>
            <div class="box-tools">
              <div class="input-group search-group">
                <input type="text"
                       class="form-control input-sm search"
                       id="search"
                       placeholder="请输入姓名"
                       v-model.trim="localKeywords"
                       @keyup.enter="search" />
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
                    <tr v-if="displayedList.length === 0">
                      <td colspan="11" class="text-empty">对不起，查询不到记录！</td>
                    </tr>
                    <tr v-for="item in displayedList"
                        :key="item.user_name">
                      <td>
                        <span>{{ item.user_name }}</span>
                      </td>
                      <td>
                        <span>{{ item.real_name }}</span>
                      </td>
                      <td>
                        <span>{{ item.class_name }}</span>
                      </td>
                      <td>
                        <span>{{ item.normal }}</span>
                      </td>
                      <td>
                        <span>{{ item.late }}</span>
                      </td>
                      <td>
                        <span>{{ item.early_leave }}</span>
                      </td>
                      <td>
                        <span>{{ item.leave }}</span>
                      </td>
                      <td>
                        <span>{{ item.absent }}</span>
                      </td>
                      <td>
                        <span>{{ item.makeup }}</span>
                      </td>
                      <td>
                        <span>{{ item.missingCard }}</span>
                      </td>
                      <td>
                        <router-link :to="`/attendance/attendanceDetails/${item.user_name}/${year}/${month}/${pageNum}`"
                                     class="label xiugai action-btn action-btn-info">
                          <span class="glyphicon glyphicon-edit"></span>
                          查看详情
                        </router-link>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <footer-paging :pageInfo="pageInfo" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FooterPaging from "../../components/FooterPaging.vue";

export default {
  props: ["year", "month", "pageNum", "keywords"],
  components: {
    FooterPaging,
  },
  data () {
    return {
      pageInfo: {},
      classList: [],
      localKeywords: "",
    };
  },
  created () {
    this.localKeywords = this.keywords || "";
    this.fetchData();
  },
  computed: {
    displayedList () {
      if (!this.localKeywords) {
        return this.classList;
      }
      return this.classList.filter((item) =>
        (item.real_name || "").includes(this.localKeywords)
      );
    },
  },
  methods: {
    normalizeMonth (monthValue) {
      return String(monthValue).padStart(2, "0");
    },
    goToPreviousMonth () {
      let targetYear = Number(this.year);
      let targetMonth = Number(this.month);
      if (targetMonth === 1) {
        targetYear -= 1;
        targetMonth = 12;
      } else {
        targetMonth -= 1;
      }
      this.$router.push(`/attendance/monthList/${targetYear}/${this.normalizeMonth(targetMonth)}/1/${this.localKeywords || ""}`);
    },
    goToNextMonth () {
      let targetYear = Number(this.year);
      let targetMonth = Number(this.month);
      if (targetMonth === 12) {
        targetYear += 1;
        targetMonth = 1;
      } else {
        targetMonth += 1;
      }
      this.$router.push(`/attendance/monthList/${targetYear}/${this.normalizeMonth(targetMonth)}/1/${this.localKeywords || ""}`);
    },
    fetchData () {
      const queryMonth = this.normalizeMonth(this.month);
      this.axios
        .get("attendance/list", {
          params: {
            searchDate: `${this.year}-${queryMonth}`,
            page: this.pageNum || 1,
          },
        })
        .then((response) => {
          this.pageInfo = response.data.classList || {};
          this.classList = (response.data.classList && response.data.classList.list) || [];
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    search () {
      this.$router.push(`/attendance/monthList/${this.year}/${this.normalizeMonth(this.month)}/1/${this.localKeywords || ""}`);
    },
  },
  watch: {
    $route () {
      this.localKeywords = this.$route.params.keywords || "";
      this.fetchData();
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
