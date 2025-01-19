document.addEventListener('DOMContentLoaded', () => {
    const parts = [
        'cpu', 'cpucooler', 'motherboard', 'memory',
        'storage', 'videocard', 'powersupply', 'cover'
    ];

    parts.forEach(part => {
        const starContainer = document.getElementById(`${part}RatingStars`);
        const stars = starContainer.querySelectorAll('.star');
        const ratingInput = document.getElementById(`${part}RatingInput`);

        stars.forEach((star, index) => {
            // 마우스를 올렸을 때
            star.addEventListener('mouseover', () => {
                stars.forEach((s, i) => {
                    s.classList.toggle('hovered', i <= index); // Hover된 별까지 강조
                });
            });

            // 마우스를 뗐을 때
            star.addEventListener('mouseout', () => {
                stars.forEach((s) => {
                    s.classList.remove('hovered'); // Hover 스타일 제거
                });
            });

            // 별 클릭 시
            star.addEventListener('click', () => {
                const selectedRating = index + 1; // 클릭된 별점 값
                ratingInput.value = selectedRating; // 값 저장

                stars.forEach((s, i) => {
                    s.classList.toggle('selected', i < selectedRating); // 선택된 별 강조
                });
            });
        });
    });
});