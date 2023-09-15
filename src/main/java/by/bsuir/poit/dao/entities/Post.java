package by.bsuir.poit.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
private Integer id;
private Integer publisherId;
private Integer groupId;
private String title;
private Timestamp publishTime;
private String contentPath;
}
