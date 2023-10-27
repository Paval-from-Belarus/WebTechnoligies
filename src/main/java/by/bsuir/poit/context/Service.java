package by.bsuir.poit.context;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Service {
      String value() default "";
}
