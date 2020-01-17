<html>
<head>
    <title>Nutzer Profil</title>
</head>

<body>

<h1>Profil von dummy@dummy.com</h1>
<br/>
<div>
        <li>Benutzername:${kontoinhab}</li>
        <li>Anzahl erstellter Projekte:${anzahlerst}</li>
        <li>Anzahl unterstüzter Projekte:${anzahlunte}</li>
</div>
<br/>
<h1>*****************************</h1>
<h1>Erstellte Projekte</h1>
<br/>
<div>
    <#list aufprojekte as projekt>
        <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
        <li>Aktuell:${projekt.finanzierungslimit}</li>
            <li>Status:${projekt.status}</li>
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
        <li>Limit:${projekt.finanzierungslimit}</li>
        <li>Status:${projekt.status}</li>
            <li>Gespendet:${projekt.finanzierungslimit}</li>
        <li>***********************************</li>
    </#list>
</div>
</body>
</html>