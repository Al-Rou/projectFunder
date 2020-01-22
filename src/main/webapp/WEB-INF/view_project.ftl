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


        <h3>${projekt.beschreibung}</h3>
        <br/>
        <li>Finanzierungslimit:${projekt.finanzierungslimit}EUR</li>
            <li>Aktuelle Spendensumme:${total}&nbsp;EUR</li>
            <li>Status:${projekt.status}</li>
    <li>Kennung:${projekt.kennung}</li>

</#list>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Aktionsleiste</h1>
<br/>
<#list projekte as projekt>
<a href="/new_project_fund?kennung=${projekt.kennung}"><button>Spenden</button></a>

<a href="/edit_project?kennung=${projekt.kennung}"><button>Projekt editieren</button></a>
</#list>
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
    <li>${spenden.spender}:&nbsp;${spenden.spendenBetrag}&nbsp;EUR</li>
</#list>
<br/>
<h1>*****************************</h1>
<br/>
<h1>Kommentare</h1>
<br/>

<#list kommente as showkomment>
    <li>${showkomment.benutzer}:&nbsp;${showkomment.komtext}</li>
</#list>

<br/>
<a href="/new_comment">kommentieren</a>
</body>
</html>