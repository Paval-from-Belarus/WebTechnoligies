package by.bsuir.poit.dao.entities;

import lombok.*;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
private Integer id;
private String name;
private String email;
private byte[] hash;
}
