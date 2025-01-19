document.addEventListener("DOMContentLoaded", function () {
    const partsButton = document.getElementById("partsbutton");
    const partsGridContainer = document.getElementById("parts-grid-container");

    // 버튼 데이터를 정의합니다.
    const partsItems = [
        { img: "/images/nav-cpu.png", name: "CPU", href: "/part/cpu" },
        { img: "/images/nav-cpucooler.png", name: "CPU Cooler", href: "/part/cpucooler" },
        { img: "/images/nav-videocard.png", name: "VideoCard", href: "/part/videocard" },
        { img: "/images/nav-memory.png", name: "Memory", href: "/part/memory" },
        { img: "/images/nav-storage.png", name: "Storage", href: "/part/storage" },
        { img: "/images/nav-motherboard.png", name: "Motherboard", href: "/part/motherboard" },
        { img: "/images/nav-powersupply.png", name: "PowerSupply", href: "/part/powersupply" },
        { img: "/images/nav-cover.png", name: "Case", href: "/part/cover" },
    ];


// 이미지를 미리 렌더링합니다.
    partsItems.forEach((item) => {
        const button = document.createElement("a");
        button.href = item.href;
        button.className = "grid-button";

        // 이미지 요소 생성
        const img = document.createElement("img");
        img.src = item.img;
        img.alt = item.name;

        // 텍스트 요소 생성
        const text = document.createElement("span");
        text.textContent = item.name;

        // 버튼에 이미지와 텍스트 추가
        button.appendChild(img);
        button.appendChild(text);

        // 컨테이너에 버튼 추가 (초기 상태로 숨김)
        button.style.display = "none"; // 처음에는 숨김 처리
        partsGridContainer.appendChild(button);
    });


// 마우스 이벤트 처리
    partsButton.addEventListener("mouseenter", function () {
        const buttons = partsGridContainer.querySelectorAll(".grid-button");
        buttons.forEach((button) => {
            button.style.display = "flex"; // 마우스 호버 시 표시
        });
        partsGridContainer.classList.remove("hidden");
        partsGridContainer.classList.add("grid");
    });

    partsButton.addEventListener("mouseleave", function () {
        setTimeout(() => {
            if (!partsGridContainer.matches(":hover")) {
                const buttons = partsGridContainer.querySelectorAll(".grid-button");
                buttons.forEach((button) => {
                    button.style.display = "none"; // 마우스 벗어나면 숨김
                });
                partsGridContainer.classList.add("hidden");
                partsGridContainer.classList.remove("grid");
            }
        }, 200);
    });



});