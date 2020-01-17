<html>
<head><title>Projekt Spende</title>

<body>
<form name="user" action="hello" method="post">

    <#list users as user>
    <tr>
        <td>${user.firstname}</td><br/>
    Spendenbetrag (EUR): <input type="text" name="lastname" /><br/>
    </tr>
    </#list>
    <td><input type="radio" name="version" value="Anonym"/>Anonym spenden?</td><br/>
    <input type="submit" value="Spenden" />
</form>
</body>
</html>