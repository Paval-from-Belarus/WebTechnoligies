package by.bsuir.poit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Paval Shlyk
 * @since 06/12/2023
 */
@SpringBootApplication
@EnableTransactionManagement
public class AuctionApplication {
public static void main(String[] args) {
      SpringApplication.run(AuctionApplication.class, args);
}
}
