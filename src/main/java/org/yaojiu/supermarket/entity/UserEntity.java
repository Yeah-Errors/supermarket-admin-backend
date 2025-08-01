package org.yaojiu.supermarket.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static org.yaojiu.supermarket.utils.SecurityUtil.hash;

@Data
@TableName("user")
public class UserEntity {
    @TableId
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private Integer userType;
    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setUserType(userType);
        return userDTO;
    }
    public UserEntity desPwd(){
        this.password = hash(password);
        return this;
    }
}
