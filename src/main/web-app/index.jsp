<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Main page</title>
</head>
<body>
<form action="auth" method="post">
    <input type="hidden" name="command" value="do_anything_command" />
    <input type="hidden" name="filename" value="myfile" />
    <input type="text" name="xxx" value=""/>
    <input type="submit" value="SAX" /><br/>
</form>
</body>
</html>