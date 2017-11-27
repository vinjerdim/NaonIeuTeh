<%
    // New location to be redirected
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", "Login");
%>