package by.bsuir.poit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AuctionMemberId implements Serializable {
@Serial
private static final long serialVersionUID = 1;
@NotNull
@Column(name = "client_id", nullable = false)
private Long clientId;

@NotNull
@Column(name = "auction_id", nullable = false)
private Long auctionId;

@Override
public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      AuctionMemberId entity = (AuctionMemberId) o;
      return Objects.equals(this.auctionId, entity.auctionId) &&
		 Objects.equals(this.clientId, entity.clientId);
}

@Override
public int hashCode() {
      return Objects.hash(auctionId, clientId);
}

}