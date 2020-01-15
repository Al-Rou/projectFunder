<html>
<head><title>Projekt Erstellen</title>

<body>
<h1>Projekt erstellen</h1>
<form name="user" action="erstellen" method="post">
    Titel: <input type="text" name="titel" /> <br/>
    Finanzierungslimit: <input type="text" name="amount" />EUR<br/>
    E-Mail: <input type="text" name="email" />EUR<br/>
    Kategorie:<br/>
    <input type="radio" name="group" value="Health& Wellness"/>Health& Wellness<br/>
    <input type="radio" name="group" value="Art& Creative Works"/>Art& Creative Works<br/>
    <input type="radio" name="group" value="Education"/>Education<br/>
    <input type="radio" name="group" value="Tech& Innovation"/>Tech& Innovation<br/>
    Vorgänger:<br/>
    <#list users as user>
        <tr>
            <td><input type="radio" name="version" value="${user.firstname}"/>${user.firstname}</td><br/>
        </tr>
    </#list>
    <td><input type="radio" name="version" value="Kein Vorg"/>Kein Vorgänger</td><br/>
    Beschreibung: <input type="text" name="explanation" /> <br/>
    <input type="submit" value="Erstellen" />
</form>
</body>
</html>