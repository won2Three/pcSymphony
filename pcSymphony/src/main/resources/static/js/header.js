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

    // 그리드에 버튼을 동적으로 추가
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


    // 마우스를 올렸을 때 그리드를 표시
    partsButton.addEventListener("mouseover", function () {
        partsGridContainer.classList.remove("hidden");
        partsGridContainer.classList.add("grid");
        displayGridButtons();
    });

    // 마우스를 내렸을 때 그리드를 숨김
    partsButton.addEventListener("mouseout", function () {
        partsGridContainer.classList.add("hidden");
        partsGridContainer.classList.remove("grid");
    });

    // 그리드 영역에서 마우스를 내리면 숨김
    partsGridContainer.addEventListener("mouseleave", function () {
        partsGridContainer.classList.add("hidden");
        partsGridContainer.classList.remove("grid");
    });
});