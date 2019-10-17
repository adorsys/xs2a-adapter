setTimeout(function () {
    initGlobals();
    document.querySelector("#import>button").addEventListener("click", () => FILE_UPLOAD_FIELD.click());
    FILE_UPLOAD_FIELD.addEventListener("change", upload);
    document.querySelectorAll(".mdl-textfield__input").forEach(field => field.addEventListener('keypress', event => onEnterPress(event)));
}, 0);

function initGlobals() {
    window.FILE_UPLOAD_FIELD = document.querySelector("#import-field");
    window.HIDDEN_ROW = document.querySelector("tbody>tr.hidden");
    window.WARNING = document.querySelector(".alert.warning");
    window.FAILURE = document.querySelector(".alert.failure");
    window.SUCCESS = document.querySelector(".alert.success");
    window.COUNTER = 0;
}

function validateBankName(element) {
    let target = element.textContent;
    let regex = /^[a-zA-Z0-9-\s]*$/;

    if (!(regex.test(target) || target === "")) {
        element.style.background = "rgba(255, 152, 0, 0.2)";
        element.classList.add("invalid");
        warning("Bank name should be a plain text, e.g. there shouldn't be symbols like #, @, *, %, etc.");
    } else {
        element.style.background = "none";
        element.classList.remove("invalid");
    }
}

function validateBic(element) {
    toUpper(element);
    let target = element.textContent;
    let regex = /^[A-Z0-9]*$/;
    let length = 11;

    if (!((target.length === length && regex.test(target)) || target === "")) {
        element.style.background = "rgba(255, 152, 0, 0.2)";
        element.classList.add("invalid");
        warning("BIC should be 11 characters long and consist of aA-zZ, 0-9");
    } else {
        element.style.background = "none";
        element.classList.remove("invalid");
    }
}

function validateUrl(element) {
    let target = element.textContent;
    let regex = /^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/;

    if (!(regex.test(target) || target === "")) {
        element.style.background = "rgba(255, 152, 0, 0.2)";
        element.classList.add("invalid");
        warning("URL format is wrong, e.g. right format is https://example.test");
    } else {
        element.style.background = "none";
        element.classList.remove("invalid");
    }
}

function validateAdapterId(element) {
    let target = element.textContent;
    let regex = /^[a-zA-Z0-9-]*$/;

    if (!(regex.test(target) || target === "")) {
        element.style.background = "rgba(255, 152, 0, 0.2)";
        element.classList.add("invalid");
        warning("Adapter Id should consist of aA-zZ, 0-9 and a hyphen(-) only, e.g. 'Adapter-12345'");
    } else {
        element.style.background = "none";
        element.classList.remove("invalid");
    }
}

function validateBankCode(element) {
    let target = element.textContent;
    let regex = /^[0-9]*$/;
    let length = 8;

    if (!((target.length === length && regex.test(target)) || target === "")) {
        element.style.background = "rgba(255, 152, 0, 0.2)";
        element.classList.add("invalid");
        warning("Bank Code should consist of numbers only");
    } else {
        element.style.background = "none";
        element.classList.remove("invalid");
    }
}

function toUpper(element) {
    element.innerText = element.innerText.toUpperCase();
}

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        let r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function forceValidation() {
    let rows = document.querySelectorAll("tr");

    rows.forEach((row) => {
        for (let cell of row.cells) {
            if (cell.classList.contains("invalid")) {
                cell.classList.remove("invalid");
                cell.style.background = "none";
                console.log("Cell with content '" + cell.textContent + "' is made valid");
            }
        }
    })
}

// Manipulating cells
function uneditableCells(e) {
    let rowCells = e.parentElement.parentElement.cells;
    let approach = e.parentElement.parentElement.querySelectorAll('input');

    for (let i = 1, till = (rowCells.length - 1); i < till; i++) {
        rowCells[i].removeAttribute("contenteditable");
    }

    approach.forEach(element => {
        element.disabled = true;
    })
}

function editableCells(e) {
    let rowCells = e.parentElement.parentElement.cells;
    let approach = e.parentElement.parentElement.querySelectorAll('input');

    for (let i = 1, till = (rowCells.length - 2); i < till; i++) {
        rowCells[i].setAttribute("contenteditable", true);

    }

    approach.forEach(element => {
        element.disabled = false;
    })
}
// End of cells part

// Manipulating tables
function showTable() {
    let table = HIDDEN_ROW.parentElement.parentElement.parentElement;
    let message = document.querySelector(".welcome-message");

    table.hidden = false;
    message.hidden = true;
}

function clearTable() {
    let body = document.querySelectorAll("tbody>tr");

    if (body.length > 1) {
        body.forEach(e => { if (!e.className) { e.remove(); } })
    }
}

function clearContent() {
    clearTable();

    document.querySelectorAll(".mdl-textfield__input").forEach(element => { element.value = ""; element.parentElement.classList.remove("is-dirty") });
}
// End of table part

function onEnterPress(event) {
    if (event.keyCode === 13) {
        search();
    }
}

function addTooltips(e) {
    let editId = "edit-";
    let updateId = "update-";
    let deleteId = "delete-";

    if (e.className.indexOf("edit") > -1) {
        let helper = e.parentNode.childNodes[7];

        e.addEventListener("click", () => { editButton(e) });
        e.setAttribute("id", editId + COUNTER);

        helper.setAttribute("data-mdl-for", editId + COUNTER);
        helper.setAttribute("class", "mdl-tooltip mdl-tooltip--top");
    }

    if (e.className.indexOf("update") > -1) {
        let helper = e.parentNode.childNodes[9];

        e.addEventListener("click", () => { greenButton(e) });
        e.setAttribute("id", updateId + COUNTER);

        helper.setAttribute("data-mdl-for", updateId + COUNTER);
        helper.setAttribute("class", "mdl-tooltip mdl-tooltip--top");
    }

    if (e.className.indexOf("delete") > -1) {
        let helper = e.parentNode.childNodes[11];

        e.addEventListener("click", () => { redButton(e) });
        e.setAttribute("id", deleteId + COUNTER);

        helper.setAttribute("data-mdl-for", deleteId + COUNTER);
        helper.setAttribute("class", "mdl-tooltip mdl-tooltip--top");
    }
}

// Manipulating rows
function purgeRow(e) {
    let tableRow = e.parentElement.parentElement;

    tableRow.remove();
}

function assembleRowData(e) {
    let row = e.parentNode.parentNode;

    let object = {};
    object.id = row.cells[0].textContent;
    object.name = row.cells[1].textContent;
    object.bic = row.cells[2].textContent;
    object.url = row.cells[3].textContent;
    object.adapterId = row.cells[4].textContent;
    object.bankCode = row.cells[5].textContent;
    object.idpUrl = row.cells[6].textContent;
    object.scaApproaches = approachParser(row.cells[7]);

    return JSON.stringify(object);

    function approachParser(data) {
        let inputs = data.querySelectorAll("input");
        let resultString = [];

        inputs.forEach((element) => {
            if (element.checked) {
                resultString.push(element.name);
            }
        });

        return resultString;
    }
}

function buildRow(data) {
    let clone = HIDDEN_ROW.cloneNode(true);
    clone.removeAttribute("class");

    clone.cells[0].textContent = data.id;
    clone.cells[1].textContent = data.name;
    clone.cells[2].textContent = data.bic;
    clone.cells[3].textContent = data.url;
    clone.cells[4].textContent = data.adapterId;
    clone.cells[5].textContent = data.bankCode;
    clone.cells[6].textContent = data.idpUrl;
    approachParser(data.scaApproaches, clone.cells[7]);

    clone.lastElementChild.childNodes.forEach(e => {
        if (e.className) {
            addTooltips(e)
        }
    });
    document.querySelector("table>tbody").appendChild(clone);

    COUNTER++;

    // updating MDL library for making Tooltip working
    componentHandler.upgradeAllRegistered();

    function approachParser(data, cell) {
        if (!data) {
            return;
        }

        let inputs = cell.querySelectorAll("input");

        data.forEach(element => {
            switch (element) {
                case "EMBEDDED":
                    inputs[0].checked = true;
                    break;
                case "REDIRECT":
                    inputs[1].checked = true;
                    break;
                case "DECOUPLED":
                    inputs[2].checked = true;
                    break;
                case "OAUTH":
                    inputs[3].checked = true;
                    break;
            }
        })
    }
}
// End of row part

function fail(message) {
    let messageBlock = FAILURE.querySelector(".message");
    messageBlock.textContent = message;

    setTimeout(() => { FAILURE.style.opacity = 1 }, 500);

    setTimeout(() => { FAILURE.style.opacity = 0 }, 8000);
}

function success() {
    setTimeout(() => { SUCCESS.style.opacity = 1 }, 500);

    setTimeout(() => { SUCCESS.style.opacity = 0 }, 8000);
}

function warning(message) {
    let messageBlock = WARNING.querySelector(".message");

    if (message) {
        messageBlock.textContent = message;
    }

    setTimeout(() => { WARNING.style.opacity = 1 }, 500);

    setTimeout(() => { WARNING.style.opacity = 0 }, 8000);
}

function addRow() {
    let clone = HIDDEN_ROW.cloneNode(true);
    clone.cells[0].textContent = uuid();
    clone.removeAttribute("class");
    clone.setAttribute("class", "new");
    clone.lastElementChild.childNodes.forEach(e => {
        if (e.className) {
            addTooltips(e);
            editButton(e);
        }
    });
    document.querySelector("table>tbody").appendChild(clone);

    COUNTER++;

    if (HIDDEN_ROW.parentElement.parentElement.parentElement.hidden) {
        showTable();
    }
    // updating MDL library for making Tooltip working
    componentHandler.upgradeAllRegistered();
}

// Requests part
function saveButton(e) {
    let row = e.parentElement.parentElement;

    for (let cell of row.cells) {
        if (cell.classList.contains("invalid")) {
            warning();
            return;
        }
    }

    fetch("/v1/aspsps/", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: assembleRowData(e)
    }).then((response) => {
        if (response.status !== 201) {
            throw RangeError(response.statusText);
        }
        row.removeAttribute("class");
        toggleButtons(e);
        return response.text();
    }).then(response => {
        let output = JSON.parse(response);
        row.cells[0].textContent = output.id;
    }).catch(() => {
        fail("Saving process has failed");
    });
}

function updateButton(e) {
    let row = e.parentElement.parentElement;

    for (let cell of row.cells) {
        if (cell.classList.contains("invalid")) {
            warning();
            return;
        }
    }

    fetch("/v1/aspsps/", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: assembleRowData(e)
    }).then((response) => {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response;
    }).then(response => {
        if (response.ok) {
            toggleButtons(e);
        }
    }).catch(() => {
        fail("Update process has failed");
    });
}

function deleteButton(e) {
    let uuidCell = e.parentElement.parentElement.cells[0].innerText;
    let url = "/v1/aspsps/" + uuidCell;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        if (response.status !== 204) {
            throw Error(response.statusText);
        }
        purgeRow(e);
        return response;
    }).catch(() => {
        fail("Deleting process has fialed");
    });
}

function persist() {
    fetch("v1/aspsps/csv/persist", {
        method: "POST"
    }).then(response => {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        success();
    }).catch(() => {
        fail("Could not update Lucene indexes");
    })
}

function upload() {
    let file = FILE_UPLOAD_FIELD.files[0];
    let data = new FormData();

    data.append("file", file);

    fetch("/v1/aspsps/csv/import", {
        method: 'POST',
        body: data
    }).then(response => {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        success();
    }).catch(() => {
        fail("Failed to upload the file. It looks like the file has an inappropriate format.");
    })
}

function search() {
    clearTable();

    let data = document.querySelector(".search-form");
    let url = "/v1/aspsps/?";

    if (data[0].value !== "")
        url += "name=" + data[0].value.toLowerCase() + "&";

    if (data[1].value !== "")
        url += "bic=" + data[1].value + "&";

    if (data[2].value !== "")
        url += "bankCode=" + data[2].value + "&";

    url += "size=9999";

    fetch(url).then((response) => {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response;
    }).then(response => response.text()
    ).then(response => paginate(JSON.parse(response))
    ).catch(() => {
        fail("Failed to find any records. Please double check input parameters.");
    });

    if (HIDDEN_ROW.parentElement.parentElement.parentElement.hidden) {
        showTable();
    }
}
// End of requests part

function editButton(e) {
    let editButton = e.parentNode.children[0];
    let updateOrSaveButton = e.parentNode.children[1];
    let deleteButton = e.parentNode.children[2];

    if (editButton.style.display !== "none") {
        editButton.style.display = "none";
        updateOrSaveButton.style.display = "inherit";
        deleteButton.style.display = "inherit";
        editableCells(e);
    }
}

function toggleButtons(e) {
    let editButton = e.parentNode.children[0];
    let updateOrSaveButton = e.parentNode.children[1];
    let deleteButton = e.parentNode.children[2];

    if (editButton.style.display === "none") {
        editButton.style.display = "inherit";
        updateOrSaveButton.style.display = "none";
        deleteButton.style.display = "none";
        uneditableCells(e);
    } else {
        editButton.style.display = "none";
        updateOrSaveButton.style.display = "inherit";
        deleteButton.style.display = "inherit";
        editableCells(e);
    }
}

function greenButton(e) {
    let tableRow = e.parentElement.parentElement;

    if (tableRow.className) {
        if (window.confirm("Are you sure you want to save the new entry?")) {
            saveButton(e);
        }
    } else {
        if (window.confirm("Are you sure you want to update the aspsp?")) {
            updateButton(e);
        } else {
            toggleButtons(e);
        }
    }
}

function redButton(e) {
    let tableRow = e.parentElement.parentElement;

    if (tableRow.className) {
        purgeRow(e);
    } else {
        if (window.confirm("You you sure you want to delete this aspsp record?")) {
            deleteButton(e);
        } else {
            toggleButtons(e);
        }
    }
}

function paginate(data) {
    let dataLength = data.length;
    let step = 10;
    let current = 0;
    let button = document.querySelector(".show-more");
    let total = document.querySelector(".total");

    addPage();
    total.innerHTML = dataLength;

    function addPage() {
        for (let limit = current + Math.min(step, dataLength); current < limit; current++) {
            buildRow(data[current]);
        }
        button.hidden = current >= dataLength;
    }

    button.addEventListener('click', addPage);
}
