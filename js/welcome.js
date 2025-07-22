document.body.classList.add("scrolled");
window.onscroll = function () {
    var box = document.getElementById("box");
    var font = document.querySelectorAll(".font-img a, .menu a, .login-wel,.link-button");
    var logo = document.getElementById("logo-loka");
    var button = document.querySelectorAll(".login");
    var user = document.getElementById("user-logo-login");
    var menu = document.querySelector(".menu");
    var loginButton = document.querySelector("a.login");
    let isScrolled = window.scrollY > 0;
    if (isScrolled) {
        box.classList.add("white-bg");
        button.forEach(btn => btn.classList.add("white-bg"));
        menu.classList.remove("border");
        if (loginButton)
            loginButton.classList.add("scrolled-border");
        if (user)
            user.src = "assets/images/user-blue.png";
        logo.src = "assets/images/d4969644-5d07-445a-b9ba-b38d158b34ce-removebg-preview.png";
    } else {
        box.classList.remove("white-bg");
        button.forEach(btn => btn.classList.remove("white-bg"));
        menu.classList.add("border");
        if (loginButton)
            loginButton.classList.remove("scrolled-border");
        if (user)
            user.src = "assets/images/user-white.png";
        logo.src = "assets/images/logo_white.png";
    }
    if (font) {
        for (let i = 0; i < font.length; i++) {
            font[i].classList.remove("hover-black", "hover-white", "text-black", "text-white");
            if (isScrolled) {
                font[i].classList.add("hover-white", "text-black");
            } else {
                font[i].classList.add("hover-black", "text-white");
            }
        }
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

function switchSearchForm(type) {
    const searchHotel = document.querySelector('.search-hotel');
    const searchPlane = document.querySelector('.search-plane');
    if (type === 'hotel') {
        searchHotel.style.display = 'block';
        searchPlane.style.display = 'none';
    } else {
        searchHotel.style.display = 'none';
        searchPlane.style.display = 'block';
    }
}

function copyCode(button) {
    var codeText = button.parentElement.querySelector('span:nth-child(2)').innerText;

    navigator.clipboard.writeText(codeText).then(() => {
        button.innerText = "Copied! ✅";
        button.classList.add("copied");

        setTimeout(() => {
            button.innerText = "Copy";
            button.classList.remove("copied");
        }, 1500);
    });
}

function initBookingMenu() {
    const bookingItems = document.querySelectorAll(".booking-item");
    const tabContents = document.querySelectorAll(".tab-content");
    bookingItems.forEach(item => {
        item.addEventListener('click', function () {
            bookingItems.forEach(b => {
                b.classList.remove('active');
                const img = b.querySelector('img');
                if (img.id === 'hotel') {
                    img.src = "assets/images/logo-hotel.png";
                } else {
                    img.src = "assets/images/maybay.png";
                }
            });
            this.classList.add('active');
            const activeImg = this.querySelector('img');
            if (activeImg.id === 'hotel') {
                activeImg.src = "assets/images/lgoo-hotelclick.png";
            } else {
                activeImg.src = "assets/images/maybayclick.png";
            }
            tabContents.forEach(tab => tab.style.display = 'none');
            const targetId = this.getAttribute('data-tab');
            const targetTab = document.getElementById(targetId);
            if (targetTab) {
                targetTab.style.display = 'flex';
            }
            switchSearchForm(targetId === 'hotel-bk' ? 'hotel' : 'flight');
        });
    });
}

function initBookingTabItems() {
    const bookItems = document.querySelectorAll(".hotelcs, .flightcs");
    bookItems.forEach(item => {
        item.addEventListener('click', function () {
            const parent = this.closest('.tab-content');
            if (!parent)
                return;
            const siblingItems = parent.querySelectorAll('.hotelcs, .flightcs');
            siblingItems.forEach(i => i.classList.remove('active-content'));
            this.classList.add('active-content');

            const accTypeInput = document.getElementById("accommodationType");
            if (this.classList.contains("hotelcs")) {
                if (this.textContent.trim() === "Khách sạn") {
                    accTypeInput.value = "Khách sạn";
                } else if (this.textContent.trim() === "Biệt thự") {
                    accTypeInput.value = "Biệt thự";
                }
            }
        });
    });
}

function toggleDropdown() {
    document.getElementById("menu").classList.toggle("show");
}

window.onclick = function (e) {
    if (!e.target.closest('.login-wel')) {
        document.getElementById("menu").classList.remove("show");
    }
}

// Hàm để tính toán ngày kế tiếp
function getNextDay(dateString) {
    const date = new Date(dateString);
    date.setDate(date.getDate() + 1);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Hàm để cập nhật checkout date
function updateCheckoutDate(checkinElement, checkoutElement) {
    const checkinDate = checkinElement.value;
    if (checkinDate) {
        const nextDay = getNextDay(checkinDate);
        checkoutElement.min = nextDay;

        // Nếu checkout hiện tại nhỏ hơn hoặc bằng checkin, đặt checkout = checkin + 1 ngày
        if (!checkoutElement.value || checkoutElement.value <= checkinDate) {
            checkoutElement.value = nextDay;
        }
    } else {
        // Nếu chưa chọn checkin, xóa giá trị checkout
        checkoutElement.value = '';
        checkoutElement.removeAttribute('min');
    }
}

window.addEventListener("load", function () {
    const today = new Date();
    const year = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const dd = String(today.getDate()).padStart(2, '0');
    const todayStr = `${year}-${mm}-${dd}`;
    const maxDate = `${year + 1}-12-31`;

    const checkin = document.getElementById("checkin");
    const checkout = document.getElementById("checkout");

    // Thiết lập min/max cho checkin
    checkin.min = todayStr;
    checkin.max = maxDate;

    // Thiết lập max cho checkout (không set min và value ban đầu)
    checkout.max = maxDate;

    // Event listener cho hotel checkin
    checkin.addEventListener("change", function () {
        updateCheckoutDate(checkin, checkout);
    });

    window.onscroll(); // Khởi động scroll effect
    initBookingMenu(); // Khởi động menu
    initBookingTabItems(); // Khởi động tab nhỏ
    switchSearchForm('flight'); // mở tab flight ban đầu
});