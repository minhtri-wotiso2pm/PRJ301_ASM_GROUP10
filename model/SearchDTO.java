/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author nzero
 */
public class SearchDTO {
    private HotelDTO hotel;
    private List<ServiceDTO> serviceList;
    private List<RoomDTO> roomList;
    private List<HotelImageDTO> imageList;

    public SearchDTO() {
    }

    public SearchDTO(HotelDTO hotel, List<ServiceDTO> serviceList, List<RoomDTO> roomList,List<HotelImageDTO>imageList) {
        this.hotel = hotel;
        this.serviceList = serviceList;
        this.roomList = roomList;
        this.imageList = imageList;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public List<ServiceDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public List<RoomDTO> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomDTO> roomList) {
        this.roomList = roomList;
    }

    public List<HotelImageDTO> getImageList() {
        return imageList;
    }

    public void setImageList(List<HotelImageDTO> imageList) {
        this.imageList = imageList;
    }
    
}
