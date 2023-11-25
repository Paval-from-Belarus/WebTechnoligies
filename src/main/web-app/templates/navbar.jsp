<header>
    <nav class="navbar">
        <div class="navbar__title" onclick="changePage('lobby')">
            <div class="navbar__title-logo">
                <!-- <img src="img/nav-links/mascot.png" alt="Pet OS logo"> -->
            </div>
            <div class="navbar__title-text">
                <p>
                    <fmt:message key="application.name"/>
                </p>
            </div>
        </div>
        <div class="navbar__links-wrapper">
            <menu class="navbar__links">
                <button class="navbar__link" onclick="changePage('lobby')">
                    <!-- <img src="img/nav-links/megaphone.png" alt="megaphone" class="navbar__link-icon"> -->
                    <div class="navbar__link-text">
                        <fmt:message key="label.lobby-page-name"/>
                    </div>
                </button>
                <button class="navbar__link" onclick="changePage('user')">
                    <div class="navbar__link-text">
                        <fmt:message key="label.user-page-name"/>
                    </div>
                </button>
                <button class="navbar__link" onclick="changeLanguage('${nextLang}')">
                    <div class="navbar__link-text">
                        <fmt:message key="label.change-lang"/>
                    </div>
                </button>
                <button class="navbar__link" onclick="leave()">
                    <div class="navbar__link-text">
                        <fmt:message key="label.leave"/>
                    </div>
                </button>
            </menu>
        </div>
    </nav>
</header>