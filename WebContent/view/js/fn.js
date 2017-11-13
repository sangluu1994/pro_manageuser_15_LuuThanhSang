function addUser() {
	window.location.href = "addUserInput.do";
}

function toggleJpField() {
    var jpField = document.querySelectorAll('.japaneseField');
	var length = jpField.length;
	for (var i = 0; i < length; i++) {
		jpField[i].style.display = jpField[i].style.display == 'table-row' ? 'none' : 'table-row';
	}
}

function back(backType) {
	document.inputform.action = backType;
	document.inputform.method = "get";
	var accessType = document.createElement('input');
	accessType.setAttribute("type", "hidden");
	accessType.setAttribute("name", "type");
	accessType.setAttribute("value", "back");
	document.inputform.appendChild(accessType);
	document.inputform.submit();
}