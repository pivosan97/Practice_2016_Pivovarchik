var msgList = [];
var isModifMsg;

function run(){
	document.getElementById("confirmNameButton").onclick = confirmNameButtomClicked;
	document.getElementById("sendMsgButton").onclick = sendMsgButtonClicked;
	document.onkeyup = function (e) {
    					e = e || window.event;
    					if (e.keyCode === 13) {
        					sendMsgButtonClicked();
    					}
				}
	
	var msgsHist = loadHist();
	for(var i=0; msgsHist && i<msgsHist.length; i++){
		addNewMsgInLocal(msgsHist[i]);
		addNewMsgInHTML(msgsHist[i]);
	}

	var name = loadName();
	setCurName(name);

	updateLatency();
	isModifMsg = false;
}

function addNewMsgInLocal(msg){
	msgList.push(msg);
	saveHist(msgList);
}

function addNewMsgInHTML(msg){
	var msgDiv = createMsgDiv(msg);
	var msgsBoardDiv =  document.getElementById("chatOutDiv");
	msgsBoardDiv.appendChild(msgDiv);
	scrollDown();
}

function scrollDown() {
	var block = document.getElementById("chatOutDiv");
	block.scrollTop = block.scrollHeight;
}

function confirmNameButtomClicked(){
	if(isModifMsg){
		alert("Please finish message modification")
		return;
	}

	var nameInput = document.getElementById("userNameInput");

	if(nameInput.value) {
		if(nameInput.value == "x") {
			setCurName("");
		} else {
			setCurName(nameInput.value);
		}
		nameInput.value = "";
	} else {
		alert("Please enter not null name or 'x' to exit!")
	}
}

function setCurName(newName){
	document.getElementById("userName").innerHTML = newName;
	resetMsgOwnship();
	saveName(newName);
}

function getCurName() {
	var userNameDiv = document.getElementById("userName");
	return userNameDiv.innerHTML;
}

function sendMsgButtonClicked(){
	if(isModifMsg){
		alert("Please finish message modification")
		return;
	}

	var newMsgTextArea = document.getElementById("newMsgTextArea");

	if(!getCurName()){
		alert("Please log in!");
		return;
	}

	if(newMsgTextArea.value){
		var date = new Date();
		var dateStr = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

		var msg = createMsg(newMsgTextArea.value, getCurName(), dateStr, "");
		addNewMsgInHTML(msg);
		addNewMsgInLocal(msg);

		newMsgTextArea.value = "";	
	} else {
		alert("Please enter not null message!");
	}
}

function createMsgDiv(msg){
	var msgDiv = document.createElement("div");
	msgDiv.classList.add("msg");

	if(msg.author == document.getElementById("userName").innerHTML){
		msgDiv.classList.add("my");	
	} else {
		msgDiv.classList.add("notmy");
	}

	var delButton = createDelButton();
	var modifButton = createModifButton();
	var textDiv = createTextDiv(msg.text);
	var authorDiv = createAuthorDiv(msg.author);
	var dateDiv = createDateDiv(msg.date);
	var statusDiv = createStatusDiv(msg.status);

	msgDiv.setAttribute('msg-id', msg.id);
	msgDiv.appendChild(authorDiv);
	if(msg.status != "removed") {
		msgDiv.appendChild(delButton);
		msgDiv.appendChild(modifButton);
	}
	msgDiv.appendChild(textDiv);
	msgDiv.appendChild(statusDiv);
	msgDiv.appendChild(dateDiv);
	return msgDiv;
}

function resetMsgOwnship(){
	var msgs = document.getElementById("chatOutDiv").getElementsByClassName("msg");
	var name = getCurName();

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

function modifMsgButtonClicked(){
	if(isModifMsg){
		alert("Please finish message modification")
		return;
	}

	var msgDiv = this.parentNode;
	var curAuthor = getCurName();
	var msgAuthor = msgDiv.getElementsByClassName("msgAuthor")[0].innerHTML;

	if(curAuthor == msgAuthor) {
		var id = msgDiv.attributes['msg-id'].value;
		var oldText = msgDiv.getElementsByClassName("msgText")[0].innerHTML;
		showModifPopUp(id, oldText);
	} else {
		alert("You can`t modify not your message!")
	}
}

function showModifPopUp(id, oldText){
	var popUpDiv = document.createElement("div");
	popUpDiv.classList.add("modifMsgPopUp");
	popUpDiv.setAttribute('modif-msg-id', id);

	var modifMsgTextarea = createModifTextarea(oldText);
	var confirmModifButton = createConfirmModifButton();
	var cancelModifButton = createCancelModifButton();

	popUpDiv.appendChild(modifMsgTextarea);
	popUpDiv.appendChild(confirmModifButton);
	popUpDiv.appendChild(cancelModifButton);
	document.getElementById("centralDiv").appendChild(popUpDiv);
	isModifMsg = true;
}

function closeModifPopUp(){
	var popUpDiv = document.getElementsByClassName("modifMsgPopUp")[0];
	if(popUpDiv) {
		document.getElementById("centralDiv").removeChild(popUpDiv);
	}
}

function confirmModifButtonClicked(){
	var modifDiv = this.parentNode;

	var msgID = modifDiv.attributes['modif-msg-id'].value;
	var newText = modifDiv.getElementsByClassName("modifMsg")[0].value;
	
	if(newText) {
		modifMsgInLocal(msgID, newText, "modified");
		modifMsgInHTML(msgID, newText, "modified");
	} else {
		modifMsgInLocal(msgID, newText, "removed");
		modifMsgInHTML(msgID, newText, "removed");
	}

	closeModifPopUp();
	isModifMsg = false;
}

function cancelModifButtonClicked(){
	closeModifPopUp();
	isModifMsg = false;
}

function modifMsgInLocal(id, text, status) {
	for(var i=0; msgList && i<msgList.length; i++) {
		if(msgList[i].id == id) {
			msgList[i].text = text;
			msgList[i].status = status;	
			saveHist(msgList);
			return;
		}
	}
}

function modifMsgInHTML(msgID, text, status){
	msgDivList = document.getElementsByClassName("msg");
	for(var i=0; i<msgDivList.length; i++){
		id = msgDivList[i].attributes['msg-id'].value;
		if(id == msgID){
			msgDivList[i].childNodes[3].innerHTML = text;
        		msgDivList[i].childNodes[4].innerHTML = status;
			if(status == "removed") {
				msgDivList[i].removeChild(msgDivList[i].getElementsByClassName("msgAction")[1]);
				msgDivList[i].removeChild(msgDivList[i].getElementsByClassName("msgAction")[0]);
			}
			return;
		}
	}
}

function deleteMsgButtonClicked(){
	if(isModifMsg){
		alert("Please finish message modification")
		return;
	}

	var msgDiv = this.parentNode;
	var curAuthor = getCurName();
	var msgAuthor = msgDiv.getElementsByClassName("msgAuthor")[0].innerHTML;

	if(curAuthor == msgAuthor) {
		var id = msgDiv.attributes['msg-id'].value;
		modifMsgInLocal(id, "", "removed");
		modifMsgInHTML(id, "", "removed");
	} else {
		alert("You can`t remove not your message!");
	}
}

function updateLatency() {
	setLatency(1);
}

function saveName(newName) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem("Name", JSON.stringify(newName));
}

function loadName() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var name = localStorage.getItem("Name");
	return name && JSON.parse(name);
}

function saveHist(listToSave) {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem("Msgs list", JSON.stringify(listToSave));
}

function loadHist() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem("Msgs list");
	return item && JSON.parse(item);
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

function createMsg(t, a, d, s){
	if(!t || !a || !d){
		//error
		return null;
	}

	return {
		text : t,
		author : a,
		date : d,
		status : s,
		id : createID()
	};
}

function createID() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random).toString();
}

function createModifButton(){
	var modifButton = document.createElement("button");
        modifButton.classList.add("msgAction");
        modifButton.innerHTML = "M";
	modifButton.onclick = modifMsgButtonClicked;
	return modifButton;
}

function createDelButton()
{
	var delButton = document.createElement("button");
	delButton.classList.add("msgAction");
	delButton.innerHTML = "D";
	delButton.onclick = deleteMsgButtonClicked;
	return delButton;
}

function createTextDiv(text) {
	var textDiv = document.createElement("div");
	textDiv.classList.add("msgText");
	textDiv.innerHTML = text;
	return textDiv;
}

function createAuthorDiv(author) {
	var authorDiv = document.createElement("div");
	authorDiv.classList.add("msgAuthor");
	authorDiv.innerHTML = author;
	return authorDiv;
}

function createDateDiv(date) {
	var dateDiv = document.createElement("div");
	dateDiv.classList.add("msgTime");
	dateDiv.innerHTML = date;
	return dateDiv;
}

function createStatusDiv(status) {
	var statusDiv = document.createElement("div");
	statusDiv.classList.add("msgstatus");
	statusDiv.innerHTML = status;
	return statusDiv;
}

function createModifTextarea(oldText){
	var modifMsgTextArea = document.createElement("textarea");
	modifMsgTextArea.classList.add("modifMsg");
	modifMsgTextArea.value = oldText;
	return modifMsgTextArea;
}

function createConfirmModifButton(){
	var confirmModifButton = document.createElement("button");
	confirmModifButton.classList.add("modifAction");
	confirmModifButton.innerHTML = "Confirm";
	confirmModifButton.onclick = confirmModifButtonClicked;
	return confirmModifButton;
}

function createCancelModifButton(){
	var cancelModifButton = document.createElement("button");
	cancelModifButton.classList.add("modifAction");
	cancelModifButton.innerHTML = "Cancel";
	cancelModifButton.onclick = cancelModifButtonClicked;
	return cancelModifButton;
}