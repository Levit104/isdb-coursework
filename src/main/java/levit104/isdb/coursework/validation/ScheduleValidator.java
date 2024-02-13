package levit104.isdb.coursework.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.List;

import static levit104.isdb.coursework.validation.ErrorMessages.EMPTY_SCHEDULE;

@Component
public class ScheduleValidator {
    public void validate(List<Integer> selectedDaysIds, Errors errors) {
        if (selectedDaysIds == null) errors.rejectValue("days", "", EMPTY_SCHEDULE);
    }
}
