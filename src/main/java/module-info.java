/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
module bsuir.poit.webtechnologies {
      requires static lombok;
      requires static org.jetbrains.annotations;
      opens by.bsuir.poit.geometry;
      opens by.bsuir.poit.calculation;
      opens by.bsuir.poit.database;
}