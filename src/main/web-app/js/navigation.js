"use strict";
const APPLICATION_PATH = '/jdbc-servlets/'
const changePage = (pageUrl) => {
    window.location.replace(APPLICATION_PATH + pageUrl);
}
const changeLanguage = (language) => {
    let pageUrl = window.location.href;
    if (pageUrl.indexOf('?') === -1) {
        pageUrl += `?lang=${language}`;
    } else {
        if (pageUrl.indexOf('lang=') === -1) {
            pageUrl += `&lang=${language}`;
        } else {
            pageUrl = pageUrl.replace(/lang=[^&]+/g, `lang=${language}`);
        }
    }
    window.location.href = pageUrl;
}
const leave = () => {
    setCookie('lang', '', 0);
    setCookie('user_id', '', 0);
    setCookie('user_role', '', 0);
    window.location.href = APPLICATION_PATH + "api/leave";
}
const setCookie = (name, value, expiration) => {
    const d = new Date();
    d.setTime(d.getTime() + (expiration * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}