package ait.cohort49.hostel_casa_flamingo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "cart_bed",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "bed_id")
    )
    private List<Bed> beds;

    @ManyToMany
    @JoinTable(
            name = "cart_room",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public Long getId() {
        return id;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Cart: id - %d, beds - %s, rooms - %s",
                this.id, beds == null ? 0 : beds.size(), rooms == null ? 0 : rooms.size());
    }


    public void addBed(Bed bed) {
        if (bed != null) {
            beds.add(bed);
        }
    }

    public List<Bed> getAllBeds() {
        return beds.stream()
                .toList();
    }

    public Bed removeById(Long id) {
        Optional<Bed> optionalBed = beds.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
        if (optionalBed.isEmpty()) return null;

        Bed bed = optionalBed.get();
        beds.remove(bed);
        return bed;
    }

    public BigDecimal getTotalPrice() {
        if (beds == null) return BigDecimal.ZERO;

        return beds.stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        beds.clear();
    }
}
