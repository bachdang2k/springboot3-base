package vivas.repo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String username;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;
    public RefreshToken() {
    }
}
