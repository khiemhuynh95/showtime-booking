package showtime.booking.datagen;

import showtime.booking.enums.MovieStatus;
import showtime.booking.enums.SeatStatus;
import showtime.booking.model.Cinema;
import showtime.booking.model.CinemaHall;
import showtime.booking.model.CinemaSeat;
import showtime.booking.model.Genre;
import showtime.booking.model.Movie;
import showtime.booking.model.MovieGenre;
import showtime.booking.model.Show;
import showtime.booking.model.ShowSeat;
import showtime.booking.services.CinemaHallsService;
import showtime.booking.services.CinemaSeatsService;
import showtime.booking.services.CinemasService;
import showtime.booking.services.GenresService;
import showtime.booking.services.MovieGenreService;
import showtime.booking.services.MoviesService;
import showtime.booking.services.ShowSeatsService;
import showtime.booking.services.ShowsService;
import showtime.booking.util.ApiHelper;
import showtime.booking.util.DateUtil;
import showtime.booking.util.JsonHelper;
import showtime.booking.util.RandomData;

import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataGenerator {

	private static CinemasService cService;
	private static MoviesService mService;
	private static GenresService gService;
	private static MovieGenreService mgService;
	private static CinemaHallsService chService;
	private static ShowsService sService;
	private static CinemaSeatsService csService;
	private static ShowSeatsService ssService;

	public DataGenerator(MoviesService mService, GenresService gService, MovieGenreService mgService,
			CinemasService cService, CinemaHallsService chService, ShowsService sService, CinemaSeatsService csService,
			ShowSeatsService ssService) {
		this.mService = mService;
		this.gService = gService;
		this.mgService = mgService;
		this.cService = cService;
		this.chService = chService;
		this.sService = sService;
		this.csService = csService;
		this.ssService = ssService;
	}

	public static void start() {
		System.out.println("Starting DataGenerator...");
		// populate genres
		populateGenres();
		// populate movies
		populateTMDBMovies(MovieStatus.NOW_PLAYING);
		populateTMDBMovies(MovieStatus.UPCOMING);
		// populate theaters
		populateCinemas();

		// populate cinema halls
		populateCinemaHalls();

		// populate shows
		populateShows();

		System.out.println("Generating data is done!");
	}

	private static void populateShows() {
		// TODO Auto-generated method stub
		// get the list of cinemahalls from db
		List<CinemaHall> cinemahalls = chService.getAllCenemaHalls();
		// Get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// for the next seven days
		for (int i = 0; i < 7; i++) {
			for (CinemaHall ch : cinemahalls) {
				// for each date, generate some showtimes
				int shows = RandomData.number(2, 6);
				for (int j = 0; j < shows; j++) {
					// Generate a random showtime for each day
					Date randomShowtime = RandomData.showtimeForDate(calendar.getTime());

					// Print the generated showtime for this day
					SimpleDateFormat showtimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					System.out.println("Random Showtime for " + dateFormat.format(calendar.getTime()) + ":");
					System.out.println("  " + showtimeFormat.format(randomShowtime));

					// generate a show
					Show s = new Show(randomShowtime, randomShowtime);
					// set a random cinema hall
					s.setCinemaHall(ch);
					// set a random movie
					Movie m = mService.getRandomNowPlaying();
					// add duration to random show time to get endtime
					Date endTime = DateUtil.calculateEndTime(randomShowtime, m.getDuration());
					s.setMovie(m);
					s.setEndTime(endTime);
					// save show to db
					sService.addShow(s);

					// generate some showseats, set this show to them					
					for(CinemaSeat seat : ch.getCinemaSeats()) {
						ShowSeat showSeat = new ShowSeat(RandomData.seatStatus(), seat, s);
						ssService.addShowSeat(showSeat);
						
					}

				}

			}

			// Move to the next day
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

	public static void populateCinemas() {
		JsonObject jo = JsonHelper.fromFile("src/main/resources/mock/theaters.json");
		JsonArray theatersJsonArray = jo.getAsJsonArray("theaters");
		for (JsonElement theater : theatersJsonArray) {
			JsonObject theaterObject = theater.getAsJsonObject();
			String name = theaterObject.get("name").getAsString();
			String addressStr = theaterObject.get("address").getAsString();
			// Define a regular expression pattern to match the address, city, state, and
			// ZIP code
			String pattern = "^(.*?),\\s*(.*?),\\s*(\\w{2})\\s*(\\d{5})$";

			// Create a Pattern object and a Matcher object
			Pattern regex = Pattern.compile(pattern);
			Matcher matcher = regex.matcher(addressStr);

			// Check if the pattern matches
			if (matcher.matches()) {
				String address = matcher.group(1);
				String city = matcher.group(2);
				String state = matcher.group(3);
				String zipcode = matcher.group(4);

				Cinema cinema = new Cinema(name, address, city, state, zipcode);
				cService.addCinema(cinema);

			} else {
				System.out.println("Pattern not matched.");
			}
		}

	}

	public static void initCinemaSeats(CinemaHall ch) {
		// Define the row labels and the number of seats per row
		String[] rowLabels = { "A", "B", "C", "D", "E", "F" };
		int seatsPerRow = 14;

		for (String row : rowLabels) {
			for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
				CinemaSeat seat = new CinemaSeat(seatNumber, row);
				seat.setCinemaHall(ch);
				csService.addCinemaSeat(seat);
			}
		}

	}

	public static void populateCinemaHalls() {
		// get list of cinemas from db
		List<Cinema> cinemas = cService.getAllCinemas();

		// for every cinema, random a number of halls
		for (Cinema c : cinemas) {
			int numOfHalls = RandomData.number(5, 10);
			for (int i = 0; i < numOfHalls; i++) {
				// generate cinema hall
				Faker faker = new Faker();
				String hallName = faker.pokemon().name() + "-" + RandomData.generateUniqueString(4);

				// add cinema to cinemahall
				CinemaHall ch = new CinemaHall(hallName);

				ch.setCinema(c);

				chService.addCinemaHall(ch);
				// init
				initCinemaSeats(ch);
			}
		}
	}

	public static void populateTMDBMovies(MovieStatus status) {
		String url = String.format("https://api.themoviedb.org/3/movie/%s?language=en-US&page=1&region=us",
				status.name().toLowerCase());

		JsonObject jsonObject = ApiHelper.getJsonResponse(url);
		JsonArray resultsArray = jsonObject.getAsJsonArray("results");

		// Loop through movie results and print titles
		for (JsonElement resultElement : resultsArray) {
			JsonObject movieObject = resultElement.getAsJsonObject();
			Long id = movieObject.get("id").getAsLong();
			String title = movieObject.get("title").getAsString();
			String description = movieObject.get("overview").getAsString();
			String language = movieObject.get("original_language").getAsString();
			Date releaseDate = DateUtil.convert(movieObject.get("release_date").getAsString(), "yyyy-mm-dd");
			Date duration = RandomData.duration();
			Double rating = movieObject.get("vote_average").getAsDouble();
			JsonArray genresArray = movieObject.get("genre_ids").getAsJsonArray();

			// create a movie
			Movie m = new Movie(id, title, description, releaseDate, language, status, duration, rating);

			// get images
			fetchMovieImages(m);

			// get teaser url
			fetchVideoTrailerUrl(m);

			mService.addMovie(m);
			// creating movie-genre
			for (JsonElement genreElement : genresArray) {
				Genre g = gService.findGenre(genreElement.getAsLong());
				MovieGenre mg1 = new MovieGenre(m, g);
				mgService.createMovieGenre(mg1);
			}
		}
	}

	public static void populateGenres() {
		String url = "https://api.themoviedb.org/3/genre/movie/list?language=en";
		JsonObject jsonObject = ApiHelper.getJsonResponse(url);
		JsonArray resultsArray = jsonObject.getAsJsonArray("genres");
		for (JsonElement resultElement : resultsArray) {
			JsonObject object = resultElement.getAsJsonObject();
			Long id = object.get("id").getAsLong();
			String name = object.get("name").getAsString();
			gService.addGenre(new Genre(id, name));
		}

	}

	public static void fetchMovieImages(Movie m) {
		String url = String.format("https://api.themoviedb.org/3/movie/%d/images", m.getMovieID());
		JsonObject jsonObject = ApiHelper.getJsonResponse(url);
		// get poster

		JsonObject temp = null;
		JsonArray resultsArray = jsonObject.getAsJsonArray("posters");
		if (!resultsArray.isEmpty()) {
			JsonElement resultElement = resultsArray.get(0);
			temp = resultElement.getAsJsonObject();
			m.setPosterURL("https://image.tmdb.org/t/p/w300" + temp.get("file_path").getAsString());
		} else {
			m.setPosterURL("");
		}

		// get backdrop images
		resultsArray = jsonObject.getAsJsonArray("backdrops");
		List<String> backdrop_paths = new ArrayList<>();
		for (JsonElement element : resultsArray) {
			temp = element.getAsJsonObject();
			backdrop_paths.add("https://image.tmdb.org/t/p/w300" + temp.get("file_path").getAsString());
		}
		m.setBackdrops(backdrop_paths.toString());
	}

	public static void fetchVideoTrailerUrl(Movie m) {
		String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?language=en-US", m.getMovieID());
		JsonObject jsonObject = ApiHelper.getJsonResponse(url);
		// get poster
		JsonArray resultsArray = jsonObject.getAsJsonArray("results");
		for (JsonElement resultElement : resultsArray) {
			JsonObject object = resultElement.getAsJsonObject();
			String key = object.get("key").getAsString();
			String type = object.get("type").getAsString();
			System.out.println(type);
			if (type.equals("Trailer")) {
				String trailerURL = "https://www.youtube.com/watch?v=" + key;
				m.setTrailerURL(trailerURL);
				break;
			}

		}
	}

}
