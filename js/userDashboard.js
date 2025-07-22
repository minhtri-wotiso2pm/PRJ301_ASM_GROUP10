window.addEventListener('DOMContentLoaded', () => {
    const daySelect = document.getElementById("daySelect");
    const monthSelect = document.getElementById("monthSelect");
    const yearSelect = document.getElementById("yearSelect");

    function isLeapYear(year) {
        return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    }

    function updateDays() {
        const month = parseInt(monthSelect.value);
        const year = parseInt(yearSelect.value);

        if (!month || !year) return;

        // Xác định số ngày trong tháng
        let daysInMonth;
        switch (month) {
            case 2:
                daysInMonth = isLeapYear(year) ? 29 : 28;
                break;
            case 4: case 6: case 9: case 11:
                daysInMonth = 30;
                break;
            default:
                daysInMonth = 31;
        }

        // Xóa các option cũ trừ option đầu tiên
        while (daySelect.options.length > 1) {
            daySelect.remove(1);
        }

        for (let d = 1; d <= daysInMonth; d++) {
            const option = document.createElement("option");
            option.value = d;
            option.textContent = d;
            daySelect.appendChild(option);
        }
    }

    // Tạo tháng
    for (let m = 1; m <= 12; m++) {
        const option = document.createElement("option");
        option.value = m;
        option.textContent = m;
        monthSelect.appendChild(option);
    }

    // Tạo năm từ hiện tại về 1900
    const currentYear = new Date().getFullYear();
    for (let y = currentYear; y >= 1900; y--) {
        const option = document.createElement("option");
        option.value = y;
        option.textContent = y;
        yearSelect.appendChild(option);
    }

    // Sự kiện khi chọn tháng hoặc năm → cập nhật ngày
    monthSelect.addEventListener('change', updateDays);
    yearSelect.addEventListener('change', updateDays);
});
