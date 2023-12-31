package showtime.booking.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import showtime.booking.enums.SeatStatus;
import showtime.booking.model.CinemaSeat;

public class RandomData {
	public static int number(int max) {
		int min = 4;
		Random random = new Random();
		// Generate a random number within the specified range
		return random.nextInt(max - min + 1) + min;
	}

	public static int number(int min, int max) {
		Random random = new Random();
		// Generate a random number within the specified range
		return random.nextInt(max - min + 1) + min;
	}

	public static String generateUniqueString(int maxLength) {
		UUID uuid = UUID.randomUUID();
		String uniqueString = uuid.toString().replaceAll("-", "").substring(0, maxLength);
		return uniqueString;
	}

	public static Date duration() {
		int randomHours = number(1, 2);
		int randomMins = number(0, 60);
		int randomSeconds = number(0, 60);

		Date duration = new GregorianCalendar(2023, Calendar.AUGUST, 8, randomHours, randomMins).getTime();
		duration.setSeconds(randomSeconds);
		System.out.println("Random Duration: " + duration);
		return duration;
	}

	public static Date showtimeForDate(Date date) {
		// Generate a random time between 9:00 AM and 11:59 PM with minutes divisible by
		// 15
		Random random = new Random();
		Calendar showtimeCalendar = Calendar.getInstance();
		showtimeCalendar.setTime(date);

		// Randomly select an hour between 9 and 23
		int hour = 9 + random.nextInt(15);

		// Randomly select a multiple of 15 for the minutes
		int minutes = 15 * random.nextInt(4);

		showtimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
		showtimeCalendar.set(Calendar.MINUTE, minutes);
		showtimeCalendar.set(Calendar.SECOND, 0);
		showtimeCalendar.set(Calendar.MILLISECOND, 0);

		return showtimeCalendar.getTime();
	}

	public static SeatStatus seatStatus() {
		Random random = new Random();
		int randomIndex = random.nextInt(SeatStatus.values().length);
		return SeatStatus.values()[randomIndex];
	}

	public static float generateRandomPrice() {
		Random random = new Random();
		float minPrice = 12.0f;
		float maxPrice = 18.0f;
		float range = maxPrice - minPrice;
		int numIncrements = (int) (range / 0.25f);
		int randomIncrement = random.nextInt(numIncrements + 1);
		float randomPrice = minPrice + randomIncrement * 0.25f;
		return randomPrice;
	}
}
