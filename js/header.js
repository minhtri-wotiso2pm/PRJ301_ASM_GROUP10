document.body.classList.add("scrolled");

window.onscroll = function () {
    var box = document.getElementById("box");
    var button = document.querySelectorAll(".login");
    var user = document.getElementById("user-logo-login");
    var menu = document.querySelector(".menu");
    var loginButton = document.querySelector("a.login");

    let isScrolled = window.scrollY > 0;

    if (isScrolled) {
        box.classList.add("white-bg");
        button.forEach(btn => btn.classList.add("white-bg"));
        menu.classList.remove("border");
        if (loginButton) loginButton.classList.add("scrolled-border");
        if (user) user.src = "assets/images/user-blue.png";
    } else {
        box.classList.remove("white-bg");
        button.forEach(btn => btn.classList.remove("white-bg"));
        menu.classList.add("border");
        if (loginButton) loginButton.classList.remove("scrolled-border");
        if (user) user.src = "assets/images/user-white.png";
    }

    for (let i = 0; i < button.length; i++) {
        button[i].classList.remove("hover-black", "hover-white");
        if (isScrolled) {
            button[i].classList.add("hover-white");
        } else {
            button[i].classList.add("hover-black");
        }
    }
};

function toggleDropdown() {
    document.querySelector(".dropdown-menu").classList.toggle("show");
}

window.onclick = function (e) {
    if (!e.target.closest('.login-wel')) {
        document.querySelector(".dropdown-menu").classList.remove("show");
    }
}

window.addEventListener("load", function () {
    window.onscroll(); // Khởi động scroll effect
});