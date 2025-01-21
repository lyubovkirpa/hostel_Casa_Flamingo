package ait.cohort49.hostel_casa_flamingo.model.entity;

import java.util.Date;

public class Booking {

    private Long id;
    private String date_of_entry;     // дата въезда
    private String departure_date;    // дата выезда
    private Bed bed;                  // кровать, которую забронировали
    private User user;              // гость, который сделал бронирование
    private String bookingStatus;     // статус бронирования
    private Date bookingDate;         // дата создания бронирования

}
