package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);


}
