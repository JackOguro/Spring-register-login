const elemBirthday = document.getElementById('birthday2');
const elemAge = document.getElementById('age');

elemBirthday.addEventListener("input", () => {
	
	const birthday = elemBirthday.value.replace(/\D/g, '');
	
	const d = new Date();
	
	const today = d.getFullYear() 
		+ String(d.getMonth() + 1).padStart(2, '0')
		+ String(d.getDate()).padStart(2, '0');
		
	const age = Math.floor((today - birthday) / 10000);
	
	elemAge.value = age < 0 ? '--' : age;
});