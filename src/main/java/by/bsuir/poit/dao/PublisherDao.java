package by.bsuir.poit.dao;

import by.bsuir.poit.connections.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Paval Shlyk
 * @since 15/09/2023
 */
@RequiredArgsConstructor
public class PublisherDao {
private final @NotNull ConnectionPool pool;
}
