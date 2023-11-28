package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_status", schema = "auction_db")
public class UserStatus {
@Id
@Column(name = "user_status_id", nullable = false)
private Short id;

@Size(max = 30)
@Column(name = "name", length = 30)
private String name;

@Lob
@Column(name = "description")
private String description;

}