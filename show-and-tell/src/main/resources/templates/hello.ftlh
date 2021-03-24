<!DOCTYPE html>
<html lang="en">
<head>

    <title>Credit Table</title>
    <meta charset="UTF-8">
    <script src="js/main.js" type="text/javascript"></script>
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"
            type="text/javascript"></script>
    <link href="css/main.css" rel="stylesheet"
          type="text/css" />

    <script>
    var data= JSON.parse(${jsonArr}.replace(/&quot;/g,'"'));
    console.log("initial");
    console.log(data);
    var list = JSON.parse(${jsonArr});
        $(document).ready(function () {
            tableList(list);
            });
    </script>

</head>
<body>
<h2 class="hello-title">Hello ${jsonArr}!</h2>
<script src="/js/main.js"></script>
</body>
</html>