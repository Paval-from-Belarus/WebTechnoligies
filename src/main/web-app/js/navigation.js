"use strict";
const APPLICATION_PATH = '/jdbc-servlets/'
const changePage = (pageUrl) => {
    window.location.replace(APPLICATION_PATH + pageUrl);
}
const changeLanguage = (language) => {
    let pageUrl = window.location.href.replace(/\?.*/g, '');
    window.location.href = pageUrl + '?' + `lang=${language}`
}