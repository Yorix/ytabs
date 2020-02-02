const groupSelect = document.getElementById("groupSelect");
const submitForm = document.getElementById("submitForm");
const newGroupInput = document.getElementById("newGroupInput");
const urlInput = document.getElementById("urlInput");
const newGroupLabel = document.getElementById("newGroupLabel");
const request = new XMLHttpRequest();
const thumbWrapper = document.getElementById("thumb");
let timerId;

newGroupInput.addEventListener("input", urlInputCheck);
urlInput.addEventListener("input", urlInputCheck);


request.onload = function () {
    const thumbString = this.response;
    let thumbs = thumbString.substring(1, thumbString.length).split(",");
    let html = '';
    if (thumbs.length === 0 || thumbs[0].substring(0, 5) !== "\"http")
        html = 'no images';
    else
        for (let i = 0; i < thumbs.length; i++)
            html = html
                .concat('<div class="col-4 my-1 text-center thumb"><img src=')
                .concat(thumbs[i])
                .concat(' style="width:100%"/></div>');
    thumbWrapper.innerHTML = html;
};

function sendRequest(url) {
    thumbWrapper.innerHTML = "loading...";
    const form = new FormData();
    form.append("url", url);

    request.open("POST", "/url/get-logo/");
    request.responseType = "text";
    request.send(form);
}

function urlInputCheck() {
    timerId = setTimeout(sendRequest, 1000, urlInput.value);

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
