package cn.njcit.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CourseManageSaveRequest {
    @Valid
    @NotNull(message = "课程安排参数不能为空")
    private CourseManagePayload courseManage;

    @Valid
    private List<AttendClassSettingPayload> attendClassSettingList = new ArrayList<>();

    public CourseManagePayload getCourseManage() {
        return courseManage;
    }

    public void setCourseManage(CourseManagePayload courseManage) {
        this.courseManage = courseManage;
    }

    public List<AttendClassSettingPayload> getAttendClassSettingList() {
        return attendClassSettingList;
    }

    public void setAttendClassSettingList(List<AttendClassSettingPayload> attendClassSettingList) {
        this.attendClassSettingList = attendClassSettingList;
    }

    public static class CourseManagePayload {
        private Long id;

        @NotBlank(message = "课程编号不能为空")
        private String courseNo;

        @NotBlank(message = "课程名称不能为空")
        private String courseName;

        @NotBlank(message = "教师名称不能为空")
        private String teacherName;

        private String teacherNo;
        private String classNo;

        @NotBlank(message = "班级名称不能为空")
        private String className;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCourseNo() {
            return courseNo;
        }

        public void setCourseNo(String courseNo) {
            this.courseNo = courseNo;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherNo() {
            return teacherNo;
        }

        public void setTeacherNo(String teacherNo) {
            this.teacherNo = teacherNo;
        }

        public String getClassNo() {
            return classNo;
        }

        public void setClassNo(String classNo) {
            this.classNo = classNo;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    public static class AttendClassSettingPayload {
        @NotBlank(message = "上课周期不能为空")
        private String attendClassWeek;

        @NotBlank(message = "上课时间不能为空")
        private String startTime;

        @NotBlank(message = "下课时间不能为空")
        private String endTime;

        @NotBlank(message = "教学地点不能为空")
        private String address;

        public String getAttendClassWeek() {
            return attendClassWeek;
        }

        public void setAttendClassWeek(String attendClassWeek) {
            this.attendClassWeek = attendClassWeek;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
