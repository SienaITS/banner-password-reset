<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/2/2015
  Time: 10:39 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner Password Reset</title>
    <style>
    #leftDiv {
        position: relative;
        float: left;
        padding: 10px;
    }

    #rightDiv {
        position: relative;
        float: left;
        padding: 10px;
    }
    </style>
</head>

<body>

<div id="stepsDiv">
    <h1>Steps in Workflow:</h1>
    <ol>
        <li class="currentStep">Choose Action</li>
        <li>Choose System</li>
        <li>Verify Username</li>
        <li>Verify Birth Date</li>
        <li>Check your Email</li>
        <li>Log into Banner</li>
    </ol>
</div>

<div id="mainDiv">
    What action would you like to perform on your Banner account?
    <g:form>
        <div id="leftDiv">
            <g:submitButton name="unlock" value="Unlock Account"></g:submitButton>
        </div>

        <div id="rightDiv">
            <g:submitButton name="reset" value="Reset Password"></g:submitButton>
        </div>
    </g:form>
</div>

</body>
</html>