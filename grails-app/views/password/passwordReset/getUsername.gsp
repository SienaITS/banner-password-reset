<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/9/2015
  Time: 12:34 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner Password Reset - Get Username</title>
</head>

<body>
<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li>Choose System</li>
        <li>Choose Action</li>
        <li class="currentStep">Verify Username</li>
        <li>Verify Birth Date</li>
        <li>Check your Email</li>
        <li>Log into Banner</li>
    </ol>
</div>

<div id="mainDiv">
    <g:form>
        <p>

        <g:if test="${message}">
            <div class="errors"><ul><li>${message}</li></ul></div>
        </g:if>

        Enter your Banner Username:
        <g:textField name="username"></g:textField>
        </p>

        <g:submitButton name="submit" value="submit"></g:submitButton>
    </g:form>
</div>
</body>
</html>