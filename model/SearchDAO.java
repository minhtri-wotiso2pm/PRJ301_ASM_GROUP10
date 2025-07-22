package model;

import java.util.ArrayList;
import java.util.List;
import utils.AuthUtils;

public class SearchDAO {

    public List<SearchDTO> searchHotelWithDetail(String type,String hotelName, int adults,int rooms) {
        List<SearchDTO> result = new ArrayList<>();
        List<HotelDTO> hotelList = HotelDAO.getHotelsByNameOrCity(type,hotelName);

        for (HotelDTO h : hotelList) {
            List<RoomDTO> roomsInHotel = RoomDAO.getRoomsByHotelID(h.getHotelID());
            List<ServiceDTO> serviceInHotel = ServiceDAO.getServiceByHotelId(h.getHotelID());
            List<HotelImageDTO> imageInHotel = HotelImageDAO.getImageByHotelId(h.getHotelID());

            boolean isSuitable = RoomDAO.canAccommodate(roomsInHotel, rooms, adults);
            if (isSuitable) {
                result.add(new SearchDTO(h, serviceInHotel, roomsInHotel, imageInHotel));
            }
        }
        return result;
    }

    public SearchDTO displayHotelById(int hotelID) {
        HotelDTO hotel = HotelDAO.getHotelsById(hotelID);
        List<RoomDTO> roomsInHotel = RoomDAO.getRoomsByHotelID(hotelID);
        List<ServiceDTO> serviceInHotel = ServiceDAO.getServiceByHotelId(hotelID);
        List<HotelImageDTO> imageInHotel = HotelImageDAO.getImageByHotelId(hotelID);
        SearchDTO result = new SearchDTO(hotel, serviceInHotel, roomsInHotel, imageInHotel);
        return result;
    }

    public List<SearchDTO> getAllInfoHotel(String type) {
        List<SearchDTO> result = new ArrayList<>();
        List<HotelDTO> hotel = HotelDAO.getHotelsByType(type);
        for (HotelDTO h : hotel) {
            List<RoomDTO> roomsInHotel = RoomDAO.getRoomsByHotelID(h.getHotelID());
            List<ServiceDTO> serviceInHotel = ServiceDAO.getServiceByHotelId(h.getHotelID());
            List<HotelImageDTO> imageInHotel = HotelImageDAO.getImageByHotelId(h.getHotelID());
            result.add(new SearchDTO(h, serviceInHotel, roomsInHotel, imageInHotel));
        }
        return result;
    }
    
    public List<SearchDTO> setPriceHotel(String city) {
        List<SearchDTO> result = new ArrayList<>();
        List<HotelDTO> hotel = HotelDAO.getHotelByCity(city);
        for (HotelDTO h : hotel) {
            List<RoomDTO> roomsInHotel = RoomDAO.getRoomsByHotelID(h.getHotelID());
            List<ServiceDTO> serviceInHotel = ServiceDAO.getServiceByHotelId(h.getHotelID());
            List<HotelImageDTO> imageInHotel = HotelImageDAO.getImageByHotelId(h.getHotelID());
            result.add(new SearchDTO(h, serviceInHotel, roomsInHotel, imageInHotel));
        }
        return result;
    }

}
