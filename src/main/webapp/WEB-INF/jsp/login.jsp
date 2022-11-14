<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Lab 2 Web</title>
	<link rel="stylesheet" href="assets/css/style.css" />
</head>

<body>
	<header>
		<div>
			<h1>Stepan Tikunov</h1>
			<h2>P32092 / 521866</h2>
		</div>
	</header>
	<main>
		<div>
			<h1>Login</h1>
			<form method="post">
				<div class="input-group">
					<label for="username">Username:</label>
					<input type="text" name="username" id="username" required />
				</div>
				<div class="input-group">
					<label for="password">Password:</label>
					<input type="password" name="password" id="password" required />
				</div>

				<a href="register">
					<small>I don't have an account</small>
				</a>

				<input type="submit" value="Login!" />
			</form>
		</div>
	</main>
</body>

</html>