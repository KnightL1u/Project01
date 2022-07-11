package com.liu.project01.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author liuzhengwei
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    /**
     * 昵称
     */


    private String nickname;

    /**
     * 密码
     */
    private String password;

    private String slat;

    /**
     * 头像
     */
    private String head;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    private Integer loginCount;


}
