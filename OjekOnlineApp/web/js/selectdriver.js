var prefDriver = document.getElementById("pref-driver");
var otherDriver = document.getElementById("other-driver");
var driverSumRating = [];
var driverCountRating = [];
var driverNameList = [];

for (var i = 0; i < driverArr.length; i++) {
    for(var j = 0; j < ratingArr.length; j++) {
        if(driverArr[i] === ratingArr[j]["usernameDriver"]){
            driverSumRating[driverArr[i]] = (driverSumRating[driverArr[i]] === undefined) ? parseFloat(ratingArr[j]["rating"]) : driverSumRating[driverArr[i]] + parseFloat(ratingArr[j]["rating"]);
            driverCountRating[driverArr[i]] = (driverCountRating[driverArr[i]] === undefined) ? 1 : driverCountRating[driverArr[i]]+1;
        }
    }
    for(var k = 0; k < driverNameArr.length; k++) {
        if(driverArr[i] === driverNameArr[k]["username"]){
            driverNameList[driverArr[i]] = driverNameArr[k]["fullname"];
            break;
        }
    }
}

for(var j = 0; j < ratingArr.length; j++) {
    if(preferredDriver === ratingArr[j]["usernameDriver"]){
        driverSumRating[preferredDriver] = (driverSumRating[preferredDriver] === undefined) ? parseFloat(ratingArr[j]["rating"]) : driverSumRating[preferredDriver] + parseFloat(ratingArr[j]["rating"]);
        driverCountRating[preferredDriver] = (driverCountRating[preferredDriver] === undefined) ? 1 : driverCountRating[preferredDriver]+1;
    }
}
for(var k = 0; k < driverNameArr.length; k++) {
    if(preferredDriver === driverNameArr[k]["username"]){
        driverNameList[preferredDriver] = driverNameArr[k]["fullname"];
        break;
    }
}

if(preferredDriver !== "") {
    CreateDriverDisplay(prefDriver, preferredDriver);
} else {
    CreateNotFoundDisplay(prefDriver);
}

if(driverArr.length > 0) {
    for (var i = 0; i < driverArr.length; i++) {
        CreateDriverDisplay(otherDriver, driverArr[i]);
    }
} else {
    CreateNotFoundDisplay(otherDriver);
}

function CreateDriverDisplay(driverType, driverUsrName) {
    var newForm = driverType.appendChild(document.createElement("form"));
    var newContainer = newForm.appendChild(document.createElement("div"));
    var newImg = newContainer.appendChild(document.createElement("img"));
    var newName = newContainer.appendChild(document.createElement("div"));
    var newRating = newContainer.appendChild(document.createElement("div"));
    var postUsrName = newContainer.appendChild(document.createElement("input"));
    var newPickLoc = newContainer.appendChild(document.createElement("input"));
    var newDest = newContainer.appendChild(document.createElement("input"));
    var newBtn = newContainer.appendChild(document.createElement("input"));


    var avgRating = (driverSumRating[driverUsrName]/driverCountRating[driverUsrName]).toFixed(1).toString();

    newForm.setAttribute('method', 'post');
    newForm.setAttribute('action', '/order/' + user + '/completeorder');
    newContainer.classList.add("driver-content");

    newImg.classList.add("driver-pic");
    newImg.setAttribute('src', '/public/img/' + driverUsrName);

    newName.classList.add("driver-name-disp");
    newName.innerHTML = driverNameList[driverUsrName];

    newRating.classList.add("driver-rating-disp");
    newRating.innerHTML = "★" + avgRating + " <span class='vote-disp'>(" + driverCountRating[driverUsrName] + " votes)</span>";

    postUsrName.setAttribute('type', 'hidden');
    postUsrName.setAttribute('name', 'driver-username');
    postUsrName.setAttribute('value', driverUsrName);

    newPickLoc.setAttribute('type','hidden');
    newPickLoc.setAttribute('name','pickLoc');
    newPickLoc.setAttribute('value', pickLoc);
    newDest.setAttribute('type','hidden');
    newDest.setAttribute('name','dest');
    newDest.setAttribute('value', dest);

    newBtn.classList.add("accept-button");
    newBtn.classList.add("select-driver-btn");
    newBtn.setAttribute('type','submit');
    newBtn.setAttribute('value','Select Driver');
}

function CreateNotFoundDisplay(driverType) {
    var newContainer = driverType.appendChild(document.createElement("div"));
    newContainer.classList.add("driver-not-found");
    newContainer.innerHTML = "Nothing to display.";
}