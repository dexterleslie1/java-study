window.sessionStorageOnSave = function() {
    let value = document.getElementById("sessionStorageValue").value;
    sessionStorage.setItem("sessionStorageKey", value);
}

window.sessionStorageOnRead = function() {
    let value = sessionStorage.getItem("sessionStorageKey");
    alert("sessionStorage值为：" + value);
}

window.localStorageOnSave = function() {
    let value = document.getElementById("localStorageValue").value;
    localStorage.setItem("localStorageKey", value);
}

window.localStorageOnRead = function() {
    let value = localStorage.getItem("localStorageKey");
    alert("localStorage值为：" + value);
}