package test.main.testex.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "expired_date")
    Date expiredDate;

    @Column(name = "token")
    String token;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.ALL)
    private User user;

    public RefreshToken(Date startDate, Date expiredDate, String token) {
        this.startDate = startDate;
        this.expiredDate = expiredDate;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RefreshToken() {
    }
}
