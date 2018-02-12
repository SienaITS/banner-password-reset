<%--
  Created by IntelliJ IDEA.
  User: mstanco
  Date: 9/2/2015
  Time: 11:41 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Banner Password Reset - Choose System</title>
    <style>
    #leftdiv {
        position: relative;
        float: left;
        padding: 10px;
    }

    #rightdiv {
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
        <li class="currentStep">Choose System</li>
        <li>Choose Action</li>
        <li>Verify Username</li>
        <li>Verify Birth Date</li>
        <li>Check your Email</li>
        <li>Log into Banner</li>
    </ol>
</div>

<div id="mainDiv">
    <p>Choose which system to
        <g:if test="${account?.action == 'unlock'}">
            unlock your account for:
        </g:if>
        <g:if test="${account?.action == 'reset'}">
            reset your password for:
        </g:if><br/>
    </p>

    <g:form>
        <div id="leftdiv"><g:submitButton name="selfServiceBanner" value="Self-Service Banner"
                                          disabled="${disableSSB}"></g:submitButton></div>

        <div id="rightdiv"><g:submitButton name="internetNativeBanner" value="Internet Native Banner"
                                           disabled="${disableINB}"></g:submitButton></div>
    </g:form>

    <g:if test="${disableSSB == "true"}">
        <div class="message"
             style="clear:left;"><ul><li>You do not have access to the Self-Service Banner (SSB) System.</li></ul></div>
    </g:if>
    <g:if test="${disableINB == "true"}">
        <div class="message"
             style="clear:left;"><ul><li>You do not have access to the Internet Native Banner (INB) System.</li></ul>
        </div>
    </g:if>
</div>
</body>
</html>