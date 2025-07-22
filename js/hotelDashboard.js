window.addEventListener("load", function () {
    const today = new Date();
    const year = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const dd = String(today.getDate()).padStart(2, '0');

    const todayStr = `${year}-${mm}-${dd}`;
    const maxDate = `${year + 1}-12-31`;

    const checkin = document.getElementById("checkin");
    const checkout = document.getElementById("checkout");
    const checkin_plane = document.getElementById("checkin-plane");
    const checkout_plane = document.getElementById("checkout-plane");

    checkin.min = todayStr;
    checkin.max = maxDate;
    checkin_plane.min = todayStr;
    checkin_plane.max = maxDate;

    checkout.min = checkin.value || todayStr;
    checkout.max = maxDate;
    checkout_plane.min = checkin_plane.value || todayStr;
    checkout_plane.max = maxDate;

    checkin.addEventListener("change", function () {
        checkout.min = this.value;
        if (checkout.value < this.value) {
            checkout.value = this.value;
        }
    });

    checkin_plane.addEventListener("change", function () {
        checkout_plane.min = this.value;
        if (checkout_plane.value < this.value) {
            checkout_plane.value = this.value;
        }
    });
});