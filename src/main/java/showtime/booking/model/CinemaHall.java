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

	@OneToOne(mappedBy = "CinemaHall", cascade = CascadeType.ALL, orphanRemoval = true)
	private Show show;

	@ManyToOne
	@JoinColumn(name = "CinemaID")
	private Cinema Cinema;

	@OneToMany(mappedBy = "CinemaHall")
	private Set<CinemaSeat> CinemaSeats = new HashSet<>();
	
	public CinemaHall(String name) {
		this.name = name;
	}
	
	public CinemaHall() {
		
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public void setCinema(Cinema cinema) {
		Cinema = cinema;
	}

	public String getCinema() {
		return Cinema.getCinemaID() + "|" + Cinema.getName() + "|" + Cinema.getAddress();
	}
	
	

	
}