<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/10/2015
  Time: 3:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Banner INB Password Reset</title>
</head>

<body>
<h1>Banner INB Password Reset</h1>
<p>Dear ${name},</p>
<p>The password for your Banner INB account has been reset as requested through Banner Password Reset application.  If you did not request that your password be reset, please contact ITS immediately because this may mean that your account has been compromised.  The security of your account is very important to us.</p>
<p>
    The new password for your account can be obtained by clicking on the link below:<br/>
    <a href="${password}">${password}</a><br/>
</p>
<p>Please note, this is an automated email. Please do not reply directly to this email.   If you reply, it will not be received by ITS staff.</p>
<p>
    Sincerely,<br/>
    ITS - Administrative Systems<br/>
    Siena College
</p>

</body>
</html>