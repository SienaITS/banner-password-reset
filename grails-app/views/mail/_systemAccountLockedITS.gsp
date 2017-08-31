<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Banner Password Reset Access Blocked</title>
</head>

<body>
<h1>Banner Password Reset Access Blocked</h1>

<p>The following user's access to the Banner Password Reset application (bannerpwdreset.siena.edu) has been blocked due to too many invalid attempts:</p>

<table>
    <tr><th>AD Username:</th><td>${ldap_username}</td></tr>
    <tr><th>IP Address:</th><td>${ip_address}</td></tr>
</table>
</body>
</html>