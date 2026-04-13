package cn.njcit.dto;

public class LoginResult {
    private Long userId;
    private String realName;
    private String className;
    private Long roleId;
    private Integer superman;
    private String imgPath;
    private Object menus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getSuperman() {
        return superman;
    }

    public void setSuperman(Integer superman) {
        this.superman = superman;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Object getMenus() {
        return menus;
    }

    public void setMenus(Object menus) {
        this.menus = menus;
    }
}
