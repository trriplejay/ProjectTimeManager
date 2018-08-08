package de.lgblaumeiser.ptm.cli.engine;

import com.google.common.collect.Iterables;
import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PrettyPrinter {
    private CommandLogger logger;

    public PrettyPrinter setLogger(CommandLogger logger) {
        this.logger = logger;
        return this;
    }

    public void tablePrint(Collection<Collection<Object>> data) {
        List<Integer> sizelist = newArrayList();
        for (Collection<Object> line : data) {

            int index = 0;
            for (Object field : line) {
                setToList(sizelist, index, Math.max(Iterables.get(sizelist, index, 0), field.toString().length()));
                index++;
            }
        }

        for(Collection<Object> line : data) {
            logger.log(createString(line, sizelist));
        }
    }

    private <T> void setToList(List<T> list, int index, T element) {
        if (index < list.size()) {
            list.set(index, element);
        } else {
            list.add(index, element);
        }
    }

    public void bookingPrint(Collection<Booking> data) {
        Collection<Collection<Object>> table = newArrayList();
        table.add(newArrayList("Activity", "Number", "Activity Id", "Starttime", "EndTime", "Id", "Comment"));
        for (Booking booking : data) {
            table.add(flattenBooking(booking));
        }
        tablePrint(table);
    }

    public void activityPrint(Collection<Activity> data) {
        Collection<Collection<Object>> table = newArrayList();
        table.add(newArrayList("Activity", "Number", "Activity Id"));
        for (Activity activity : data) {
            table.add(flattenActivity(activity));
        }
        tablePrint(table);
    }

    private Collection<Object> flattenBooking(Booking booking) {
        List<Object> line = newArrayList();
        line.addAll(flattenActivity(booking.getActivity()));
        line.add(booking.getStarttime().format(DateTimeFormatter.ofPattern("HH:mm")));
        line.add(booking.hasEndtime() ? booking.getEndtime().format(DateTimeFormatter.ofPattern("HH:mm")) : " ");
        line.add(booking.getId().toString());
        line.add(booking.getComment());
        return line;
    }

    private Collection<Object> flattenActivity(Activity activity) {
        return newArrayList(activity.getActivityName(), activity.getBookingNumber(), activity.getId().toString());
    }

    private String createString(final Collection<Object> columns, final List<Integer> sizelist) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("| ");
        int index = 0;
        for (Object current : columns) {
            resultString.append(StringUtils.rightPad(current.toString(), sizelist.get(index)));
            resultString.append(" | ");
            index++;
        }
        return resultString.toString();
    }
}
