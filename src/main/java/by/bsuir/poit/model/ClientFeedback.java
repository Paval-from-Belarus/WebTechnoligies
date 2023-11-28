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
@Table(name = "client_feedback", schema = "auction_db")
public class ClientFeedback {
@Id
@Column(name = "client_feedback_id", nullable = false)
private Long id;

@NotNull
@Column(name = "ranking", nullable = false)
private Byte ranking;

@Lob
@Column(name = "text")
private String text;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "lot_id", nullable = false)
private Lot lot;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "author_client_id", nullable = false)
private Client authorClient;

@NotNull
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "target_client_id", nullable = false)
private Client targetClient;

}