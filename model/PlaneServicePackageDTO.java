/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class PlaneServicePackageDTO {
     private int packageId;
    private String ticketClass;
    private String baggageAllowance;
    private String mealOption;
    private float extraPrice;
    private String serviceName;

    public PlaneServicePackageDTO() {
    }

    public PlaneServicePackageDTO(int packageId, String ticketClass, String baggageAllowance, String mealOption, float extraPrice, String serviceName) {
        this.packageId = packageId;
        this.ticketClass = ticketClass;
        this.baggageAllowance = baggageAllowance;
        this.mealOption = mealOption;
        this.extraPrice = extraPrice;
        this.serviceName = serviceName;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public String getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(String baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public String getMealOption() {
        return mealOption;
    }

    public void setMealOption(String mealOption) {
        this.mealOption = mealOption;
    }

    public float getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(float extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    
}
