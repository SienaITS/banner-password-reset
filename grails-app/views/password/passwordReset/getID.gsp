<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/2/2015
  Time: 3:05 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner Password Reset - Get ID</title>
</head>

<body>

<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li>Choose Action</li>
        <li>Choose System</li>
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

        Enter your Banner ID:
        <g:textField name="spridenID"></g:textField>
        <br/>
        </p>

        <g:submitButton name="submit" value="submit"></g:submitButton>
    </g:form>
</div>
</body>
</html>