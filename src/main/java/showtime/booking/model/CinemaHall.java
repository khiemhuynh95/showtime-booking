package showtime.booking.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cinema_Hall")
public class CinemaHall {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cinemaHallID;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "CinemaHall", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Show> shows;

	@ManyToOne
	@JoinColumn(name = "CinemaID")
	private Cinema Cinema;

	@OneToMany(mappedBy = "CinemaHall", fetch = FetchType.EAGER)
	private Set<CinemaSeat> CinemaSeats = new HashSet<>();
	
	public CinemaHall(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CinemaHall() {
		
	}

	public void setCinema(Cinema cinema) {
		Cinema = cinema;
	}

	public String getCinema() {
		return Cinema.getCinemaID() + "|" + Cinema.getName() + "|" + Cinema.getAddress();
	}

	public Set<CinemaSeat> getCinemaSeats() {
		return CinemaSeats;
	}

	public void setCinemaSeats(Set<CinemaSeat> cinemaSeats) {
		CinemaSeats = cinemaSeats;
	}
	
	
	
	

	
}