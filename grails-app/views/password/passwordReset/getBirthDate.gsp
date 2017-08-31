<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/4/2015
  Time: 12:31 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner Password Reset - Get Birth Date</title>
</head>

<body>
<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li>Choose Action</li>
        <li>Choose System</li>
        <li>Verify Username</li>
        <li class="currentStep">Verify Birth Date</li>
        <li>Check your Email</li>
        <li>Log into Banner</li>
    </ol>
</div>
<div id="mainDiv">
<g:if test="${message}">
    <div class="errors"><ul><li>${message}</li></ul></div>
</g:if>

<p>
    Hi ${account.firstName} ${account.lastName}! <br/>
    In order to proceed with
    <g:if test="${account?.action == 'unlock'}">
        unlocking your account,
    </g:if>
    <g:if test="${account?.action == 'reset'}">
        resetting the password on your account,
    </g:if>
    we need to verify your identity.   Please provide your birth date.<br/>
</p>

<g:form>
    Enter your Birth Date: <g:datePicker name="birthDate" precision="day"></g:datePicker><br/><br/>
    <g:submitButton name="submit" value="Verify Birth Date"></g:submitButton>
</g:form>
</body>
</div>
</html>