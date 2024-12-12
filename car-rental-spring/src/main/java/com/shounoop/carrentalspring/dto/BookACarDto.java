package com.shounoop.carrentalspring.dto;

import com.shounoop.carrentalspring.entity.BookACar;
import com.shounoop.carrentalspring.enums.BookCarStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookACarDto {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private Long carId;
    private Long userId;
    private String username;
    private String email;
    private String carName; // Added field for car name

    public static BookACarDto fromEntity(BookACar booking) {
        BookACarDto dto = new BookACarDto();
        dto.setId(booking.getId());
        dto.setBookCarStatus(booking.getBookCarStatus());
        dto.setCarName(booking.getCar().getName()); // Populate carName
        dto.setPrice(booking.getCar().getPrice());  // Populate car price
        dto.setFromDate(booking.getFromDate());
        dto.setToDate(booking.getToDate());
        return dto;
    }
}
