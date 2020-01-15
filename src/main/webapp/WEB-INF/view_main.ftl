<html>
<head>
    <title>Hauptseite</title>
</head>

<body>
<h1>ProjektFunder</h1>
<a href="/view_profile">Mein Profil</a>
<h1>Offene Projekte</h1>
<br/>
<div>
    <#list aufprojekte as projekt>
            <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
            <li>Von:<a href="/view_profile">${projekt.ersteller}</a></li>
            <li>Aktuell:${projekt.finanzierungslimit}</li>
            <li>***********************************</li>
    </#list>
</div>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Abgeschlossene Projekte</h1>
<br/>
<div>
    <#list zuprojekte as projekt>
        <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
        <li>Von:<a href="/view_profile">${projekt.ersteller}</a></li>
        <li>Aktuell:${projekt.finanzierungslimit}</li>
        <li>***********************************</li>
    </#list>
</div>
<a href="/new_project">Projekt erstellen</a>
</body>
</html>