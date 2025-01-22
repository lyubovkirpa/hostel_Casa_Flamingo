package ait.cohort49.hostel_casa_flamingo.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> errors = new ArrayList<>();

    public List<Violation> getErrors() {
        return errors;
    }

    public void setErrors(List<Violation> errors) {
        this.errors = errors;
    }
}

