<html>
<head>
    <title>Projekt Details</title>
</head>

<body>

<h1>Informationen</h1>
<#list projekte as projekt>
<h2>${projekt.titel}</h2>
<br/>
    <h3>von:<a href="/view_profile">${projekt.ersteller}</a></h3>
        <br/>

<div>
    <!--#list aufprojekte as projekt-->
        <h3>${projekt.beschreibung}</h3>
        <br/>
        <li>Finanzierungslimit:${projekt.finanzierungslimit}EUR</li>
            <li>Aktuelle Spendensumme:${projekt.finanzierungslimit}EUR</li>
            <li>Status:${projekt.status}</li>
            <li>Vorgänger-Projekt:<a href="/view_project">${projekt.vorgaenger}</a></li>
    <!--/#list-->
</div>
</#list>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Aktionsleiste</h1>
<br/>
<a href="/new_project_fund">Spenden</a>
<a href="/edit_project">Projekt editieren</a>
<br/>
<form name="del" action="delete" method="post">
    <input type="submit" value="Projekt Löschen" />
</form>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Liste der Spender</h1>
<br/>
<#list spendern as spenden>
    <li>${spenden.spender}:${spenden.spendenBetrag}EUR</li>
</#list>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Kommentare</h1>
<br/>
<#list kommente as kommentar>
    <li>${kommentar.key}:${kommentar.value}</li>
</#list>
<br/>
<a href="/new_comment">kommentieren</a>
</body>
</html>