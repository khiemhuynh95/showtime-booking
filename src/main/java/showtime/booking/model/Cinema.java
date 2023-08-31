package showtime.booking.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
@Entity
@Table(name = "Cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaID;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)
    private String zipcode;
    
    @OneToMany(mappedBy = "Cinema")
    private Set<CinemaHall> cinemaHalls = new HashSet<>();

	public Cinema( String name,String address, String city, String state, String zipcode) {
//		this.cinemaID = cinemaID;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	public Cinema() {}
	public Set<CinemaHall> getCinemaHalls() {
		return cinemaHalls;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public Long getCinemaID() {
		return cinemaID;
	}
	
	
	
	
}
