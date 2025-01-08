document.addEventListener("DOMContentLoaded", function () {
    // 삭제 버튼 처리
    const deleteButtons = document.querySelectorAll(".deleteButton");
    deleteButtons.forEach(function (button, index) {
        button.addEventListener("click", function () {
            const row = button.closest("tr");
            const category = row.querySelector(".category").innerText;
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

            const cartItem = categoryMapping[category];
            if (!cartItem) return;

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
                    const priceElement = row.querySelector(".price");
                    const itemPrice = parseFloat(priceElement.innerText.replace(',', '')) || 0;

                    row.querySelector(".name").innerText = '제품 없음';
                    row.querySelector("th:nth-child(4)").innerText = '0';
                    button.setAttribute("disabled", true);

                    const totalPriceElement = document.querySelector(".totalPrice");
                    const currentTotalPrice = parseFloat(totalPriceElement.innerText.replace(',', '').replace('달러', '')) || 0;
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

    // 호환성 검사 버튼 클릭 이벤트 추가
    const compatibilityButton = document.getElementById('Compatibility Check');
    if (compatibilityButton) {
        compatibilityButton.addEventListener("click", function () {
            checkCompatibility();  // checkCompatibility 함수 호출
        });
    }

    // 호환성 검사 함수
    function checkCompatibility() {
        const cpuElement = document.querySelector('tr th[data-part="cpu"] span');
        const motherboardElement = document.querySelector('tr th[data-part="motherboard"] span');
        const memoryElement = document.querySelector('tr th[data-part="memory"] span');

        const cpuSelected = cpuElement && cpuElement.innerText !== '제품 없음';
        const motherboardSelected = motherboardElement && motherboardElement.innerText !== '제품 없음';
        const memorySelected = memoryElement && memoryElement.innerText !== '제품 없음';

        // 각 부품 간의 호환성 검사를 개별적으로 진행
        if (cpuSelected && motherboardSelected) {
            fetch('/cart/check-cpu-motherboard-compatibility')
                .then(response => response.json())
                .then(data => {
                    if (!data.isCompatible) {
                        cpuElement.closest('th').style.border = '2px solid red';
                        motherboardElement.closest('th').style.border = '2px solid red';
                    } else {
                        resetBorders(['cpu', 'motherboard']);
                    }
                });
        }

        if (motherboardSelected && memorySelected) {
            fetch('/cart/check-motherboard-memory-compatibility')
                .then(response => response.json())
                .then(data => {
                    if (!data.isCompatible) {
                        motherboardElement.closest('th').style.border = '2px solid red';
                        memoryElement.closest('th').style.border = '2px solid red';
                    } else {
                        resetBorders(['motherboard', 'memory']);
                    }
                });
        }

        // CPU와 Memory 호환성 검사 추가
        if (cpuSelected && memorySelected) {
            fetch('/cart/check-cpu-memory-compatibility', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ cpuName: cpuElement.innerText, memoryType: memoryElement.innerText })
            })
            .then(response => response.json())
            .then(data => {
                const isCompatible = data.isCompatible;
                const cpuCell = cpuElement.closest('th');
                const memoryCell = memoryElement.closest('th');

                if (isCompatible) {
                    cpuCell.style.border = 'none';
                    memoryCell.style.border = 'none';
                } else {
                    cpuCell.style.border = '2px solid red';
                    memoryCell.style.border = '2px solid red';
                }
            });
        }
    }

    // 호환성 검사 후 호환되지 않는 부품의 테두리 리셋
    function resetBorders(parts) {
        parts.forEach(part => {
            const partElement = document.querySelector(`tr th[data-part="${part}"]`);
            if (partElement) {
                partElement.style.border = 'none';
            }
        });
    }
});
