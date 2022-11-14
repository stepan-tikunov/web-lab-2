"use strict";

(function() {

	function makeInvalid(group, message) {
		group.dataset.message = message;
		group.classList.add("invalid");
	}

	function validate(event) {
		const password = document.getElementById("password").value;
		const again = document.getElementById("again").value;

		const againGroup = document.getElementById("again-group");

		if (password !== again) {
			makeInvalid(againGroup, "Must match password");
			event.preventDefault();
			return false;
		}

		return true;
	}

	document.querySelector("form").addEventListener("submit", validate);

}());