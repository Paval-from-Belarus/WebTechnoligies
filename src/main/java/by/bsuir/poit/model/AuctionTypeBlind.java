package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auction_type_blind", schema = "auction_db")
public class AuctionTypeBlind {
@Id
@Column(name = "auction_id", nullable = false)
private Long id;

@MapsId
@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "auction_id", nullable = false)
private Auction auction;

@NotNull
@Column(name = "bet_limit", nullable = false)
private Integer betLimit;

@NotNull
@Column(name = "timeout", nullable = false)
private Instant timeout;

}