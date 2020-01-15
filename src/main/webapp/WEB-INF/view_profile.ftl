<html>
<head>
    <title>Hauptseite</title>
</head>

<body>
<#list users as user>
<h1>Profil von ${user.email}</h1>
<br/>
</#list>
<br/>
<#list users as user>
        <li>Benutzername:${user.firstname}</li>
        <li>Anzahl erstellter Projekte:2</li>
        <li>Anzahl unterstüzter Projekte:5</li>
</#list>
<br/>
<h1>*****************************</h1>
<h1>Erstellte Projekte</h1>
<br/>
<div>
    <#list aufprojekte as projekt>
        <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
        <li>Aktuell:${projekt.finanzierungslimit}</li>
            <li>Status:${projekt.finanzierungslimit}</li>
        <li>***********************************</li>
    </#list>
</div>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Unterstüzte Projekte</h1>
<br/>
<div>
    <#list zuprojekte as projekt>
        <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
        <li>Limit:${projekt.ersteller}</li>
        <li>Status:${projekt.finanzierungslimit}</li>
            <li>Gespendet:${projekt.finanzierungslimit}</li>
        <li>***********************************</li>
    </#list>
</div>
</body>
</html>