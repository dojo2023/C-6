function inputChange(event){
    console.log(event.currentTarget.value);
}

function click_form(){

}

let text = document.getElementsByClassName('select');

for (let i = 0; i < text.length; i++) {
    text[i].addEventListener('change', inputChange);
}
