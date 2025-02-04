package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user, String code);
}
