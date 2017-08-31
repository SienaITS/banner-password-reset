<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/10/2015
  Time: 3:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner INB Password Successfully Reset</title>
</head>

<body>
<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li>Choose Action</li>
        <li>Choose System</li>
        <li>Verify Username</li>
        <li>Verify Birth Date</li>
        <li class="currentStep"><a href="https://mail.google.com/" target="_blank">Check your Email</a></li>
        <li class="currentStep"><a href="http://banner.siena.edu/" target="_blank">Log into Banner</a></li>
    </ol>
</div>

<div id="mainDiv">
    <h3>Banner INB Password Successfully Reset</h3>

    <p>The password for your Banner INB account, ${account.bannerUsername}, has been successfully reset.  A new temporary password has been sent to your email: ${account.email}.<br/>
    </p>

    <p>You can sign into Banner using your new password by going to <a
            href="http://banner.siena.edu">http://banner.siena.edu</a>.  After signing into Banner with this password you will be forced to change your password. For security purposes, Banner INB passwords must be changed every 90 days and can not be re-used.<br/>
    </p>
    <g:form>
        <p>To perform another action on your Banner account, you may click the button:</p>
        <g:submitButton name="submit" value="Start Over"></g:submitButton>
    </g:form>
</div>
</body>
</html>