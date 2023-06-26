function inputChange(event){
    console.log(event.currentTarget.value);
}

function click_form(event){
	let f_id = document.getElementsByClassName("submit_jsp_f_id").value;
	let num = document.getElementsByClassName("submit_jsp_num").value;
	f_id = String(event.target.name);			// f_id
	num = String(event.currentTarget.value);	// num

    console.log(event.target.name);

//    console.log(f_id);
//	console.log(num);
//	console.log(event.currentTarget.value);

	this.form.submit();
}

let text = document.getElementsByClassName('select');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('change', click_form);
}
