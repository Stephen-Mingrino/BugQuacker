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
}
// Change color theme
const changeColorTheme = (element, color) => {
	const palattes = document.querySelectorAll('.palatte')
	
	// Set user colorTheme
	fetch('/changeColorTheme/'+color)
	
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
}

function toggleForm(){
    var form = document.getElementById("newProjectForm");
    form.classList.toggle("visible");
}

function toggleProject(projectId){
	// Get list of tasks by projectId
    var tasksElement = document.querySelector(`[data-task-view="${projectId}"]`);
    tasksElement.classList.toggle("visible");
}

// Validate project Name
const validateProjectName = projectName => {
	projectNameValidation = document.querySelector(".projectNameValidation");
	createButton = document.querySelector(".createButton");
	if(projectName.length > 0 && projectName.length < 21){
		createButton.disabled = false;
		projectNameValidation.style.opacity = "0%";
		createButton.style.opacity = "100%"
	}
	else{
		createButton.disabled = true;
		projectNameValidation.style.opacity = "100%";
		createButton.style.opacity = "0%"
	}
}


// Find user and add their id to memberIds hidden input
const findUser = (activeUserId) =>  {
	const emailInput = document.querySelector(".emailInput");
	const encodedEmail = encodeURIComponent(emailInput.value);

	// Get the member object by email	
	fetch('/getMemberInfoByEmail/' + encodedEmail)
        .then(res => res.json())
        .then(newMemberInfo => {
        	const addedMembers = document.querySelector(".addedMembers");
        	const newMemberElement = document.createElement("div");
        	const memberFeedback = document.querySelector(".memberFeedback");

        	// If activeUser is the member serched for
        	if(newMemberInfo.id == activeUserId){
				memberFeedback.innerHTML = "You're already a member silly goose."
				memberFeedback.style.opacity = "100%";
				
				setTimeout( function() {
					memberFeedback.style.opacity = "10%";
				}, 2000);
				return;
			}
        	
        	// If member email is not found
			if(!newMemberInfo.id){
				memberFeedback.innerHTML = "Member not found."
				memberFeedback.style.opacity = "100%";
				
				setTimeout( function() {
					memberFeedback.style.opacity = "10%";
				}, 2000);
				
				return;	
			} else{
				memberFeedback.innerHTML = "Member added."
				memberFeedback.style.opacity = "100%";
				
				setTimeout( function() {
					memberFeedback.style.opacity = "10%";
				}, 2000);
			}
 
        	newMemberElement.className = "member";
        	newMemberElement.className = "initials-icon";
        	// Add initials to newly created member div for display
        	newMemberElement.innerHTML = newMemberInfo.initials;
        	// Add the newly created member div to addMembers div
        	addedMembers.appendChild(newMemberElement); 
        	
        	//Add newMember's id to hidden input
        	const hiddenInput = document.querySelector(".currentMemberIdsHiddenInput");
        	const newMemberId = newMemberInfo.id;
        	
        	let currentMemberIds = hiddenInput.value.split(',');
        	// let currentMemberIds = hiddenInput.value ? hiddenInput.value.split(',') : [];
        	currentMemberIds.push(newMemberId);
        	hiddenInput.value = currentMemberIds.join(',');
        	
        	hiddenInput.value = currentMemberIds;
        	
        	// Clear the email input field
            emailInput.value = "";
        })
}


// Change the isResolved status of task
const changeResolvedStatus = (taskId) => {
	const tasks = document.querySelectorAll(`[data-task-id="${taskId}"]`);
	const isResolved = tasks[0].getAttribute('data-is-resolved');
	console.log(isResolved)
	// If current status is false, set to true and set new style properties
	if(isResolved == 'false'){
		fetch('/changeResolvedStatus/' + taskId + '/true',{method: 'POST'})
		for(const task of tasks){
			task.setAttribute('data-is-resolved', 'true');
			task.style.opacity = '50%';
			task.querySelector('.instructions').style.textDecoration = 'line-through';
		}
	}
	else{
		fetch('/changeResolvedStatus/' + taskId + '/false', {method: 'POST'})
		for(const task of tasks){
			task.setAttribute('data-is-resolved', 'false');
			task.style.opacity = '100%';
			task.querySelector('.instructions').style.textDecoration = 'none';
		}	
	}
}


