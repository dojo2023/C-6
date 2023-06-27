function inputChange(event){
    console.log(event.currentTarget.value);
}

function click_form(event){
	document.getElementById("submit_jsp_f_id").value = String(event.target.name);
	document.getElementById("submit_jsp_num").value = String(event.currentTarget.value);

	console.log(document.getElementsByClassName("submit_jsp_f_id").value);
	console.log(document.getElementsByClassName("submit_jsp_num").value);

	document.form_num.submit();
}

let text = document.getElementsByClassName('select');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('change', click_form);
}
