package by.bsuir.poit.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.zone.ZoneOffsetTransitionRule;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
private Integer id;
private Integer postId;
private Integer publisherId;
private String contentPath;
private Timestamp publishTime;
}
