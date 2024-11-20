package vn.demo.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.demo.starter.entity.enumeration.Role;
import vn.demo.starter.entity.enumeration.UserStatus;
import vn.demo.starter.util.DateUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "blocked_count")
    private int blockedCount;

    @Column(name = "blocked_date")
    private Instant blockedDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "deleted")
    private boolean deleted;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserSession> sessions;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private UserDetail userDetail;

    public void resetBlocked() {
        setBlockedCount(0);
        setBlockedDate(null);
        setStatus(UserStatus.ACTIVE);
    }

    public void setNewSession(UserSession session) {
        session.setUser(this);
        this.sessions.clear();
        this.sessions.add(session);
    }

    public void addNewSession(UserSession session){
        session.setUser(this);
        this.sessions = new ArrayList<>();
        this.sessions.add(session);
    }

    public boolean inBlockedTime() {
        Instant compareDate = DateUtils.currentInstant().minus(30, ChronoUnit.MINUTES);
        return Objects.nonNull(getBlockedDate()) && getBlockedDate().isAfter(compareDate);
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }


}
