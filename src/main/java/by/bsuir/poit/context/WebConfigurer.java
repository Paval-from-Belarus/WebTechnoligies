package by.bsuir.poit.context;

import by.bsuir.poit.controller.LotModificationHandler;
import by.bsuir.poit.utils.ParserUtils;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Paval Shlyk
 * @since 07/12/2023
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
private static final Logger LOGGER = LogManager.getLogger(WebConfigurer.class);

@Override
public void addFormatters(@NotNull FormatterRegistry registry) {
      registry.addConverter(lotModificationTypeConverter());
}

@Bean
public Converter<String, LotModificationHandler.LotModificationType> lotModificationTypeConverter() {
      return string -> ParserUtils
			   .parseEnum(LotModificationHandler.LotModificationType.class, string)
			   .orElseThrow(() -> newInvalidEnumSyntaxException(string));
}

private IllegalStateException newInvalidEnumSyntaxException(String value) {
      final String msg = String.format("Invalid syntax for given enum=%s", value);
      LOGGER.warn(msg);
      throw new IllegalStateException(msg);
}
}
