package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lot", schema = "auction_db")
public class Lot {
@Id
@Column(name = "lot_id", nullable = false)
private Long id;

@Size(max = 50)
@NotNull
@Column(name = "title", nullable = false, length = 50)
private String title;

@NotNull
@Column(name = "start_price", nullable = false, precision = 10, scale = 2)
private BigDecimal startPrice;

@Column(name = "auction_price", precision = 10, scale = 2)
private BigDecimal auctionPrice;

@NotNull
@Column(name = "status", nullable = false)
private Short status;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "auction_type_id", nullable = false)
private AuctionType auctionType;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "seller_client_id", nullable = false)
private Client sellerClient;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_client_id")
private Client customerClient;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "delivery_point_id")
private DeliveryPoint deliveryPoint;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id")
private Auction auction;

}