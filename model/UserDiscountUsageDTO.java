/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nzero
 */
public class UserDiscountUsageDTO {

    private String discountCode;
    private int userID;
    private int usageCount;

    public UserDiscountUsageDTO() {
    }

    public UserDiscountUsageDTO(String discountCode, int userID, int usageCount) {
        this.discountCode = discountCode;
        this.userID = userID;
        this.usageCount = usageCount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
