//Global color theme variable
var colorTheme = null;

// Get color theme
const setColorTheme = (color) => {
	// Select the current palatte for display
	palatte = document.querySelector(`[data-color="${color}"]`);
	if(palatte){
		palatte.style.border = '.15vw solid white';
		palatte.style.opacity = '100%'
		
	}
	color = '#' + color;
	document.documentElement.style.setProperty('--colorTheme', color);
	colorTheme = color;
}
// Change color theme
const changeColorTheme = (element, color) => {
	const palattes = document.querySelectorAll('.palatte')
	
	// Set user colorTheme
	fetch('/changeColorTheme/'+color, {method : 'POST'})
	
	// Change current DOM color theme
	color = '#' + color;
	document.documentElement.style.setProperty('--colorTheme', color);
	
	// Change selected color on color palatte
	for( const palatte of palattes){
		palatte.style.border = '.15vw solid #211f2a';
		palatte.style.opacity = '50%';
	}
	element.style.border = '.15vw solid white';
	element.style.opacity = '100%'
	
	colorTheme = color;
}


function toggleForm() {
    var newTask = document.getElementById("newTask");
    newTask.classList.toggle("visible");
}

const showEditName = () => {
	
	const editTitle = document.querySelector(".editName");
	editTitle.style.display = "block";
}
const hideEditName = () => {
	
	const editTitle = document.querySelector(".editName");
	editTitle.style.display = "none";
}

const showAddEmail = () => {
	const addEmail = document.querySelector(".addEmail");
	addEmail.style.display = "block";
}

// Select user to be assigned the task and add their id to the hidden input value array
const selectUser = element => {
	const isSelected = element.getAttribute('data-is-selected');
	const userId = element.getAttribute('data-userId');
	const hiddenInput = document.querySelector('.hiddenInput');
	const currentAssignedUserIds = hiddenInput.value ? hiddenInput.value.split(',') : [];
	const newAssignedUserIds = [];
	const assignButton = document.querySelector('.assignButton')

	// If user wasnt already selected, select them and add their id to the hidden input value array
	if(isSelected == 'false'){
		element.style.backgroundColor = colorTheme;
		element.style.color = '#211f2a';
		element.style.fontWeight = '700';
		element.setAttribute('data-is-selected', "true");
		currentAssignedUserIds.push(userId);
		hiddenInput.value = currentAssignedUserIds;
	}
	// If they were selected, unselect them and remove their id to the hidden input value array
	else{
		element.style.backgroundColor = 'black';
		element.style.color = 'white';
		element.setAttribute('data-is-selected', "false");	
		for(const id of currentAssignedUserIds){
			if(id != userId){
				newAssignedUserIds.push(id);
			}
		}
		hiddenInput.value = newAssignedUserIds;	
	}
	
	// If there are no users assigned to task, disable submit button
	if(hiddenInput.value == ''){
		assignButton.disabled = true;
		assignButton.style.opacity = "50%";
	} else {
		assignButton.disabled = false;
		assignButton.style.opacity = "100";
	}
}

// Change the isResolved status of task
const changeResolvedStatus = (taskId) => {
	const tasks = document.querySelectorAll(`[data-task-id="${taskId}"]`);
	const isResolved = tasks[0].getAttribute('data-is-resolved');
	const checkmarks = document.querySelectorAll(`[data-checkmark-id="${taskId}"]`);
	// If current status is false, set to true and set new style properties
	if(isResolved == 'false'){
		fetch('/changeResolvedStatus/' + taskId + '/true', {method: 'POST'})
		for(const task of tasks){
			task.setAttribute('data-is-resolved', 'true');
			task.style.opacity = '50%';
			task.querySelector('.instructions').style.textDecoration = 'line-through';
		}
		for(const checkmark of checkmarks){
			checkmark.style.display = 'block';
		}
	}
	else{
		fetch('/changeResolvedStatus/' + taskId + '/false', {method: 'POST'})
		for(const task of tasks){
			task.setAttribute('data-is-resolved', 'false');
			task.style.opacity = '100%';
			task.querySelector('.instructions').style.textDecoration = 'none';
		}	
		for(const checkmark of checkmarks){
			checkmark.style.display = 'none';
		}
	}
}

