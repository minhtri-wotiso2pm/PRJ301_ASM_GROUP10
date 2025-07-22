/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author nzero
 */
public class DiscountDTO {

    private String discountCode;
    private String discountTitle;
    private String description;
    private double discountPercent;
    private int maxDiscount;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    private double minBookingAmount;
    private int usageLimit;

    public DiscountDTO() {
    }

    public DiscountDTO(String discountCode, String discountTitle, String description, double discountPercent, int maxDiscount, Date startDate, Date endDate, boolean isActive, double minBookingAmount, int usageLimit) {
        this.discountCode = discountCode;
        this.discountTitle = discountTitle;
        this.description = description;
        this.discountPercent = discountPercent;
        this.maxDiscount = maxDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.minBookingAmount = minBookingAmount;
        this.usageLimit = usageLimit;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountTitle() {
        return discountTitle;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public double getMinBookingAmount() {
        return minBookingAmount;
    }

    public void setMinBookingAmount(double minBookingAmount) {
        this.minBookingAmount = minBookingAmount;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }
}
