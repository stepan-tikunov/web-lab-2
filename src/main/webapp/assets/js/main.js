"use strict";

(function() {

	const xGroup = document.querySelector("#x-group");
	const yGroup = document.querySelector("#y-group");
	const rGroup = document.querySelector("#r-group");

	function isNumeric(str) {
		return (typeof str == "string") && !isNaN(str) && !isNaN(parseFloat(str));
	}

	function makeInvalid(group, message) {
		group.dataset.message = message;
		group.classList.add("invalid");
	}

	function validate(event) {
		const x = document.querySelector(".s-form [name=x]:checked")?.value;
		const y = document.querySelector(".s-form [name=y]")?.value;
		const r = document.querySelector(".s-form [name=r] option:checked:not(:disabled)")?.value;

		     if (!isNumeric(x))   makeInvalid(xGroup, "Must be numeric");
		else if (!isNumeric(y))   makeInvalid(yGroup, "Must be numeric");
		else if (!isNumeric(r))   makeInvalid(rGroup, "Must be numeric");
		else if (y < -5 || y > 3) makeInvalid(yGroup, "Must be in -5 ... 3 range");
		else                      return true;

		event.preventDefault();
		return false;
	}

	function inputGroupClickHandler(group) {
		return () => group.classList.remove("invalid");
	}

	function plotClickHandler(event) {
		const r = document.querySelector("[name=r] option:checked:not(:disabled)")?.value;

		if (!isNumeric(r)) {
			makeInvalid(rGroup, "Select R before aiming");
			return;
		}

		const rsInImageWidth = 2.533;
		const rsInImageHeight = 2.533;

		const imageWidthPx = event.target.clientWidth;
		const imageHeightPx = event.target.clientHeight;

		const rActualWidth = imageWidthPx / rsInImageWidth;
		const rActualHeight = imageHeightPx / rsInImageHeight;

		const viewportOffset = event.target.getBoundingClientRect();
		const imageLeft = viewportOffset.left;
		const imageTop = viewportOffset.top;

		const xFromCornerPx = event.clientX - imageLeft;
		const yFromCornerPx = event.clientY - imageTop;

		const xFromCenterPx = xFromCornerPx - imageWidthPx / 2;
		const yFromCenterPx = imageHeightPx / 2 - yFromCornerPx;

		const x = xFromCenterPx / rActualWidth * r;
		const y = yFromCenterPx / rActualHeight * r;

		const xInput = document.querySelector("#hidden-form [name=x]");
		const yInput = document.querySelector("#hidden-form [name=y]");
		const rInput = document.querySelector("#hidden-form [name=r]");
		const form = document.querySelector("#hidden-form")

		xInput.value = x;
		yInput.value = y;
		rInput.value = r;

		form.submit();
	}

	document.querySelector("#plot").addEventListener("click", plotClickHandler);

	rGroup.addEventListener("click", inputGroupClickHandler(xGroup));
	yGroup.addEventListener("click", inputGroupClickHandler(yGroup));
	rGroup.addEventListener("click", inputGroupClickHandler(rGroup));

	document.querySelector(".s-form form").addEventListener("submit", validate);

}());