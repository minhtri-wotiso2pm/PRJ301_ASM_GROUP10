/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.jar.Attributes;

/**
 *
 * @author nzero
 */
public class UserDTO {
    private int userID;
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private String roleID;
    private String gender;
    private Date birthday;
    private String city;
    private boolean status;

    public UserDTO() {
    }

    public UserDTO(String fullName, String phone, String email, String password, String roleID,String gender,Date birthday,String city, boolean status) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.gender =  gender;
        this.birthday = birthday;
        this.city = city;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleID() {
        return roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getUserName(){
        String[] name = this.fullName.split("\\s+");
        String userName="";
        for(int i=0;i<name.length;i++){
            userName = name[0] + " " + name[name.length-1];
        }
        return userName;
    }
}
