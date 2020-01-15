<html>
<head>
    <title>Projekt Details</title>
</head>

<body>
<div>
<h1>Informationen</h1>
<#list aufprojekte as projekt>
<h2>${projekt.titel}</h2>
<br/>
    <h3>von:<a href="/view_profile">${projekt.ersteller}</a></h3>
        <br/>
</#list>
</div>
<div>
    <#list aufprojekte as projekt>
        <h3>${projekt.beschreibung}</h3>
        <br/>
        <li>Finanzierungslimit:${projekt.finanzierungslimit}EUR</li>
            <li>Aktuelle Spendensumme:${projekt.finanzierungslimit}EUR</li>
            <!--li>Status:</li-->
            <li>Vorgänger-Projekt:<a href="/view_project">${projekt.vorgaenger}</a></li>
    </#list>
</div>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Aktionsleiste</h1>
<br/>
<a href="/new_project_fund">Spenden</a>
<a href="/edit_project">Projekt editieren</a>
<br/>
<form name="del" action="hi" method="post">
    <input type="submit" value="Projekt Löschen" />
</form>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Liste der Spender</h1>
<br/>
<#list aufprojekte as projekt>
    <li>Finanzierungslimit:${projekt.finanzierungslimit}EUR</li>
    <li>Aktuelle Spendensumme:${projekt.finanzierungslimit}EUR</li>
    <li>Vorgänger-Projekt:<a href="/view_project">${projekt.vorgaenger}</a></li>
</#list>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Kommentare</h1>
<br/>
<#list aufprojekte as projekt>
    <li>Finanzierungslimit:${projekt.finanzierungslimit}EUR</li>
    <li>Aktuelle Spendensumme:${projekt.finanzierungslimit}EUR</li>
    <li>Vorgänger-Projekt:<a href="/view_project">${projekt.vorgaenger}</a></li>
</#list>
<br/>
<a href="/new_comment">kommentieren</a>
</body>
</html>