package by.bsuir.poit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auction_type", schema = "auction_db")
public class AuctionType {
@Id
@Column(name = "auction_type_id", nullable = false)
private Long id;

@Size(max = 30)
@NotNull
@Column(name = "schema_name", nullable = false, length = 30)
private String schemaName;

@Size(max = 50)
@NotNull
@Column(name = "name", nullable = false, length = 50)
private String name;

@Size(max = 100)
@Column(name = "description", length = 100)
private String description;

}