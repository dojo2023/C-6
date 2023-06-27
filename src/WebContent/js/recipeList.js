// クリック時の処理
function click_form(event){
	document.getElementById("b_select").value = String(event.target.name);
}

let text = document.getElementsByClassName('c_search_btn');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('click', click_form);
}
