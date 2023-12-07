package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auction_bet", schema = "auction_db")
public class AuctionBet {
@Id
@Column(name = "auction_bet_id", nullable = false)
private Long id;

@NotNull
@Column(name = "bet", nullable = false, precision = 10, scale = 2)
private Double bet;

@NotNull
@Column(name = "time", nullable = false)
@CreatedDate
private Date time;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "lot_id", nullable = false)
private Lot lot;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "client_id", nullable = false)
private Client client;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "auction_id", nullable = false)
private Auction auction;

}