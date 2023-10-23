/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
module bsuir.poit.webtechnologies {
      requires static lombok;
      requires static org.jetbrains.annotations;
      requires static org.mapstruct;
      requires java.sql;
      requires jakarta.servlet;
      requires org.apache.logging.log4j;
      opens by.bsuir.poit.servlets;
      opens by.bsuir.poit.dao.connections;
      opens by.bsuir.poit.context;
      opens by.bsuir.poit.dao.entities;
      opens by.bsuir.poit.dao.impl;
      exports by.bsuir.poit.dao.entities;
      exports by.bsuir.poit.dao.mappers;
      exports by.bsuir.poit.dao.impl;
      exports by.bsuir.poit.servlets;
}