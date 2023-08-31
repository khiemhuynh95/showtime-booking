package showtime.booking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date convert(String dateString, String pattern) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date calculateEndTime(Date startTime, Date duration) {
		// Convert Date objects to Calendar instances
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startTime);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(duration);

		// Extract hours and minutes from both dates
		int hours1 = cal1.get(Calendar.HOUR_OF_DAY);
		int minutes1 = cal1.get(Calendar.MINUTE);

		int hours2 = cal2.get(Calendar.HOUR_OF_DAY);
		int minutes2 = cal2.get(Calendar.MINUTE);

		// Add hours and minutes separately
		int totalHours = hours1 + hours2;
		int totalMinutes = minutes1 + minutes2;

		// Handle carry-over if minutes exceed 60
		if (totalMinutes >= 60) {
			totalHours += totalMinutes / 60;
			totalMinutes %= 60;
		}
		
		
		// Set the updated hours and minutes back into the Calendar
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, totalHours);
        calendar.set(Calendar.MINUTE, totalMinutes);

        // Get the updated Date
        return calendar.getTime();
	}

}
