package by.bsuir.poit.dto;

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
private long id;
private double ranking;
private String text;
private long lotId;
private long authorId;
private long targetId;
}