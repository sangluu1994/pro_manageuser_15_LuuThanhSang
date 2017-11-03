function addUser() {
	window.location.href = "addUserInput.do";
}

function showJpField() {
    var jpField = document.getElementById('japaneseField');
	if (jpField.style.display == 'none') {
        jpField.style.display = 'block';
    } else {
        jpField.style.display = 'none';
    }
    
}
