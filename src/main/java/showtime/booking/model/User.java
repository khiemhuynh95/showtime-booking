package showtime.booking.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;
    
    @Column(unique = true, nullable = false)
    private String Email;

    @Column(nullable = false)
    private String Password;
    
    @Column(unique = true, nullable = true)
    private String Phone;
    
    @OneToMany(mappedBy = "User")
	private Set<Booking> bookings = new HashSet<>();

}
