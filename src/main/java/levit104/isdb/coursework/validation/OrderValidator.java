package levit104.isdb.coursework.validation;


import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.models.Order;
import levit104.isdb.coursework.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class OrderValidator implements Validator {
    private final OrdersService ordersService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Order order = (Order) target;
        Repairman repairman = order.getRepairman();

        boolean orderExists = ordersService.existsByClientAndApplianceAndActive(order.getClient(), order.getAppliance(), true);
        if (orderExists)
            errors.rejectValue("appliance", "", ErrorMessages.ORDER_FOR_APPLIANCE_EXISTS);

        if (order.getDate() != null) {
            if (order.getDate().isBefore(LocalDate.now()))
                errors.rejectValue("date", "", ErrorMessages.INVALID_ORDER_DATE);

            if (repairman != null && !dayInSchedule(order.getDate(), repairman.getDays()))
                errors.rejectValue("date", "",
                        ErrorMessages.REPAIRMAN_NOT_WORKING_DAY + " (" + repairman.scheduleString() + ")");
        }
    }

    private boolean dayInSchedule(LocalDate date, List<Day> schedule) {
        String dateDayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return schedule.stream().anyMatch(day -> day.getFullName().equalsIgnoreCase(dateDayOfWeek));
    }
}
