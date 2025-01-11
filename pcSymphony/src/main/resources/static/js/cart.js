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
                body: JSON.stringify({ cartItem })
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
                    const cpuMotherboardStatus = document.querySelector('#cpu-motherboard-compatibility');

                    if (!data.isCompatible) {
                        cpuMotherboardStatus.classList.remove("compatible");
                        cpuMotherboardStatus.classList.add("incompatible");
                    } else {
                        cpuMotherboardStatus.classList.remove("incompatible");
                        cpuMotherboardStatus.classList.add("compatible");
                    }
                });
        }

        if (motherboardSelected && memorySelected) {
            fetch('/cart/check-motherboard-memory-compatibility')
                .then(response => response.json())
                .then(data => {
                    const motherboardMemoryStatus = document.querySelector('#motherboard-memory-compatibility');

                    if (!data.isCompatible) {
                        motherboardMemoryStatus.classList.remove("compatible");
                        motherboardMemoryStatus.classList.add("incompatible");
                    } else {
                        motherboardMemoryStatus.classList.remove("incompatible");
                        motherboardMemoryStatus.classList.add("compatible");
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
                const cpuMemoryStatus = document.querySelector('#cpu-memory-compatibility');

                if (data.isCompatible) {
                    cpuMemoryStatus.classList.remove("incompatible");
                    cpuMemoryStatus.classList.add("compatible");
                } else {
                    cpuMemoryStatus.classList.remove("compatible");
                    cpuMemoryStatus.classList.add("incompatible");
                }
            });
        }

        // Motherboard-Cover 호환성 검사 함수
        function checkMotherboardCoverCompatibility() {
            const motherboardElement = document.querySelector('tr th[data-part="motherboard"] span');
            const coverElement = document.querySelector('tr th[data-part="cover"] span');

            const motherboardSelected = motherboardElement && motherboardElement.innerText !== '제품 없음';
            const coverSelected = coverElement && coverElement.innerText !== '제품 없음';

            // 두 부품이 모두 선택되었을 때만 검사를 진행
            if (motherboardSelected && coverSelected) {
                fetch('/cart/check-motherboard-cover-compatibility', {
                    method: 'POST',  // 변경: GET -> POST
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        motherboard: motherboardElement.innerText,
                        cover: coverElement.innerText
                    })
                })
                .then(response => response.json())
                .then(data => {
                    const motherboardCoverStatus = document.querySelector('#motherboard-cover-compatibility');

                    if (!data.isCompatible) {
                        motherboardCoverStatus.classList.remove("compatible");
                        motherboardCoverStatus.classList.add("incompatible");
                    } else {
                        motherboardCoverStatus.classList.remove("incompatible");
                        motherboardCoverStatus.classList.add("compatible");
                    }
                });
            }
        }

        // Motherboard와 Cover 호환성 검사
        checkMotherboardCoverCompatibility();

        // Videocard-Cover 호환성 검사 함수
        function checkVideocardCoverCompatibility() {
            const videoCardElement = document.querySelector('tr th[data-part="videocard"] span');
            const coverElement = document.querySelector('tr th[data-part="cover"] span');

            const videoCardSelected = videoCardElement && videoCardElement.innerText !== '제품 없음';
            const coverSelected = coverElement && coverElement.innerText !== '제품 없음';

            if (videoCardSelected && coverSelected) {
                fetch('/cart/check-videocard-cover-compatibility', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        videocard: videoCardElement.innerText,
                        cover: coverElement.innerText
                    })
                })
                .then(response => response.json())
                .then(data => {
                    const videocardCoverStatus = document.querySelector('#videocard-cover-compatibility');

                    if (!data.isCompatible) {
                        videocardCoverStatus.classList.remove("compatible");
                        videocardCoverStatus.classList.add("incompatible");
                    } else {
                        videocardCoverStatus.classList.remove("incompatible");
                        videocardCoverStatus.classList.add("compatible");
                    }
                });
            }
        }

        // powerSupply와 Cover 호환성 검사 호출
        checkVideocardCoverCompatibility();

        // PowerSupply와 Cover 호환성 검사 함수
        function checkPowerSupplyCoverCompatibility() {
            const powersupplyElement = document.querySelector('tr th[data-part="powersupply"] span');
            const coverElement = document.querySelector('tr th[data-part="cover"] span');

            const powersupplySelected = powersupplyElement && powersupplyElement.innerText !== '제품 없음';
            const coverSelected = coverElement && coverElement.innerText !== '제품 없음';

            // 두 부품이 모두 선택되었을 때만 검사를 진행
            if (powersupplySelected && coverSelected) {
                fetch('/cart/check-powersupply-cover-compatibility', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        powersupply: powersupplyElement.innerText,
                        cover: coverElement.innerText
                    })
                })
                .then(response => response.json())
                .then(data => {
                    const powersupplyCoverStatus = document.querySelector('#powersupply-cover-compatibility');

                    if (!data.isCompatible) {
                        powersupplyCoverStatus.classList.remove("compatible");
                        powersupplyCoverStatus.classList.add("incompatible");
                    } else {
                        powersupplyCoverStatus.classList.remove("incompatible");
                        powersupplyCoverStatus.classList.add("compatible");
                    }
                });
            }
        }

        // PowerSupply와 Cover 호환성 검사
        checkPowerSupplyCoverCompatibility();

        // PowerSupply - Case 호환성 검사 결과를 바로 초록색으로 변경
        const powersupplyCaseStatus = document.querySelector('#powersupply-case-compatibility2');
        if (powersupplyCaseStatus) {
            powersupplyCaseStatus.classList.remove("incompatible");
            powersupplyCaseStatus.classList.add("compatible");
        }

        // --- 모달 관련 코드 시작 ---
        // 모달 요소 찾기
        const modal = document.getElementById('compatibility-modal');
        const closeModalButton = document.getElementById('close-modal');
        const incompatibleResultsList = document.getElementById('incompatible-results');

        // Compatibility Detail 버튼 클릭 시 모달 열기
        const compatibilityButtonModal = document.getElementById('view-details-button'); // 중복 선언 문제를 해결

        if (compatibilityButtonModal) {
            compatibilityButtonModal.addEventListener("click", function () {
                showModal();
            });
        }

        // 모달 창 닫기
        if (closeModalButton) {
            closeModalButton.addEventListener("click", function () {
                closeModal();
            });
        }

        // 모달 창 외부 클릭 시 모달 닫기
        window.addEventListener("click", function (event) {
            if (event.target === modal) {
                closeModal();
            }
        });

        function showModal() {
            incompatibleResultsList.innerHTML = ""; // 모달을 열 때마다 기존 결과를 초기화

             // 각 부품별 비호환성 메시지를 관리하는 객체
             const compatibilityMessages = {
                 "cpu-motherboard-compatibility": {
                     title: "CPU - Motherboard Socket Incompatibility",
                     message: "The socket type of the CPU does not match the motherboard. Please choose a CPU and motherboard with compatible socket types."
                 },
                 "motherboard-memory-compatibility": {
                     title: "Motherboard - Memory Form Factor Incompatibility",
                     message: "The form factor of the memory is not compatible with the motherboard. Please choose memory with the correct form factor for the motherboard."
                 },
                 "cpu-memory-compatibility": {
                     title: "Memory - CPU Compatibility Issue",
                     message: "The memory form factor may not be compatible with the CPU manufacturer. Please ensure the memory is compatible with your CPU's requirements."
                 },
                 "motherboard-cover-compatibility": {
                     title: "Motherboard - Case Form Factor Incompatibility",
                     message: "The form factor of the motherboard does not match the case. Please choose a case that supports the motherboard's form factor."
                 },
                 "videocard-cover-compatibility": {
                     title: "Videocard - Case Size Incompatibility",
                     message: "The length of the videocard exceeds the available space in the case. Please choose a smaller videocard or a larger case."
                 },
                 "powersupply-cover-compatibility": {
                     title: "Power Supply - Case Compatibility Issue",
                     message: "The type of power supply does not match the case. Please select a power supply that fits your case's specifications."
                 }
             };

           // 각 부품별 비호환성 상태를 확인하고, 해당 항목에 맞는 문구를 추가
             const compatibilityStatuses = document.querySelectorAll('.compatibility-status');

             compatibilityStatuses.forEach(function (status) {
                 if (status.classList.contains('incompatible')) {
                     const listItem = document.createElement('li');

                     // 비호환 항목 제목과 메시지를 추가
                     const compatibilityData = compatibilityMessages[status.id];

                     if (compatibilityData) {
                         // 제목
                         const titleElement = document.createElement('strong');
                         titleElement.innerText = compatibilityData.title;
                         listItem.appendChild(titleElement);

                         // 비호환 항목에 대한 설명
                         const messageElement = document.createElement('p');
                         messageElement.classList.add('compatibility-message');
                         messageElement.innerText = compatibilityData.message;
                         listItem.appendChild(messageElement);
                     }

                     incompatibleResultsList.appendChild(listItem); // 비호환 항목과 문구를 리스트에 추가
                 }
             });


            // 모달 열기
            modal.style.display = "block";
        }

        function closeModal() {
            modal.style.display = "none"; // 모달 닫기
        }
        // --- 모달 관련 코드 끝 ---
    }
});
