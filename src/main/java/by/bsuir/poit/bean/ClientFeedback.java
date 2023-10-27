package by.bsuir.poit.bean;

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
public class ClientFeedback {
private long id;
private double ranking;
private String text;
private long lotId;
private long authorId;
private long targetId;
}
