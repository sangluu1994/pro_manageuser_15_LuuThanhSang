function toggleJpField() {
    var jpField = document.querySelectorAll('.japaneseField');
	var length = jpField.length;
	for (var i = 0; i < length; i++) {
		jpField[i].style.display = jpField[i].style.display == 'table-row' ? 'none' : 'table-row';
	}
}
