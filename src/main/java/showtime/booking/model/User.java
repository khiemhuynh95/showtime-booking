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
    private String email;

    @Column(nullable = true)
    private String firstName;
    
    @Column(nullable = true)
    private String lastName;
    
    @Column(nullable = true)
    private String Password;
    
    @Column(unique = true, nullable = true)
    private String Phone;
    
    @OneToMany(mappedBy = "User")
	private Set<Booking> bookings = new HashSet<>();

	public User(String email, String firstName, String lastName, String phone) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		Phone = phone;
	}


    public User() {}
    

}
