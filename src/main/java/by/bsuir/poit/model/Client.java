package by.bsuir.poit.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client", schema = "auction_db")
public class Client {
public static final int ACTIVE = 1; //for statuses
public static final int BLOCKED = 2;
@Id
@Column(name = "user_id", nullable = false)
private Long id;
@Column(name = "name", table = "user")
private String name;

@Column(name = "account", precision = 10, scale = 2)
private BigDecimal account;

@Column(name = "ranking", precision = 10, scale = 2)
private Double ranking;

@MapsId
@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "user_id", nullable = false)
private User user;
}