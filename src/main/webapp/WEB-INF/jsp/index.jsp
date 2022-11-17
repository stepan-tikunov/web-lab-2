<!--
	<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="edu.ifmo.tikunov.web.lab2.service.area.PointCheck" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page isELIgnored="false" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
-->

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
	<form id="hidden-form" method="post">
		<input type="hidden" name="x" />
		<input type="hidden" name="y" />
		<input type="hidden" name="r" />
	</form>

	<header>
		<div>
			<h1>Stepan Tikunov</h1>
			<h2>P32092 / 521866</h2>
		</div>
		<form action="logout" method="get">
			<span>
				You signed in as: <b><c:out value="${username}" /></b>
			</span>
			<input id="logout" type="submit" value="Log out" />
		</form>
	</header>
	<main>
		<section class="s-plot">
			<h2>Plot:</h2>
			<div id="plot">
				<img src="assets/png/plot.png" />
				<c:forEach var="point" items="${checks}">
					<span title="x = ${point.x}, y = ${point.y}, r = ${point.r}"
						  class="point ${point.hitStringValue}"
						  style="left: ${point.offsetLeft}px; top: ${point.offsetTop}px">
					</span>
				</c:forEach>
			</div>
		</section>
		<section class="s-form">
			<h2>Point details:</h2>
			<form method="post">
				<div class="input-group" id="x-group">
					<label>X:</label>
					<span class="radio-wrapper">
						<input type="radio" id="x-minus-4" name="x" value="-4" required />
						<label for="x-minus-4">-4</label>
						<input type="radio" id="x-minus-3" name="x" value="-3" />
						<label for="x-minus-3">-3</label>
						<input type="radio" id="x-minus-2" name="x" value="-2" />
						<label for="x-minus-2">-2</label>
						<input type="radio" id="x-minus-1" name="x" value="-1" />
						<label for="x-minus-1">-1</label>
						<input type="radio" id="x-0" name="x" value="0" />
						<label for="x-0">0</label>
						<input type="radio" id="x-1" name="x" value="1" />
						<label for="x-1">1</label>
						<input type="radio" id="x-2" name="x" value="2" />
						<label for="x-2">2</label>
						<input type="radio" id="x-3" name="x" value="3" />
						<label for="x-3">3</label>
						<input type="radio" id="x-4" name="x" value="4" />
						<label for="x-4">4</label>
					</span>
				</div>

				<div class="input-group" id="y-group">
					<label class="float-left" for="y">Y:</label>
					<input type="text" id="y" name="y" placeholder="-5 ... 3" required />
				</div>

				<div class="input-group" id="r-group">
					<label for="r">R:</label>
					<select name="r" id="r" required>
						<option value="" selected disabled>Select value</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</div>

				<input type="submit" value="Check" />
			</form>
		</section>
		<section class="s-table">
			<h2>Results:</h2>
			<table>
				<thead>
					<th>X</th>
					<th>Y</th>
					<th>R</th>
					<th>Result</th>
					<th>Execution datetime</th>
					<th>Time took</th>
				</thead>
				<tbody>
					<c:forEach var="point" items="${checks}">
						<tr>
							<td>
								<fmt:formatNumber value="${point.x}" maxFractionDigits="3" />
							</td>
							<td>
								<fmt:formatNumber value="${point.y}" maxFractionDigits="3" />
							</td>
							<td>
								<fmt:formatNumber value="${point.r}" maxFractionDigits="3" />
							</td>
							<td>${point.hitStringValue}</td>
							<td>${point.date}</td>
							<td>${point.executionTime} ms</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
	</main>

	<script type="text/javascript" src="assets/js/main.js"></script>
</body>

</html>