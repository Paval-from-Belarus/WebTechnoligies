/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
module bsuir.poit.webtechnologies {
      requires static lombok;
      requires static org.jetbrains.annotations;
      requires static org.mapstruct;
      requires static jakarta.inject;
      requires java.sql;
      requires jakarta.servlet;
      requires org.apache.logging.log4j;
      opens by.bsuir.poit;
      opens by.bsuir.poit.servlets;
      opens by.bsuir.poit.dao;
      opens by.bsuir.poit.connections;
      exports by.bsuir.poit.dao.mappers;
}