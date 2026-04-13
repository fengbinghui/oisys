<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">课程教学管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a><span class="glyphicon glyphicon-home"></span> 首页</a>
        >
        <a>{{ isEdit ? "修改课程教学安排" : "新增课程教学安排" }}</a>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div class="bgc-w box content-card">
          <div class="panel-body card-body">
            <div class="row form-flex">
              <div class="col">
                <div class="input-group form-sty">
                  <span class="input-group-addon">课程</span>
                  <select class="form-control" v-model="courseManage.courseNo" @change="onCourseChange">
                    <option value="">请选择</option>
                    <option v-for="item in courseList" :key="item.id" :value="item.courseNo">
                      {{ item.courseNo }} - {{ item.courseName }}
                    </option>
                  </select>
                </div>
              </div>
              <div class="col">
                <div class="input-group form-sty">
                  <span class="input-group-addon">课程名称</span>
                  <input class="form-control" v-model="courseManage.courseName" readonly />
                </div>
              </div>
            </div>

            <div class="row form-flex">
              <div class="col">
                <div class="input-group form-sty">
                  <span class="input-group-addon">授课老师</span>
                  <input class="form-control" v-model.trim="courseManage.teacherName" placeholder="请输入授课老师姓名" />
                </div>
              </div>
              <div class="col">
                <div class="input-group form-sty">
                  <span class="input-group-addon">班级</span>
                  <select class="form-control" v-model="selectedClassId" @change="onClassChange">
                    <option value="">请选择</option>
                    <option v-for="item in classList" :key="item.classId" :value="item.classId">
                      {{ item.classNo }} - {{ item.className }}
                    </option>
                  </select>
                </div>
              </div>
            </div>

            <div v-for="(setting, idx) in attendClassSettingList" :key="idx" class="form-group form-background">
              <div class="row form-flex">
                <div class="col">
                  <div class="input-group form-sty">
                    <span class="input-group-addon">上课周期</span>
                    <select class="form-control" v-model="setting.attendClassWeek">
                      <option value="">请选择</option>
                      <option v-for="item in weeks" :key="item.value" :value="item.value">{{ item.name }}</option>
                    </select>
                  </div>
                </div>
                <div class="col text-right">
                  <button type="button" class="btn btn-danger btn-sm" @click="removeSetting(idx)">删除</button>
                </div>
              </div>
              <div class="row form-flex">
                <div class="col">
                  <div class="input-group form-sty">
                    <span class="input-group-addon">上课时间</span>
                    <input class="form-control" type="time" v-model="setting.startTime" />
                  </div>
                </div>
                <div class="col">
                  <div class="input-group form-sty">
                    <span class="input-group-addon">下课时间</span>
                    <input class="form-control" type="time" v-model="setting.endTime" />
                  </div>
                </div>
              </div>
              <div class="row form-flex">
                <div class="col w-full">
                  <div class="input-group form-sty">
                    <span class="input-group-addon">教学地点</span>
                    <input class="form-control" v-model.trim="setting.address" placeholder="请输入教学地点" />
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <button type="button" class="btn btn-default w-100" @click="addSetting">
                <span class="glyphicon glyphicon-plus"></span> 增加上课设置
              </button>
            </div>

            <div class="box-footer form-card-footer form-card-footer-right">
              <button class="btn btn-primary" type="button" @click="save">确定</button>
              <button class="btn btn-default" type="button" @click="goBack">返回</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["courseManageId"],
  data () {
    return {
      weeks: [
        { name: "星期一", value: "周一" },
        { name: "星期二", value: "周二" },
        { name: "星期三", value: "周三" },
        { name: "星期四", value: "周四" },
        { name: "星期五", value: "周五" },
        { name: "星期六", value: "周六" },
        { name: "星期日", value: "周日" },
      ],
      courseList: [],
      classList: [],
      selectedClassId: "",
      courseManage: {
        id: null,
        courseNo: "",
        courseName: "",
        teacherName: "",
        classNo: "",
        className: "",
      },
      attendClassSettingList: [],
    };
  },
  computed: {
    isEdit () {
      return !!this.courseManageId;
    },
  },
  created () {
    this.loadData();
  },
  methods: {
    emptySetting () {
      return { attendClassWeek: "", startTime: "", endTime: "", address: "" };
    },
    loadData () {
      this.axios.get("courseManage/edit", { params: { id: this.courseManageId } }).then((response) => {
        this.courseList = response.data.courseList || [];
        this.classList = response.data.classList || [];
        if (response.data.courseManage) {
          this.courseManage = { ...this.courseManage, ...response.data.courseManage };
          const matchedClass = this.classList.find((item) => item.className === this.courseManage.className);
          this.selectedClassId = matchedClass ? matchedClass.classId : "";
        }
        this.attendClassSettingList = (response.data.attendClassSettingList || []).map((item) => ({
          attendClassWeek: item.attendClassWeek || "",
          startTime: item.startTime || "",
          endTime: item.endTime || "",
          address: item.address || "",
        }));
        if (this.attendClassSettingList.length === 0) {
          this.attendClassSettingList.push(this.emptySetting());
        }
      });
    },
    onCourseChange () {
      const selected = this.courseList.find((item) => item.courseNo === this.courseManage.courseNo);
      if (!selected) {
        this.courseManage.courseName = "";
        return;
      }
      this.courseManage.courseName = selected.courseName;
    },
    onClassChange () {
      const selected = this.classList.find((item) => item.classId === this.selectedClassId);
      if (!selected) {
        this.courseManage.classNo = "";
        this.courseManage.className = "";
        return;
      }
      this.courseManage.classNo = selected.classNo;
      this.courseManage.className = selected.className;
    },
    addSetting () {
      this.attendClassSettingList.push(this.emptySetting());
    },
    removeSetting (idx) {
      if (this.attendClassSettingList.length === 1) {
        alert("至少保留一条上课设置");
        return;
      }
      this.attendClassSettingList.splice(idx, 1);
    },
    validateData () {
      if (!this.courseManage.courseNo || !this.courseManage.courseName || !this.courseManage.teacherName || !this.courseManage.className) {
        alert("请先完整填写课程基本信息");
        return false;
      }
      const invalid = this.attendClassSettingList.some((item) => !item.attendClassWeek || !item.startTime || !item.endTime || !item.address);
      if (invalid) {
        alert("上课设置不能有空项");
        return false;
      }
      return true;
    },
    save () {
      if (!this.validateData()) {
        return;
      }
      this.axios.post("courseManage/save", {
        courseManage: this.courseManage,
        attendClassSettingList: this.attendClassSettingList,
      }).then((response) => {
        alert(response.data.msg);
        if (response.data.code === 0) {
          this.$router.push("/courseManage/list/1");
        }
      }).catch((error) => {
        console.error(error);
      });
    },
    goBack () {
      this.$router.back();
    },
  },
};
</script>
<style scoped>
.form-flex {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.row {
  width: 100%;
  margin: 0 0 10px 0;
}

.col {
  width: 49%;
  display: flex;
}

.input-group {
  width: 100%;
}

.form-sty > .input-group-addon {
  width: 100px;
}

.w-100 {
  width: 100%;
}

.form-background {
  background-color: #f5f5f5;
  padding: 12px;
}
</style>