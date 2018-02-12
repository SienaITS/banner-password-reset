<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/10/2015
  Time: 11:54 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner INB Account Successfully Unlocked</title>
</head>

<body>
<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li>Choose System</li>
        <li>Choose Action</li>
        <li>Verify Username</li>
        <li>Verify Birth Date</li>
        <li class="currentStep"><a href="https://mail.google.com/" target="_blank">Check your Email</a></li>
        <li class="currentStep"><a href="http://banner.siena.edu/" target="_blank">Log into Banner</a></li>
    </ol>
</div>

<div id="mainDiv">
    <h3>Banner INB Account Successfully Unlocked</h3>

    <p>Your Banner INB Account, ${account.bannerUsername}, has been successfully unlocked.  A separate email notification has been sent to ${account.email}.  You can now log into Banner INB by going to <a
            href="INSERT_LINK_HERE">INSERT_LINK_HERE</a>.<br/></p>

    <p>Please note: The password for your account has not changed.  Your account has only been unlocked so that you can log in.<br/>
    </p>
    <g:form>
        <p>To perform a password reset or another action on your Banner account, you may click the button:</p>
        <g:submitButton name="submit" value="Start Over"></g:submitButton>
    </g:form>
</div>
</body>
</html>