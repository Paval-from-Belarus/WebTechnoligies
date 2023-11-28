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
@Table(name = "english_lot", schema = "auction_db")
public class EnglishLot {
@Id
@Column(name = "lot_id", nullable = false)
private Long id;

@MapsId
@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "lot_id", nullable = false)
private Lot lot;

@Column(name = "redemption_price", precision = 10, scale = 2)
private BigDecimal redemptionPrice;

}