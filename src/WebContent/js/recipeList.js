function inputChange(event){
    console.log(event.currentTarget.value);
}

function click_form(event){
	document.getElementById("submit_jsp_rec_id_ran").value = String(event.target.name);
	document.getElementById("submit_jsp_rec_id_max").value = String(event.currentTarget.value);

	console.log(document.getElementsByClassName("submit_jsp_rec_id_ran").value);
	console.log(document.getElementsByClassName("submit_jsp_rec_id_max").value);
	
		document.form_num.submit();
}

let text = document.getElementsByClassName('select');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('change', click_form);
}