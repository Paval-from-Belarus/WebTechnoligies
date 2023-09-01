/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
module bsuir.poit.webtechnologies {
      requires static lombok;
      requires static org.jetbrains.annotations;
      opens bsuir.poit.webtechnologies.geometry;
      opens bsuir.poit.webtechnologies.calculation;
      opens bsuir.poit.webtechnologies.database;
}