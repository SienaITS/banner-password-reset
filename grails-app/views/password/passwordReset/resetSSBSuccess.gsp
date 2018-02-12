<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/14/2015
  Time: 3:46 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner SSB Password Successfully Reset</title>
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
        <li class="currentStep"><a href="http://selfservice.siena.edu/" target="_blank">Log into Banner</a></li>
    </ol>
</div>

<div id="mainDiv">
    <h3>Banner SSB Password Successfully Reset</h3>

    <p>The password for your Self Service Banner SSB account, ${account.spridenID}, has been successfully reset.  A new temporary password has been sent to your email: ${account.email}.<br/>
    </p>

    <p>You can sign into Banner using your new password by going to <a
            href="INSERT_LINK_HERE">INSERT_LINK_HERE</a>.  After signing into Banner with this password you will be forced to change your password.<br/>
    </p>
    <g:form>
        <p>To perform another action on your Banner account, you may click the button:</p>
        <g:submitButton name="submit" value="Start Over"></g:submitButton>
    </g:form>
</body>
</html>