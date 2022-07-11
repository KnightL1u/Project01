package com.liu.project01.vo;//@date :2022/4/25 16:05

import com.liu.project01.Validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {
    @NotNull
    @Length(min = 11, max = 11)
    @IsMobile
    private String mobile;

    @NotNull
    private String password;
}
