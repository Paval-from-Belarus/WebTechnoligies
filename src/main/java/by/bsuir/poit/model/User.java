package by.bsuir.poit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "auction_db")
public class User {
@Id
@Column(name = "user_id", nullable = false)
private Long id;

@Size(max = 30)
@NotNull
@Column(name = "name", nullable = false, length = 30)
private String name;

@Size(max = 12)
@NotNull
@Column(name = "phone_number", nullable = false, length = 12)
private String phoneNumber;

@Size(max = 320)
@NotNull
@Column(name = "email", nullable = false, length = 320)
private String email;

@NotNull
@Column(name = "role", nullable = false)
private Short role;

@Size(max = 32)
@NotNull
@Column(name = "password", nullable = false, length = 32)
private String password;

@Size(max = 32)
@NotNull
@Column(name = "salt", nullable = false, length = 32)
private String salt;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "user_status_id", nullable = false)
private UserStatus userStatus;

}