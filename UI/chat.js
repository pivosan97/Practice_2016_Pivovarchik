function run(){
	document.getElementById("confirmNameButton").onclick = confirmButtomClicked;
	document.getElementById("sendMsgButton").onclick = sendMsgButtonClicked;

	addNewMsg("Hi", "Alex", "12:34:23", "");
	addNewMsg("Hi Alex", "Vadim", "12:35:12", "");
	setLatency(1);
}

function confirmButtomClicked(){
	var nameInput = document.getElementById("userNameInput");

	if(nameInput.value){
		setNewName(nameInput.value);
		nameInput.value = "";
	}
}

function setNewName(newName){
	if(newName){
		document.getElementById("userName").innerHTML = newName;
		resetMsgOwnship();
	}
}

function sendMsgButtonClicked(){
	var userNameDiv = document.getElementById("userName");
	var newMsgTextArea = document.getElementById("newMsgTextArea");

	if(!userNameDiv.innerHTML){
		window.alert("Please log in!");
		return;
	}

	if(newMsgTextArea.value){
		var date = new Date();
		var dateStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		addNewMsg(newMsgTextArea.value, userName.innerHTML, dateStr, "");
		newMsgTextArea.value = "";	
	}
}

function addNewMsg(text, author, date, status){
	if(!text || !author || !date){
		return;
	}

	var msgs = document.getElementById("chatOutDiv");
	var msg = createMsgDiv(text, author, date, status);
	msgs.appendChild(msg);
}

function createMsgDiv(text, author, date, status){
	var msgDiv = document.createElement("div");
	msgDiv.classList.add("msg");
	if(author == document.getElementById("userName").innerHTML){
		msgDiv.classList.add("my");	
	} else {
		msgDiv.classList.add("notmy");
	}

	var delButton = document.createElement("button");
	delButton.classList.add("msgAction");
	delButton.innerHTML = "D";
	delButton.onclick = deleteMsg;

	var modifButton = document.createElement("button");
        modifButton.classList.add("msgAction");
        modifButton.innerHTML = "M";
	modifButton.onclick = modifMsg;

	var textDiv = document.createElement("div");
	textDiv.classList.add("msgText");
	textDiv.innerHTML = text;
	
	var authorDiv = document.createElement("div");
	authorDiv.classList.add("msgAuthor");
	authorDiv.innerHTML = author;

	var timeDiv = document.createElement("div");
	timeDiv.classList.add("msgTime");
	timeDiv.innerHTML = date;

	var statusDiv = document.createElement("div");
	statusDiv.classList.add("msgstatus");
	statusDiv.innerHTML = status;

	msgDiv.appendChild(authorDiv);
	msgDiv.appendChild(delButton);
	msgDiv.appendChild(modifButton);
	msgDiv.appendChild(textDiv);
	msgDiv.appendChild(statusDiv);
	msgDiv.appendChild(timeDiv);
	return msgDiv;
}

function resetMsgOwnship(){
	var msgs = document.getElementById("chatOutDiv").getElementsByClassName("msg");
	var name = document.getElementById("userName").innerHTML;

	for(var i=0; i<msgs.length; i++) {
		var author = msgs[i].getElementsByClassName("msgAuthor")[0].innerHTML;
		if(author == name) {
			msgs[i].classList.remove("notmy");
			msgs[i].classList.add("my");
		} else {
			msgs[i].classList.remove("my");
			msgs[i].classList.add("notmy");
		}
	}
}

function modifMsg(){
	var msgDiv = this.parentNode;
	msgDiv.childNodes[3].innerHTML = "new msg text";
	msgDiv.childNodes[4].innerHTML = "modified";
}

function deleteMsg(){
	var msgDiv = this.parentNode;
	msgDiv.childNodes[3].innerHTML = "";
        msgDiv.childNodes[4].innerHTML = "removed";
	msgDiv.removeChild(msgDiv.childNodes[2]);
	msgDiv.removeChild(msgDiv.childNodes[1]);
}

function setLatency(state){
	var latInd = document.getElementById("latencyInd");
	switch(state) {
		case 1:
			latInd.className = "coolLat";
			break;
		case 2:
                        latInd.className = "normalLat";
                        break;
		case 3:
                        latInd.className = "badLat";
                        break;
		default:
			//error
	}
}
