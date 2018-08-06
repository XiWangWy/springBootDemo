package com.bless.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by wangxi on 18/7/2.
 */
@Data
public class User implements Serializable{

    @Id
    private String id;

    @ApiModelProperty("用户名字")
    private String userName;
    @ApiModelProperty("用户密码")
    private String passWord;

    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public User(){
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
