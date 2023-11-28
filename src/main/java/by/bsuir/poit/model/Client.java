package by.bsuir.poit.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client", schema = "auction_db")
public class Client {
@Id
@Column(name = "user_id", nullable = false)
private Long id;

@MapsId
@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "user_id", nullable = false)
private User user;

@Column(name = "account", precision = 10, scale = 2)
private BigDecimal account;

@Column(name = "ranking", precision = 10, scale = 2)
private BigDecimal ranking;

}