package com.gymbook.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 用户信息对象 sys_user
 * 
 * @author wooyoungnauh
 * @date 2025-02-20
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

    /** 用户类型（00系统用户） */
    private String userType;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    private String delFlag;

    /** 最后登录IP */
    @Excel(name = "最后登录IP")
    private String loginIp;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

    // 角色信息
    private Long roleId;
    private String roleName;

    private Role role;

    private UserRole userRole;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }
    public Long getUserId() 
    {
        return userId;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }
    public Long getDeptId() 
    {
        return deptId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }
    public String getUserName() 
    {
        return userName;
    }

    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }
    public String getNickName() 
    {
        return nickName;
    }

    public void setUserType(String userType) 
    {
        this.userType = userType;
    }
    public String getUserType() 
    {
        return userType;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }
    public String getEmail() 
    {
        return email;
    }

    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }
    public String getPhonenumber() 
    {
        return phonenumber;
    }

    public void setSex(String sex) 
    {
        this.sex = sex;
    }
    public String getSex() 
    {
        return sex;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }
    public String getAvatar() 
    {
        return avatar;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
    public String getPassword() 
    {
        return password;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }
    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }
    public String getDelFlag() 
    {
        return delFlag;
    }

    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }
    public String getLoginIp() 
    {
        return loginIp;
    }

    public void setLoginDate(Date loginDate) 
    {
        this.loginDate = loginDate;
    }
    public Date getLoginDate() 
    {
        return loginDate;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }
    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    public String getRoleName()
    {
        return roleName;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
    public Role getRole()
    {
        return role;
    }

    public void setUserRole(UserRole userRole)
    {
        this.userRole = userRole;
    }
    public UserRole getUserRole()
    {
        return userRole;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("userType", getUserType())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .toString();
    }
}
