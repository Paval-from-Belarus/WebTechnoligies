package by.bsuir.poit.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientFeedbackDto {
@Null(groups = Create.class)
private Long id;
@NotNull
private double ranking;
@NotNull
private String text;
@Null(groups = Create.class)
private long lotId;
@Null(groups = Create.class)
private long authorId;
@Null(groups = Create.class)
private long targetId;
}
