function check(){
	/* [ログイン]ボタンをクリックしたときの処理 */
	var formObj = document.getElementById('login_form');
	var errorMessageObj = document.getElementById('error_message');

	if (!formObj.ID.value || !formObj.PW.value) {
		errorMessageObj.textContent = '※IDとパスワードを入力してください！';
		return false;
	} else {
		return true;
	}
}
