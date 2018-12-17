<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>ログイン</title></head>
<body><h1>ログイン</h1>
<hr>
<div align="center">
    <table border="0">
        <form action="login" method="post">
            <tr>
                <th class="login_field">ユーザID</th>
                <td class="login_field"><input type="text" name="intraAccount" value="${intraAccount}" size="24" id="intraAccount">
                </td>
            </tr>
            <tr>
                <th class="login_field">パスワード</th>
                <td class="login_field"><input type="password" name="password" value="" size="24" id="password"/></td>
            </tr>
            <tr>
                <th class="login_field">パスワード(確認)</th>
                <td class="login_field"><input type="password" name="password2" value="" size="24" id="password2"/></td>
            </tr>
            <tr>
                <td colspan="2" class="login_button"><input type="submit" value="ログイン" id="login"></td>
            </tr>
        </form>
    </table>
</div>
</body>
</html>
