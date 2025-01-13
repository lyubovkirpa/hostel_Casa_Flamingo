package ait.cohort49.hostel_casa_flamingo.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest {

    private Long id;
    private String first_name;
    private String last_name;
    private String date_of_birthday;
    private String email;
    private String tel;


    @Override
    public String toString() {
        return String.format("Guest: id - %d, first_name - %s, last_name - %s, date_of_birthday - %s, email - %s, tel - %s",
                id, first_name, last_name, date_of_birthday, email, tel);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birthday() {
        return date_of_birthday;
    }

    public void setDate_of_birthday(String date_of_birthday) {
        this.date_of_birthday = date_of_birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id) && Objects.equals(first_name, guest.first_name) && Objects.equals(last_name, guest.last_name) && Objects.equals(date_of_birthday, guest.date_of_birthday) && Objects.equals(email, guest.email) && Objects.equals(tel, guest.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, date_of_birthday, email, tel);
    }
}
