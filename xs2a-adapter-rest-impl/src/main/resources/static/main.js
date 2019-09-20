setTimeout(function () {
    initGlobals();
    document.querySelector("#add>button").addEventListener("click", addRow);
}, 0)

function initGlobals() {
    window.HIDDEN_ROW = document.querySelector("tbody>tr.hidden");
    window.COUNTER = 0;
}

function uneditableCells(e) {
    let rowCells = e.parentElement.parentElement.cells;

    for (let i = 1, till = (rowCells.length - 1); i < till; i++) {
        rowCells[i].removeAttribute("contenteditable");
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

function editableCells(e) {
    let rowCells = e.parentElement.parentElement.cells;

    for (let i = 1, till = (rowCells.length - 1); i < till; i++) {
        rowCells[i].setAttribute("contenteditable", true);
    }
}

function search() {
    clearTable();

    let data = document.querySelector(".search-form");
    let url = "/v1/aspsps/?";

    if (data[0].value !== "")
        url += "name=" + data[0].value + "&";

    if (data[1].value !== "")
        url += "bic=" + data[1].value + "&";

    if (data[2].value !== "")
        url += "bankCode=" + data[2].value;

    fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }
            return response;
        }).then(response => response.text())
        .then(response => JSON.parse(response).forEach((node) => buildRow(node)))
        .catch(function (error) {
            console.log(error);
        });

    if (HIDDEN_ROW.parentElement.parentElement.hidden) {
        showTable();
    }
}

function addRow() {
    let editId = "edit-";
    let updateId = "update-";
    let deleteId = "delete-";

    let clone = HIDDEN_ROW.cloneNode(true);
    clone.cells[0].textContent = uuid();
    clone.removeAttribute("class");
    clone.setAttribute("class", "new");
    clone.lastElementChild.childNodes.forEach(e => {
        if (e.className) {
            if (e.className.indexOf("edit") > -1) {
                let helper = e.parentNode.childNodes[7];

                e.addEventListener("click", () => { editButton(e) })
                e.setAttribute("id", editId + COUNTER);

                helper.setAttribute("data-mdl-for", editId + COUNTER);
                editButton(e);
            }

            if (e.className.indexOf("update") > -1) {
                let helper = e.parentNode.childNodes[9];

                e.addEventListener("click", () => { greenButton(e) })
                e.setAttribute("id", updateId + COUNTER);

                helper.setAttribute("data-mdl-for", updateId + COUNTER);
            }

            if (e.className.indexOf("delete") > -1) {
                let helper = e.parentNode.childNodes[11];

                e.addEventListener("click", () => { redButton(e) })
                e.setAttribute("id", deleteId + COUNTER);

                helper.setAttribute("data-mdl-for", deleteId + COUNTER);
            }
        }
    });
    document.querySelector("table>tbody").appendChild(clone)

    COUNTER++;

    if (HIDDEN_ROW.parentElement.parentElement.hidden) {
        showTable();
    }
}

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

function greenButton(e) {
    let tableRow = e.parentElement.parentElement;

    if (tableRow.className) {
        if (window.confirm("Are you sure an aspsp has been built right?")) {
            saveButton(e);
        } else {
            return;
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
    }).catch(function (error) {
        console.log(error);
    });
}

function updateButton(e) {
    let editButton = e.parentNode.children[0];
    let updateOrSaveButton = e.parentNode.children[1];
    let deleteButton = e.parentNode.children[2];

    let row = e.parentNode.parentNode;

    let uri = "/v1/aspsps/";

    let id = "{\"id\": \"" + row.cells[0].textContent + "\",\n";
    let bankName = "\"name\": \"" + row.cells[1].textContent + "\",\n";
    let bic = "\"bic\": \"" + row.cells[2].textContent + "\",\n";
    let url = "\"url\": \"" + row.cells[3].textContent + "\",\n";
    let adapterId = "\"adapterId\": \"" + row.cells[4].textContent + "\",\n";
    let bankCode = "\"bankCode\": \"" + row.cells[5].textContent + "\"}";

    let data = id + bankName + bic + url + adapterId + bankCode;

    fetch(uri, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: data
    }).then((response) => {
        if (!response.ok) {
            throw Error(response.statusText);
        }
        return response;
    }).then(response => {
        if (response.ok) {
            if (editButton.style.display === "none") {
                editButton.style.display = "inherit";
                updateOrSaveButton.style.display = "none";
                deleteButton.style.display = "none";
                uneditableCells(e);
            }
        }
    }).catch(function (error) {
        console.log(error);
    });;
}

function saveButton(e) {
    let editButton = e.parentNode.children[0];
    let updateOrSaveButton = e.parentNode.children[1];
    let deleteButton = e.parentNode.children[2];

    let row = e.parentNode.parentNode;

    let uri = "/v1/aspsps/";

    let id = "{\"id\": \"" + row.cells[0].textContent + "\",\n";
    let bankName = "\"name\": \"" + row.cells[1].textContent + "\",\n";
    let bic = "\"bic\": \"" + row.cells[2].textContent + "\",\n";
    let url = "\"url\": \"" + row.cells[3].textContent + "\",\n";
    let adapterId = "\"adapterId\": \"" + row.cells[4].textContent + "\",\n";
    let bankCode = "\"bankCode\": \"" + row.cells[5].textContent + "\"}";

    let data = id + bankName + bic + url + adapterId + bankCode;

    fetch(uri, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: data
    }).then((response) => {
        if (response.status !== 201) {
            throw Error(response.statusText);
        }
        row.removeAttribute("class");
        return response;
    }).then(response => {
        if (response.status === 201) {
            if (editButton.style.display === "none") {
                editButton.style.display = "inherit";
                updateOrSaveButton.style.display = "none";
                deleteButton.style.display = "none";
                uneditableCells(e);
            }
        }
    }).catch(function (error) {
        console.log(error);
    });
}

function purgeRow(e) {
    let tableRow = e.parentElement.parentElement;

    tableRow.remove();
}

function buildRow(data) {
    let editId = "edit-";
    let updateId = "update-";
    let deleteId = "delete-";

    let clone = HIDDEN_ROW.cloneNode(true);
    clone.removeAttribute("class");

    clone.cells[0].textContent = data.id;
    clone.cells[1].textContent = data.name;
    clone.cells[2].textContent = data.bic;
    clone.cells[3].textContent = data.url;
    clone.cells[4].textContent = data.adapterId;
    clone.cells[5].textContent = data.bankCode;

    clone.lastElementChild.childNodes.forEach(e => {
        if (e.className) {
            if (e.className.indexOf("edit") > -1) {
                let helper = e.parentNode.childNodes[7];

                e.addEventListener("click", () => { editButton(e) })
                e.setAttribute("id", editId + COUNTER);

                helper.setAttribute("data-mdl-for", editId + COUNTER);
            }

            if (e.className.indexOf("update") > -1) {
                let helper = e.parentNode.childNodes[9];

                e.addEventListener("click", () => { greenButton(e) })
                e.setAttribute("id", updateId + COUNTER);

                helper.setAttribute("data-mdl-for", updateId + COUNTER);
            }

            if (e.className.indexOf("delete") > -1) {
                let helper = e.parentNode.childNodes[11];

                e.addEventListener("click", () => { redButton(e) })
                e.setAttribute("id", deleteId + COUNTER);

                helper.setAttribute("data-mdl-for", deleteId + COUNTER);
            }
        }
    });
    document.querySelector("table>tbody").appendChild(clone)

    COUNTER++;
}

function showTable() {
    let table = HIDDEN_ROW.parentElement.parentElement;
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

//# sourceMappingURL=main.js.map
