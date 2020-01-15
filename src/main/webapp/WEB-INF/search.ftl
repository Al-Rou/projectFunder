<html>
<head><title>Projekt Suche</title>

<body>
<form name="user" action="hello" method="post">
    Titel: <input type="text" name="firstname" /> <br/>
    <input type="submit" value="Suche" />
</form>
<br/>
<h1>***************************</h1>
<br/>
<h1>Suchergebnisse</h1>
<br/>
<div>
    <#list aufprojekte as projekt>
        <li>Titel:<a href="/view_project">${projekt.titel}</a></li>
        <li>Von:<a href="/view_profile">${projekt.ersteller}</a></li>
        <li>Aktuell:${projekt.finanzierungslimit}</li>
        <li>Status:${projekt.finanzierungslimit}</li>
        <li>***********************************</li>
    </#list>
</div>
</body>
</html>