function toggleDropdown() {
    document.getElementById("menu").classList.toggle("show");
}

window.onclick = function (e) {
    if (!e.target.closest('.login-wel')) {
        document.getElementById("menu").classList.remove("show");
    }
}