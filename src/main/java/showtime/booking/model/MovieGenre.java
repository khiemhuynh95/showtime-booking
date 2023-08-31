package showtime.booking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Movie_Genre")
public class MovieGenre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "MovieID")
	private Movie Movie;
	
	@ManyToOne
	@JoinColumn(name = "GenreID")
	private Genre Genre;

	public MovieGenre(Movie movie, Genre genre) {
		Movie = movie;
		Genre = genre;
	}
	
	public MovieGenre() {}

//	public Movie getMovie() {
//		return null;
//	}

	public String getGenre() {
		return Genre.getName();
	}
	
	
}
