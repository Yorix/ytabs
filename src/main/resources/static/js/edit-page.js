const groupSelect = document.getElementById("groupSelect");
const submitForm = document.getElementById("submitForm");
const newGroupInput = document.getElementById("newGroupInput");
const urlInput = document.getElementById("urlInput");
const imgUrlInput = document.getElementById("imgUrlInput");
const newGroupLabel = document.getElementById("newGroupLabel");
const request = new XMLHttpRequest();
const imagesWrapper = document.getElementById("images");
let html;

newGroupInput.addEventListener("input", urlInputCheck);
urlInput.addEventListener("input", urlInputCheck);

urlInputCheck();

request.onload = function () {
    const imagesString = this.response;
    const images = imagesString.substring(1, imagesString.length - 1).split(",");
    html = '<div class="col-12"><img src="' + imgFilename + '" class="page-image" alt="alt"/></div>';
    if (images.length === 0 || images[0].substring(0, 5) !== "\"http")
        html = html.concat('no images');
    else
        for (let i = 0; i < images.length; i++)
            html = html
                .concat('<div class="col-3 my-1 text-center thumbnail"><img src=')
                .concat(images[i])
                .concat(' onclick="setImage(\'')
                .concat(images[i].replace(/"/g, ""))
                .concat('\')" style="width:100%" alt="alt"/></div>');
    imagesWrapper.innerHTML = html;
};

function setImage(imageUrl) {
    imgFilename = imageUrl;
    imgUrlInput.value = imageUrl;
    html = '<div class="col-12"><img src="' + imgFilename + '" class="page-image" alt="alt"/></div>';
    imagesWrapper.innerHTML = html;
    sendRequest(urlInput.value);
}

function sendRequest(url) {
    imagesWrapper.innerHTML = html.concat("<div>loading...</div>");
    request.open("get", "/img/get-all/" + url);
    request.responseType = "text";
    request.send();
}

function urlInputCheck() {
    html = '<div class="col-12"><img src="' + imgFilename + '" class="page-image" alt="alt"/></div>';
    imagesWrapper.innerHTML = html;
    sendRequest(urlInput.value);

    if (
        urlInput.value.length > 0 &&
        groupSelect.options.length > 0 &&
        (newGroupLabel.style.display !== "none" && newGroupInput.value.length > 0 ||
            newGroupLabel.style.display === "none")
    )
        submitForm.removeAttribute("disabled");
    else
        submitForm.setAttribute("disabled", "true");
}

function newGroup() {
    const groupLabel = document.getElementById("groupLabel");
    const newGroupBtn = document.getElementById("newGroupBtn");

    newGroupLabel.style.display = "block";
    groupLabel.style.display = "none";
    newGroupBtn.style.display = "none";

    if (groupSelect.options.length === 0)
        groupSelect.options.add(new Option("", "-1", false, true));

    groupSelect.options[groupSelect.selectedIndex].value = "-1";

    urlInputCheck();
}
