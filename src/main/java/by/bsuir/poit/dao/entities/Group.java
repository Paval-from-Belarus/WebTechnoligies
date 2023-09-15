package by.bsuir.poit.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {
private Integer id;
private String name;
}
