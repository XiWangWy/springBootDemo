package com.bless.NewEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangxi on 18/7/2.
 */
@Data
public class NewUser implements Serializable{

    @Id
    private String id;

    @ApiModelProperty("用户名字")
    private String userName;
    @ApiModelProperty("用户密码")
    private String passWord;
    @ApiModelProperty("用户角色")
    private ArrayList<String> roles;

    public NewUser(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public NewUser(){
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", roles=" + roles +
                '}';
    }

}
