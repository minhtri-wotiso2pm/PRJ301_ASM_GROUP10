/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class ReviewsDAO {

    private static final String GET_REVIEW_BY_HOTEL_ID = "SELECT r.review_id,r.hotelID,r.userID,r.rating,r.comment,r.review_date FROM tblReviews r join tblHotels h on r.hotelID=h.hotelID join tblUsers u on r.userID = u.userID where h.hotelID=?";

    public List<ReviewsDTO> getReviewHotel(int hotelId) {
        List<ReviewsDTO> review = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_REVIEW_BY_HOTEL_ID);
            pr.setInt(1, hotelId);
            rs = pr.executeQuery();
            while (rs.next()) {
                ReviewsDTO rDTO = new ReviewsDTO();
                rDTO.setReview_id(rs.getInt("review_id"));
                rDTO.setHotelID(rs.getInt("hotelID"));
                rDTO.setUserID(rs.getInt("userID"));
                rDTO.setRating(rs.getDouble("rating"));
                rDTO.setComment(rs.getString("comment"));
                rDTO.setDATE(rs.getDate("review_date"));

                review.add(rDTO);
            }
        } catch (Exception e) {
            System.out.println("Error getReviewHotel:" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return review;
    }

    private static void closeResources(Connection conn, PreparedStatement pr, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            } else if (pr != null) {
                pr.close();
            } else {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error closeResources:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
