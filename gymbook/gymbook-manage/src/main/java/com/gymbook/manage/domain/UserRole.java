package com.gymbook.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gymbook.common.annotation.Excel;
import com.gymbook.common.core.domain.BaseEntity;

/**
 * 用户和角色关联对象 sys_user_role
 * 
 * @author wooyoungnauh
 * @date 2025-02-21
 */
public class UserRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 角色ID */
    @Excel(name = "角色ID")
    private Long roleId;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRoleId(Long roleId) 
    {
        this.roleId = roleId;
    }

    public Long getRoleId() 
    {
        return roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("roleId", getRoleId())
            .toString();
    }
}
