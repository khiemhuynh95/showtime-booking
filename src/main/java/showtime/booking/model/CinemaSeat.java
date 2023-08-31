package showtime.booking.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import showtime.booking.enums.SeatType;

@Entity
@Table(name = "Cinema_Seat")
public class CinemaSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CinemaSeatID;
    
    @Column(unique = true, nullable = false)
    private Integer SeatNumber;
    
    @Column(length = 32, columnDefinition = "varchar(32) default 'REGULAR'")
    @Enumerated(value = EnumType.STRING)
    private SeatType Type;
    
    @ManyToOne
    @JoinColumn(name = "CinemaHallID")
    private CinemaHall CinemaHall;
    
    @OneToMany(mappedBy = "CinemaSeat")
    private Set<ShowSeat> ShowSeats = new HashSet<>();
}
