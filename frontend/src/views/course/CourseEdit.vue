<template>
  <div class="page-shell">
    <div class="row page-header">
      <div class="col-md-2">
        <h1 class="page-title">课程设置</h1>
      </div>
      <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a>
        &gt; <a disabled="disabled">课程设置</a>
      </div>
    </div>
    <div id="courseEdit"
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
                    <label class="control-label"><span>课程编号</span></label>
                    <input name="userName"
                           class="form-control"
                           v-model="course.courseNo" />
                  </div>
                  <div class="col-md-6 form-group">
                    <label class="control-label"><span>课程名称</span></label>
                    <input name="className"
                           class="form-control"
                           v-model="course.courseName"/>
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
      course: {},
    };
  },
  props: ["courseId"],//接受路由器参数
  created () {
    //示例创建后,取得请求参数的学号（如果是新增课程，学号是null）
    this.getCourseById();//调用getCourseById获得指定课程信息和班级列表
  },
  methods: {
    //按照学号获得课程信息，学号为null，只会得到列表，学号不为空，会得到课程信息和班级列表
    getCourseById () {
      this.axios
        .get("courseMan/edit", { params: { id: this.courseId } })
        .then((response) => {
          console.log(response)
          if (response.data.course) {
            //如果有返回值有课程信息，将得到课程的对象信息赋值给student
            this.course = response.data.course;
          }
        });
    },
    save (event) {//保存新课程信息、更新课程信息
      if (!checkEmpty(event.target)) {//验证表单是否有空项
        return;
      }
      let formData = new FormData();//创建FormData对象，加入表单的各项数据，最为post请求的提交数据
      if (this.course.id) {//如果有用户id，表示更新
        formData.append("id", this.course.id);//添加用户id
      }
      formData.append("courseNo", this.course.courseNo);
      formData.append("courseName", this.course.courseName);
      this.axios
        .post("courseMan/save", formData, {//提交save请求，携带formDate
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          alert(response.data.msg);//提示消息，后端传回msg
          if (response.data.code == 0) {
            this.$router.push("/course/list/1");//成功跳转到列表页面
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