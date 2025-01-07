document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll(".deleteButton");

    deleteButtons.forEach(function (button, index) {
        button.addEventListener("click", function () {
            const row = button.closest("tr");
            const category = row.querySelector(".category").innerText; // 카테고리 (예: CPU, Memory 등)
            const categoryMapping = {
                "CPU": "cpu",
                "CpuCooler": "cpucooler",
                "VideoCard": "videocard",
                "Memory": "memory",
                "Storage": "storage",
                "MotherBoard": "motherboard",
                "PowerSupply": "powersupply",
                "Case": "cover"
            };

            const cartItem = categoryMapping[category]; // 카트에서 해당 항목의 key 값
            if (!cartItem) return;

            // Ajax 요청 보내기
            fetch(`/cart/removeItem`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({cartItem})
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 성공적으로 삭제되었을 때 화면에서 제품명과 가격을 변경
                        const priceElement = row.querySelector(".price");
                        const itemPrice = parseFloat(priceElement.innerText.replace(',', '')) || 0;

                        row.querySelector(".name").innerText = '제품 없음';
                        row.querySelector("th:nth-child(4)").innerText = '0';
                        button.setAttribute("disabled", true); // 버튼 비활성화

                        const totalPriceElement = document.querySelector(".totalPrice");
                        const currentTotalPrice = parseFloat(totalPriceElement.innerText.replace(',', '').replace('원', '')) || 0;
                        const newTotalPrice = currentTotalPrice - itemPrice;

                        totalPriceElement.innerText = newTotalPrice.toLocaleString() + '달러';
                    } else {
                        alert('삭제 실패');
                    }
                })
                .catch(error => {
                    console.error("삭제 실패:", error);
                });
        });
    });
});