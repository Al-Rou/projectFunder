<html>
<head><title>Projekt Kommentar</title>

<body>
<form name="user" action="hello" method="post">

    <#list users as user>
        <tr>
            <td>${user.firstname}</td><br/>
            <input type="text" name="explanation" />
        </tr>
    </#list>
    <td><input type="radio" name="version"/>Anonym kommentieren?</td><br/>
    <input type="submit" value="Kommentar hinzufügen" /><br/>
</form>
</body>
</html>