package showtime.booking.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Genre")
public class Genre {
	@Id
	@Column(unique = true, nullable = false)
	private Long GenreID;

	@Column(nullable = false)
	private String Name;
	
	@OneToMany(mappedBy = "Genre")
	private Set<MovieGenre> genres = new HashSet<>();

	public Genre(Long genreID, String name) {
		GenreID = genreID;
		Name = name;
	}

	public Genre() {
	}

	public Genre(Long genreID) {
		super();
		GenreID = genreID;
	}

	public String getName() {
		return Name;
	}

	

}
