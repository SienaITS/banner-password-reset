<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js"/>
        <link href="https://fonts.googleapis.com/css?family=Gudea|Merriweather|Oswald" rel="stylesheet">
		<g:layoutHead/>
	</head>
	<body>
		<div id="grailsLogo" role="banner">
            <div id="sienaLogo" >
                <a href="https://community.siena.edu/academic-affairs/information-technology-services"><asset:image src="siena-logo-its.jpg" alt="Siena Logo"/></a>
            </div>
            <sec:ifLoggedIn>
                <div id="logoutDiv">
                    Logged in as: <sec:username/> - <sec:loggedInUserInfo field="displayName"/>
                    <g:form name="logout" method="POST" action="index" controller="logout">
                        <g:field type="submit" name="logoutButton" value="Logout"></g:field>
                    </g:form>
                </div>
            </sec:ifLoggedIn>
        </div>
		<div id="bodyDiv">
            <div id="headerDiv">
                <h1>BANNER PASSWORD RESET</h1>
            </div>
			<g:layoutBody/>
		</div>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
