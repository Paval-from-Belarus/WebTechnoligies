package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auction_member", schema = "auction_db")
public class AuctionMember {
@EmbeddedId
private AuctionMemberId id;

@MapsId("clientId")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "client_id", nullable = false)
private Client client;

@MapsId("auctionId")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "auction_id", nullable = false)
private Auction auction;

@NotNull
@Column(name = "status", nullable = false)
private Byte status;

}