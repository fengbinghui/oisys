<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">班级信息管理</h1>
      </div>
      <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt; <a disabled="disabled">班级信息管理</a>
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
                    <label class="control-label"><span>班级编号</span></label>
                    <input name="userName"
                           class="form-control"
                           :readonly="oiclass.classId"
                           v-model="oiclass.classNo" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>班级名称</span></label>
                    <input name="className"
                           class="form-control"
                           v-model="oiclass.className" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>所在院系</span></label>
                    <input name="faculty"
                           class="form-control"
                           v-model="oiclass.faculty" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>年级</span></label>
                    <input name="grade"
                           class="form-control"
                           v-model="oiclass.grade" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>专业</span></label>
                    <input name="major"
                           class="form-control"
                           v-model="oiclass.major" />
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
      oiclass: {},
    };
  },
  props: ["classId"],//接受路由器参数
  created () {
    //示例创建后,取得请求参数的学号（如果是新增学生，学号是null）
    this.getClassById();//调用getStudentById获得指定学生信息和班级列表
  },
  methods: {
    //按照学号获得学生信息，学号为null，只会得到列表，学号不为空，会得到学生信息和班级列表
    getClassById () {
      this.axios
        .get("classManage/edit", { params: { classId: this.classId } })
        .then((response) => {
          console.log(response)
          if (response.data.oiClass) {
            //如果有返回值有学生信息，将得到学生的对象信息赋值给student
            this.oiclass = response.data.oiClass;
          }
        });
    },
    save (event) {//保存新学生信息、更新学生信息
      if (!checkEmpty(event.target)) {//验证表单是否有空项
        return;
      }
      let formData = new FormData();//创建FormData对象，加入表单的各项数据，最为post请求的提交数据
      if (this.oiclass.classId) {//如果有用户id，表示更新
        formData.append("classId", this.oiclass.classId);//添加用户id
      }
      formData.append("classNo", this.oiclass.classNo);
      formData.append("className", this.oiclass.className);
      formData.append("faculty", this.oiclass.faculty);
      formData.append("grade", this.oiclass.grade);
      formData.append("major", this.oiclass.major);
      this.axios
        .post("classManage/save", formData, {//提交save请求，携带formDate
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          alert(response.data.msg);//提示消息，后端传回msg
          if (response.data.code == 0) {
            this.$router.push("/class/list/1");//成功跳转到列表页面
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
    if (form.elements[i].value == "") {//排除图片。可以不上传头像
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
</style>