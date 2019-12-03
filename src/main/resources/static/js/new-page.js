var groupSelect = document.getElementById("groupSelect");
var submitForm = document.getElementById("submitForm");
var newGroupInput = document.getElementById("newGroupInput");
var urlInput = document.getElementById("urlInput");

newGroupInput.addEventListener("input", urlInputCheck);
urlInput.addEventListener("input", urlInputCheck);

function urlInputCheck() {
    if (urlInput.value.length > 0 && groupSelect.options.length > 0 && newGroupInput.value.length > 0)
        submitForm.removeAttribute("disabled");
    else
        submitForm.setAttribute("disabled", "true");
}

function newGroup() {
    var groupLabel = document.getElementById("groupLabel");
    var newGroupLabel = document.getElementById("newGroupLabel");
    var newGroupBtn = document.getElementById("newGroupBtn");

    newGroupLabel.style.display = "block";
    groupLabel.style.display = "none";
    newGroupBtn.style.display = "none";

    if (groupSelect.options.length === 0)
        groupSelect.options.add(new Option("", "-1", false, true));

    groupSelect.options[groupSelect.selectedIndex].value = "-1";
    urlInputCheck();
}
