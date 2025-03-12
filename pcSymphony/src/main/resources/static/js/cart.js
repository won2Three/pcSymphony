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
                "Motherboard": "motherboard",
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
            console.log('서버 응답:', data);
                if (data.success) {
                    const reviewButton = document.querySelector("#create\\ review");
                    const priceElement = row.querySelector(".price");
                    const itemPrice = parseFloat(priceElement.innerText.replace('$', '').replace(',', '')) || 0;

                    reviewButton.disabled = true; // 버튼 비활성화
                    reviewButton.style.color = "#B0BEC5"; // 텍스트 색상 변경
                    // 부품 상태를 "Choose a Part"로 변경
                    const nameElement = row.querySelector(".name");
                    const link = nameElement.querySelector("a");
                    nameElement.innerHTML = `<a href="/part/${cartItem}" class="no-product-button">Choose a ${category}</a>`;
                    row.querySelector("th:nth-child(4)").innerText = '$0';
                    button.setAttribute("disabled", true);

                    const totalPriceElement = document.querySelector(".totalPrice");
                    if (totalPriceElement) {
                                console.log('totalPriceElement 값:', totalPriceElement.innerText);  // 현재 값 확인
                            } else {
                                console.log('totalPriceElement가 선택되지 않았습니다.');
                            }
                    const currentTotalPrice = parseFloat(totalPriceElement.innerText.replace(',', '').replace('Total Price : $', '')) || 0;
                    const newTotalPrice = currentTotalPrice - itemPrice;
                    console.log("after : " + newTotalPrice)
                    console.log(newTotalPrice.toLocaleString())
                    totalPriceElement.innerText ='Total Price : $' + newTotalPrice.toLocaleString();
                } else {
                    alert('삭제 실패');
                }
            })
            .catch(error => {
                console.error("삭제 실패:", error);
            });
        });
    });

    // ✅ Delete All 버튼 이벤트 추가
    document.getElementById("deleteAllButton").addEventListener("click", async function () {
        if (!confirm("Are you sure you want to delete all items from the cart?")) {
            return;
        }

        try {
            const response = await fetch('/cart/removeAll', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            const data = await response.json();

            if (data.success) {
                console.log("All items removed successfully");

                // ✅ 테이블에서 모든 부품 제거
                document.querySelectorAll(".name").forEach(nameElement => {
                    nameElement.innerHTML = `<a href="/part/${nameElement.dataset.part}" class="no-product-button">Choose a ${nameElement.dataset.part}</a>`;
                });

                // ✅ 가격 초기화
                document.querySelectorAll(".price").forEach(priceElement => {
                    priceElement.innerText = "$0";
                });

                // ✅ 개별 삭제 버튼 비활성화
                document.querySelectorAll(".deleteButton").forEach(button => {
                    button.setAttribute("disabled", true);
                });

                // ✅ Total Price 초기화
                const totalPriceElement = document.querySelector(".totalPrice");
                if (totalPriceElement) {
                    totalPriceElement.innerText = "Total Price : $0";
                }

                // ✅ 셀렉트 박스 다시 보이기
                document.querySelectorAll(".recommendations-select").forEach(selectBox => {
                    selectBox.style.display = "block";
                });

            } else {
                alert("Failed to remove all items.");
            }
        } catch (error) {
            console.error("Failed to remove all items:", error);
        }
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

 // "Save As Image" 버튼 클릭 이벤트 추가
    const saveAsImageButton = document.getElementById('save as image');
    if (saveAsImageButton) {
        saveAsImageButton.addEventListener("click", function () {
            saveCartAsImage();
        });
    }

 // 카트 내용을 이미지로 저장하는 함수
 function saveCartAsImage() {
     // 카트 내용을 담고 있는 영역 (예: 테이블 또는 전체 카트 섹션)
     const cartContent = document.querySelector("table"); // 카트 전체 영역

     // html2canvas로 카트 영역을 캡처
     html2canvas(cartContent).then(function (canvas) {
         // 캡처한 이미지 데이터를 Data URL로 변환
         const imageUrl = canvas.toDataURL('image/png'); // 'image/png' 형식으로 캡처

         // Data URL을 사용하여 이미지 다운로드
         const link = document.createElement('a');
         link.href = imageUrl;
         link.download = 'cart-items.png'; // 다운로드할 이미지 파일 이름 설정
         link.click(); // 이미지 다운로드 실행
     }).catch(function (error) {
               console.error("Error capturing the content as an image:", error);
           });
 }
});
//------------------------------------------------------------------------------------

document.addEventListener("DOMContentLoaded", async function () {
    let cart = {}; // ✅ 전역 변수로 카트 데이터 관리

    /** ✅ 장바구니 데이터 불러오기 */
    async function loadCartData() {
        try {
            const response = await fetch('/cart/get-cart');
            if (!response.ok) {
                throw new Error('Failed to fetch cart data');
            }
            cart = await response.json();
            console.log("Cart data loaded:", cart);
        } catch (error) {
            console.error("Error loading cart data:", error);
        }
    }

    /** ✅ 추천 리스트 불러오기 */
    async function loadRecommendations(category) {
        console.log(`Loading recommendations for ${category}`);

        try {
            const response = await fetch(`/cart/recommend/${category}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch recommendations for ${category}`);
            }

            const data = await response.json();
            console.log(`Received recommendations for ${category}:`, data);

            let selectBox = document.getElementById(`${category}-recommendations`);
            let partLink = document.querySelector(`.name[data-part="${category}"] a:not(.no-product-button)`);

            if (!selectBox) {
                console.error(`Select box not found for ${category}`);
                return;
            }

            let basedOn = [];
            if (category === "cpu") {
                if (cart.motherboard) basedOn.push("Motherboard");
                if (cart.memory) basedOn.push("Memory");
            } else if (category === "memory") {
                if (cart.motherboard) basedOn.push("Motherboard");
                if (cart.cpu) basedOn.push("CPU");
            } else if (category === "motherboard") {
                if (cart.cpu) basedOn.push("CPU");
                if (cart.memory) basedOn.push("Memory");
            }

            let basedOnText = basedOn.length ? ` (Based on: ${basedOn.join(", ")})` : "";
            selectBox.innerHTML = `<option value="">Select a recommended ${category}${basedOnText}</option>`;

            if (partLink) {
                selectBox.style.display = "none";
                return;
            } else {
                selectBox.style.display = "block";
            }

            if (!Array.isArray(data)) {
                console.error(`Invalid response for ${category}:`, data);
                selectBox.innerHTML += '<option value="" disabled>Error loading recommendations</option>';
                return;
            }

            data.slice(0, 10).forEach(part => {
                let option = document.createElement("option");
                option.value = part.id;
                option.textContent = `${part.name}`;
                selectBox.appendChild(option);
            });

        } catch (error) {
            console.error("Error fetching recommendations:", error);
        }
    }

    /** ✅ 부품을 선택하면 즉시 화면에 반영 */
        document.querySelectorAll(".recommendations-select").forEach(selectBox => {
            selectBox.addEventListener("change", async function () {
                let category = this.id.replace("-recommendations", "");
                let partId = this.value;

                if (!partId) return;

                try {
                    const response = await fetch('/cart/add', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ tableName: category, id: partId })
                    });

                    const data = await response.json();

                    if (data.success) {
                        console.log(`Item ${partId} added to cart successfully.`);

                        let selectedOption = selectBox.options[selectBox.selectedIndex];
                        let partName = selectedOption.textContent.trim();

                        // ✅ 가격을 서버에서 다시 요청하여 가져오기
                        const partResponse = await fetch(`/cart/get-part-price?category=${category}&id=${partId}`);
                        const partData = await partResponse.json();
                        let partPrice = partData.price ? parseFloat(partData.price) : 0;

                        let nameElement = document.querySelector(`.name[data-part="${category}"]`);
                        let priceElement = nameElement.closest("tr").querySelector(".price");
                        let chooseButton = nameElement.querySelector(".no-product-button");

                        // ✅ 부품 이름 업데이트
                        nameElement.innerHTML = `<a href="/part/${category}/${partId}" class="${category}-selected">
                                                    <span data-id="${partId}">${partName}</span>
                                                </a>`;

                        // ✅ 가격 업데이트
                        priceElement.innerText = `$${partPrice.toFixed(2)}`;

                        // ✅ "Choose a Part"와 셀렉트 박스 숨기기
                        if (chooseButton) chooseButton.style.display = "none";
                        selectBox.style.display = "none";

                        // ✅ 삭제 버튼 활성화
                        let deleteButton = nameElement.closest("tr").querySelector(".deleteButton");
                        deleteButton.removeAttribute("disabled");

                        // ✅ 총 가격 업데이트
                        updateTotalPrice();

                    } else {
                        alert('Failed to add item to cart.');
                    }
                } catch (error) {
                    console.error('Error:', error);
                }
            });
        });




    /** ✅ 삭제 시 즉시 화면에 반영 */
    document.querySelectorAll(".deleteButton").forEach(button => {
        button.addEventListener("click", async function () {
            const row = button.closest("tr");
            const category = row.querySelector(".category").innerText.toLowerCase();

            try {
                const response = await fetch('/cart/removeItem', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ cartItem: category })
                });

                const data = await response.json();

                if (data.success) {
                    console.log(`Item from ${category} removed successfully`);

                    let nameElement = document.querySelector(`.name[data-part="${category}"]`);
                    let selectBox = document.getElementById(`${category}-recommendations`);

                    // ✅ "Choose a CPU" 다시 표시
                    nameElement.innerHTML = `<a href="/part/${category}" class="no-product-button">Choose a ${category}</a>`;

                    // ✅ 삭제 버튼 비활성화
                    button.setAttribute("disabled", true);

                    // ✅ 셀렉트 박스 즉시 표시
                    if (selectBox) {
                        selectBox.style.display = "block";
                    }

                } else {
                    alert("삭제 실패");
                }
            } catch (error) {
                console.error("삭제 실패:", error);
            }
        });
    });

//total price 업데이트
function updateTotalPrice() {
    let totalPrice = 0;

    document.querySelectorAll(".price").forEach(priceElement => {
        let priceMatch = priceElement.innerText.match(/\$(\d+(\.\d+)?)/);
        let partPrice = priceMatch ? parseFloat(priceMatch[1]) : 0;
        totalPrice += partPrice;
    });

    const totalPriceElement = document.querySelector(".totalPrice");
    if (totalPriceElement) {
        totalPriceElement.innerText = `Total Price : $${totalPrice.toLocaleString()}`;
    }
}

    /** ✅ 페이지 로드 시 모든 추천 리스트 및 장바구니 데이터 로드 */
        async function loadAllRecommendations() {
            await loadCartData();
            let categories = ["cpu", "motherboard", "memory", "videocard", "storage", "powersupply", "cpucooler", "cover"];
            categories.forEach(category => loadRecommendations(category));
        }

        loadAllRecommendations();
});



/*
1. 딜리트하면 셀렉트 바 즉시 안뜸, 새로고침 해야 반영됨
2. 셀렉트 바에서 선택했을때 바로 화면에 반영 안됨, 새로고침해야 됨
3. 파워, 글카, 메모리 추천리스트 제대로 안뜸
*/