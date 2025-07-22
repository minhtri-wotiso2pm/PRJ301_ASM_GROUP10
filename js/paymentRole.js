function confirmPayment() {
    if (confirm("Bạn đã thanh toán thành công chưa?")) {
        // ✅ Chuyển hướng sang trang chọn khách sạn
        window.location.href = "searchDashboard.jsp"; // <-- sửa lại tên file nếu khác
    }
}