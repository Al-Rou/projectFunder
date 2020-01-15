<html>
<head><title>Projekt Editieren</title>

<body>
<h1>Projekt editieren</h1>
<form name="user" action="hello" method="post">
    Titel: <input type="text" name="firstname" /> <br/>
    Finanzierungslimit: <input type="text" name="lastname" />EUR<br/>
    Kategorie:<br/>
    <input type="radio" name="group" value="Health& Wellness"/>Health& Wellness<br/>
    <input type="radio" name="group" value="Art& Creative Works"/>Art& Creative Works<br/>
    <input type="radio" name="group" value="Education"/>Education<br/>
    <input type="radio" name="group" value="Tech& Innovation"/>Tech& Innovation<br/>
    Vorgänger:<br/>
    <#list users as user>
        <tr>
            <td><input type="radio" name="version"/>${user.firstname}</td><br/>
        </tr>
    </#list>
    <td><input type="radio" name="version"/>Kein Vorgänger</td><br/>
    Beschreibung: <input type="text" name="explanation" /> <br/>
    <input type="submit" value="aktualisieren" />
</form>
</body>
</html>