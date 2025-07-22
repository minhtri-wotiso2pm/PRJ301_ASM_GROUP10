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

    // Thiết lập max cho checkout
    checkout.max = maxDate;

    // Event listener cho hotel checkin
    checkin.addEventListener("change", function () {
        updateCheckoutDate(checkin, checkout);
    });
});
