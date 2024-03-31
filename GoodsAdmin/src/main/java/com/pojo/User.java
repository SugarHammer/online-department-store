package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("user")
public class User {

    private int userId;
    private String username;
    private String password;
    private String picUrl;
    private String address;
    private String phone;
    private String email;

}