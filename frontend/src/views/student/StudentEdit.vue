<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">学生信息管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt; <a disabled="disabled">学生信息管理</a>
      </div>
    </div>
    <div id="studentEdit"
         class="row">
      <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box content-card">
          <!--盒子头-->
          <form enctype="multipart/form-data"
                @submit.prevent="save">
            <div class="box-header card-header">
              <h3 class="box-title card-tools">
                <a href="javascript:history.back();"
                   class="label label-default action-btn">
                  <i class="glyphicon glyphicon-chevron-left"></i>
                  <span>返回</span>
                </a>
              </h3>
            </div>
            <!--盒子身体-->
            <div class="box-body no-padding card-body">
              <div class="box-body">
                <div class="row">
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>学号</span></label>
                    <input name="userName"
                           class="form-control"
                           :readonly="student.userId"
                           v-model="student.userName" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>姓名</span></label>
                    <input name="realName"
                           class="form-control"
                           v-model="student.realName" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"> <span>性别</span> </label>
                    <select class="form-control"
                            name="sex"
                            v-model="student.sex">
                      <option value="男">男</option>
                      <option value="女">女</option>
                    </select>
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"> <span>班级</span></label>
                    <select class="classselect form-control"
                            name="classId"
                            v-model="student.classId">
                      <option value="">请选择</option>
                      <option v-for="clas in classes"
                              :key="clas.classId"
                              :value="clas.classId">
                        {{ clas.className }}</option>
                    </select>
                  </div>
                  <div class="col-md-6 form-group">
                    <span> <label class="control-label">入学日期</label> </span>
                    <input name="entranceDate"
                           class="form-control"
                           id="start"
                           onclick="WdatePicker()"
                           v-model="student.entranceDate" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>学籍状态</span></label>
                    <select class="form-control"
                            name="studentStatus"
                            v-model="student.studentStatus">
                      <option value="0">在籍</option>
                      <option value="1">不在籍</option>
                    </select>
                  </div>
                </div>

                <!--判断一下 请求参数的值  进行显示-->
                <div class="row">
                  <hr />
                  <div class="col-md-6">
                    <div v-show="student.userId || showPic">
                      <label class="control-label"><span>头像</span></label>
                      <div>
                        <img name="imageField"
                             :src="getPictureUrl()"
                             class="student-avatar-preview" />
                      </div>
                    </div>
                    <label class="control-label"><span>上传头像</span></label>
                    <div class="form-group">
                      <div>
                        <input type="file"
                               name="filePath"
                               class="w-full"
                               @change="previewPicture" />
                      </div>
                      <p class="help-block">
                        尺寸在512*512以内，大小在500KB以内
                      </p>
                    </div>
                  </div>
                  <div class="col-md-6"
                       v-if="student.userId">
                    <label class="control-label"><span>重置密码</span></label>
                    <br />
                    <span class="labels"><label><input name="resetPassword"
                               type="checkbox"
                               v-model="resetPassword" /><i>✓</i></label></span>
                  </div>
                </div>
              </div>
            </div>
            <!--盒子尾-->
            <div class="box-footer form-card-footer">
              <input class="btn btn-primary"
                     id="save"
                     type="submit"
                     value="保存" />
              <input class="btn btn-default"
                     id="cancel"
                     type="button"
                     value="取消"
                     onclick="history.back()" />
            </div>
          </form>
        </div>
      </div>
    </div>

    <div class="modal fade in"
         id="thismodal"
         data-backdrop="static">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body modal-success hidden-block">
            <div class="icon">
              <span class="glyphicon glyphicon-ok"></span>
            </div>
            <div class="modal-p">
              <h2>操作成功</h2>
              <div class="modal-p">
                <button type="button"
                        class="btn btn-primary successToUrl"
                        data-dismiss="modal">
                  确定
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      student: {
        //学生对象
        oiClass: {},//学生所属班级
        sex: "男",
        studentStatus: 0,//在籍
        classId: "",//班级号
        entranceDate: "",//入学时间
      },
      classes: [],//班级对象数组
      resetPassword: false,//是否重置密码(修改时)
      /*控制是否展示头像，新增页面初始化不显示头像(尚未上传)，用户选择了头像 后才展示出来*/
      showPic: false,
    };
  },
  props: ["studentId"],//接受路由器参数
  created () {
    //示例创建后,取得请求参数的学号（如果是新增学生，学号是null）
    this.getStudentById();//调用getStudentById获得指定学生信息和班级列表
  },
  methods: {
    //按照学号获得学生信息，学号为null，只会得到列表，学号不为空，会得到学生信息和班级列表
    getStudentById () {
      this.axios
        .get("studentManage/edit", { params: { userId: this.studentId } })
        .then((response) => {
          if (response.data.student) {
            //如果有返回值有学生信息，将得到学生的对象信息赋值给student
            this.student = response.data.student;
          }
          this.classes = response.data.classes;//返回值里的班级列表 设置到classes属性上
        });
    },
    getPictureUrl () {
      if (this.student.imgPath) {
        //修改学生信息，imgPath不能为空，拼接图片地址
        return `${this.axios.defaults.baseURL}/face/${this.student.imgPath}`;
      }
      return "";//新增学生信息时，imgPath为空，如果使用上面拼接地址，图片请求会404，所以新增是会返回“”为图片地址
    },
    previewPicture (event) {//选择头像时，刷新页面img标签中的图片
      let pic = window.URL.createObjectURL(event.target.files[0]);
      document.querySelector("img[name=imageField]").src = pic;
      this.showPic = true;//选择了图片，将showPIC改成true，img将展示出来)
    }, save (event) {//保存新学生信息、更新学生信息
      if (!checkEmpty(event.target)) {//验证表单是否有空项
        return;
      }
      let formData = new FormData();//创建FormData对象，加入表单的各项数据，最为post请求的提交数据
      let file = event.target.elements["filePath"].files[0];//获得用户提交的图片
      formData.append("filePath", file);//通过append向form添加数据
      if (this.student.userId) {//如果有用户id，表示更新
        formData.append("userId", this.student.userId);//添加用户id
      } else {
        this.resetPassword = true;//新增学生都重置密码
      }
      formData.append("classId", this.student.classId);
      formData.append("userName", this.student.userName);
      formData.append("realName", this.student.realName);
      formData.append("sex", this.student.sex);
      formData.append("studentStatus", this.student.studentStatus);
      formData.append("entranceDate", document.getElementById("start").value);
      formData.append("resetPassword", this.resetPassword);
      this.axios
        .post("studentManage/save", formData, {//提交save请求，携带formDate
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          alert(response.data.msg);//提示消息，后端传回msg
          if (response.data.code == 0) {
            this.$router.push("/student/list/1");//成功跳转到列表页面
          }
        })
        .catch((error) => {
          console.error(error);
        })
    }
  },

};
function checkEmpty (form) {
  //表达空验证
  for (let i = 0; i < form.elements.length; i++) {
    if (form.elements[i].value == "" && form.elements[i].name != "filePath") {//排除图片。可以不上传头像
      alert("表单不能为空！");
      return false;
    }
  }
  return true;
}
</script>
<style scoped>
a {
  color: black;
}

a:hover {
  text-decoration: none;
}

.bgc-w {
  background-color: #fff;
}

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

.student-avatar-preview {
  border-radius: 100px;
  height: 100px;
  width: 100px;
}
</style>