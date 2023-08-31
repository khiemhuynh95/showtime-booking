package showtime.booking.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import showtime.booking.enums.MovieStatus;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Movie")
public class Movie {
	@Id
	@Column(unique = true, nullable = false)
	private Long MovieID;

	@Column(nullable = true)
	private String Title;

	@Column(length = 1000, nullable = true)
	private String Description;

	@Column(nullable = true)
	@Temporal(TemporalType.TIME)
	Date Duration;

	@Column(nullable = true)
	private String Language;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	Date ReleaseDate;

	@OneToMany(mappedBy = "Movie")
	private Set<Show> shows = new HashSet<>();

	@OneToMany(mappedBy = "Movie")
	private Set<MovieGenre> genres = new HashSet<>();

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private MovieStatus status;

	@Column(precision = 1, scale = 2)
	private Double Rating;

	@Column(length = 100, nullable = true)
	private String posterURL;

    @Column(length = 10000)
    private String backdrops;
    
	@Column(length = 100, nullable = true)
	private String trailerURL;

	public Movie(Long movieID, String title, String description, Date releaseDate, String language, MovieStatus status,
			Date duration, Double rating) {
		MovieID = movieID;
		Title = title;
		Description = description;
		Duration = duration;
		Rating = rating;
		Language = language;
		ReleaseDate = releaseDate;
		this.status = status;

	}

	public Movie() {
	}

	public String getTitle() {
		return Title;
	}

	public Long getMovieID() {
		return MovieID;
	}

	public String getDescription() {
		return Description;
	}

	public Date getDuration() {
		return Duration;
	}

	public String getLanguage() {
		return Language;
	}

	public Date getReleaseDate() {
		return ReleaseDate;
	}

//	public Set<Show> getShows() {
//		return shows;
//	}

	public Set<MovieGenre> getGenres() {
		return genres;
	}

	public MovieStatus getStatus() {
		return status;
	}

	public Double getRating() {
		return Rating;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}

	public String getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(String backdrops) {
		this.backdrops = backdrops;
	}

	public String getTrailerURL() {
		return trailerURL;
	}

	public void setTrailerURL(String trailerURL) {
		this.trailerURL = trailerURL;
	}


	
	

}
