<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>
        <script src="js/history.js"></script>
    </head>
    <body>
        <div class="subtitle-cont subtitle-history">
            TRANSACTION HISTORY
        </div>
        <div class="body-container">
            <div class='menu'>
                <div class="menu-column"></div>
                <div class="menu-column"></div>
                <div id="user-his" class="menu-cell" onclick="tabActive('user');">MY PREVIOUS ORDERS</div>
                <div id="driver-his" class="menu-cell" onclick="tabActive('driver');">DRIVER HISTORY</div>
            </div>
        </div>

        <div id="history-container">
            <jsp:include page="/DisplayHistory?choice=0"/>

            <jsp:include page="/DisplayHistory?choice=1"/>
        </div>
        <script>tabActive('user');</script>
    </body>
</html>
