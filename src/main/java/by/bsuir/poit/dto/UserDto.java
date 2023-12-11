package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
@Null(groups = Create.class)
private Long id;
@NotNull
private String name;
@NotNull
private String phoneNumber;
@NotNull
private String email;
@NotNull
private Short role;
}
