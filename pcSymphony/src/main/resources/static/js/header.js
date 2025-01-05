    document.addEventListener("DOMContentLoaded", function () {
    const partsButton = document.getElementById("partsbutton");
    const partsGridContainer = document.getElementById("parts-grid-container");

    // 버튼 데이터를 정의합니다.
    const partsItems = [
{name: "CPU", href: "/part/cpu"},
{name: "CPU Cooler", href: "/part/cpucooler"},
{name: "VideoCard", href: "/part/videocard"},
{name: "Memory", href: "/part/memory"},
{name: "Storage", href: "/part/storage"},
{name: "Motherboard", href: "/part/motherboard"},
{name: "PowerSupply", href: "/part/powersupply"},
{name: "Case", href: "/part/cover"}
    ];

    // Parts 버튼 클릭 이벤트
    partsButton.addEventListener("click", function (event) {
    event.preventDefault(); // 기본 동작 막기

    // 그리드가 보이면 숨기고, 숨겨져 있으면 표시
    if (partsGridContainer.classList.contains("hidden")) {
    partsGridContainer.classList.remove("hidden");
    partsGridContainer.classList.add("grid");
    displayGridButtons();
} else {
    partsGridContainer.classList.add("hidden");
    partsGridContainer.classList.remove("grid");
}
});

    // 버튼을 동적으로 생성하는 함수
    function displayGridButtons() {
    partsGridContainer.innerHTML = ""; // 기존 버튼 초기화

    partsItems.forEach((item) => {
    const button = document.createElement("a");
    button.href = item.href;
    button.textContent = item.name;
    button.className = "grid-button";
    partsGridContainer.appendChild(button);
});
}
});