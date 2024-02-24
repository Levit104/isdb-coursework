package levit104.isdb.coursework.validation;


import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.models.order.Order;
import levit104.isdb.coursework.util.MyUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Order order = (Order) target;
        Repairman repairman = order.getRepairman();

        if (repairman == null)
            return;

        if (!MyUtils.dayInSchedule(order.getDate(), repairman.getDays()))
            errors.rejectValue("date", "", "Выбранный мастер не работает в этот день недели");
    }
}
