/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
module bsuir.poit.webtechnologies {
      requires static lombok;
      requires static jakarta.validation;
      requires static org.mapstruct;
      requires static jakarta.inject;

      requires java.sql;
      requires jakarta.servlet;
      requires org.apache.logging.log4j;
      requires reflections;

      opens by.bsuir.poit.servlets;
      opens by.bsuir.poit.dao.connections;
      opens by.bsuir.poit.context;
      opens by.bsuir.poit.bean;
      opens by.bsuir.poit.dao.impl;
      opens by.bsuir.poit.dao.exception;
      opens by.bsuir.poit.servlets.filters;

      exports by.bsuir.poit.bean;
      exports by.bsuir.poit.bean.mappers;
      exports by.bsuir.poit.dao.impl;
      exports by.bsuir.poit.servlets;
      exports by.bsuir.poit.dao.exception;
      exports by.bsuir.poit.servlets.filters;
}