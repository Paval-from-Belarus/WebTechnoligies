package by.bsuir.poit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "delivery_point", schema = "auction_db")
public class DeliveryPoint {
@Id
@Column(name = "delivery_point_id", nullable = false)
private Long id;

@Size(max = 12)
@Column(name = "city_code", length = 12)
private String cityCode;

@Size(max = 100)
@Column(name = "street_name", length = 100)
private String streetName;

@Size(max = 10)
@Column(name = "house_number", length = 10)
private String houseNumber;

@Size(max = 50)
@Column(name = "delivery_point_name", length = 50)
private String deliveryPointName;

}