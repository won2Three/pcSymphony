
    // pcReviewId 값 가져오기
    const pcReviewId = document.getElementById('pcReviewId').value;

// userName 값 가져오기
const userId = document.getElementById('userId').value;

// 현재 시간 포맷 함수
function getMySQLFormattedTimestamp() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    // MySQL 형식 (로컬 시간)
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}


    console.log("pcReviewId 값:", pcReviewId);
    console.log("userId 값:", userId);



    function loadComments() {
    $.ajax({
        url: '/pcreview/commentList?pcreviewId=' + pcReviewId,
        type: 'GET',
        success: function (data) {
            const commentTableBody = $('#commentTable tbody');
            commentTableBody.empty();

            data.forEach(function (comment) {
                let deleteButton = '';
                if (comment.userId === userId) {
                    deleteButton = `<button class="delete-small-button" onclick="deleteComment(${comment.pcreviewCommentId})">Delete</button>`;
                }
                const commentHtml = `
                    <tr id="comment-${comment.pcreviewCommentId}">
                        <td>${comment.userId}</td>
                        <td>${comment.pcreviewCommentContent}</td>
                        <td>${deleteButton}</td>
                    </tr>`;
                commentTableBody.append(commentHtml);
            });
        },
        error: function () {
            alert('Failed to load comment list.');
        }
    });
}

// 댓글 삭제 함수
function deleteComment(commentId) {
    $.ajax({
        url: '/pcreview/commentDelete',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            pcreviewId: pcReviewId,
            pcreviewCommentId: commentId,
            userId: userId
        }),
        success: function () {
            loadComments();
        },
        error: function () {
            alert('Failed to delete comment!');
        }
    });
}

// 댓글 작성 함수
$('#commentWriteButton').click(function () {
    const commentContent = $('#pcReviewCommentContent').val();
    if (!commentContent) {
        alert('Please enter a comment.');
        return;
    }

    $.ajax({
        url: '/pcreview/commentWrite',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            pcreviewId: pcReviewId,
            userId: userId,
            pcreviewCommentContent: commentContent
        }),
        success: function () {
            $('#pcReviewCommentContent').val('');
            loadComments();
        },
        error: function () {
            alert('Failed to save comment!');
        }
    });
});

// 리뷰 수정, 저장, 취소 관리
document.addEventListener("DOMContentLoaded", function () {
    const pcReviewRow = document.getElementById("pcReviewRow");
    const editPcReviewRow = document.getElementById("editPcReviewRow");
    const editButton = document.getElementById("editPcReviewButton");
    const saveButton = document.getElementById("savePcReviewButton");
    const cancelButton = document.getElementById("cancelPcReviewButton");

    editPcReviewRow.style.display = "none";

    editButton.addEventListener("click", function () {
        pcReviewRow.style.display = "none";
        editPcReviewRow.style.display = "block";
        document.getElementById("editPcReviewTitle").value = document.getElementById("pcReviewTitle").textContent;
        document.getElementById("editPcReviewContent").value = document.getElementById("pcReviewContent").textContent;
    });

    saveButton.addEventListener("click", async function () {
        const updatedTitle = document.getElementById("editPcReviewTitle").value;
        const updatedContent = document.getElementById("editPcReviewContent").value;

    const currentTime = getMySQLFormattedTimestamp();
    if (
        document.getElementById("pcReviewTitle").textContent.trim() !== updatedTitle ||
        document.getElementById("pcReviewContent").textContent.trim() !== updatedContent
    ) {
        document.getElementById("pcReviewDate").textContent = currentTime;
        document.getElementById("pcReviewDate2").textContent = currentTime;
    }

    document.getElementById("pcReviewTitle").textContent = updatedTitle;
    document.getElementById("pcReviewContent").textContent = updatedContent;


        pcReviewRow.style.display = "block";
        editPcReviewRow.style.display = "none";

        try {
            const response = await fetch('/pcreview/updatePcReview', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    pcreviewId: pcReviewRow.dataset.id,
                    pcreviewTitle: updatedTitle,
                    pcreviewContent: updatedContent
                }),
            });

            if (!response.ok) {
                console.error('Failed to update the database');
                alert('Failed to update the database.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An issue occurred while communicating with the server.');
        }
    });

    cancelButton.addEventListener("click", function () {
        pcReviewRow.style.display = "block";
        editPcReviewRow.style.display = "none";
    });
});

    document.addEventListener("DOMContentLoaded", () => {
    // CPU event handling
    const editCpuButton = document.getElementById('editCpuButton');
    const cancelCpuButton = document.getElementById('cancelCpuButton');
    const saveCpuButton = document.getElementById('saveCpuButton');
    const cpuRow = document.getElementById('cpuRow');
    const editCpuRow = document.getElementById('editCpuRow');
    const cpuTitle = document.getElementById('cpuTitle');
    const cpuContent = document.getElementById('cpuContent');
    const cpuRating = document.getElementById('cpuRating');
    const editCpuTitle = document.getElementById('editCpuTitle');
    const editCpuContent = document.getElementById('editCpuContent');
    const editCpuRating = document.getElementById('editCpuRating');

    // editCpuButton 클릭 시
    editCpuButton.addEventListener('click', () => {
    cpuRow.style.display = 'none';
    editCpuRow.style.display = 'block';
    editCpuTitle.value = cpuTitle.textContent;
    editCpuContent.value = cpuContent.textContent;
    editCpuRating.value = cpuRating.textContent.trim(); // 현재 평점을 선택 박스에 반영
});

    // cancelCpuButton 클릭 시
    cancelCpuButton.addEventListener('click', () => {
    editCpuRow.style.display = 'none';
    cpuRow.style.display = 'block';
});

    // saveCpuButton 클릭 시
    saveCpuButton.addEventListener('click', async () => {
    const updatedTitle = editCpuTitle.value;
    const updatedContent = editCpuContent.value;
    const updatedRating = editCpuRating.value; // 선택된 평점 값 가져오기

    // Update the UI
    const currentTime = getMySQLFormattedTimestamp();
    if (
                document.getElementById("cpuTitle").textContent.trim() !== updatedTitle ||
                document.getElementById("cpuContent").textContent.trim() !== updatedContent ||
                document.getElementById("cpuRating").textContent.trim() !== updatedRating
            ) {
                document.getElementById("cpuDate").textContent = currentTime;
                document.getElementById("cpuDate2").textContent = currentTime;
            }

    cpuTitle.textContent = updatedTitle;
    cpuContent.textContent = updatedContent;
    cpuRating.textContent = updatedRating; // 평점 업데이트
    editCpuRow.style.display = 'none';
    cpuRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: cpuRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating, // 서버로 평점 값 전달
}),
});

    if (!response.ok) {
    console.error('Failed to update the database');
    alert('데이터베이스 업데이트에 실패했습니다.');
}
} catch (error) {
    console.error('Error:', error);
    alert('서버와의 통신 중 문제가 발생했습니다.');
}
});
});


    // cpucooler event handling
    const editCpucoolerButton = document.getElementById('editCpucoolerButton');
    const cancelCpucoolerButton = document.getElementById('cancelCpucoolerButton');
    const saveCpucoolerButton = document.getElementById('saveCpucoolerButton');
    const cpucoolerRow = document.getElementById('cpucoolerRow');
    const editCpucoolerRow = document.getElementById('editCpucoolerRow');
    const cpucoolerTitle = document.getElementById('cpucoolerTitle');
    const cpucoolerContent = document.getElementById('cpucoolerContent');
    const cpucoolerRating = document.getElementById('cpucoolerRating');
    const editCpucoolerTitle = document.getElementById('editCpucoolerTitle');
    const editCpucoolerContent = document.getElementById('editCpucoolerContent');
    const editCpucoolerRating = document.getElementById('editCpucoolerRating');

    editCpucoolerButton.addEventListener('click', () => {
    cpucoolerRow.style.display = 'none';
    editCpucoolerRow.style.display = 'block';
    editCpucoolerTitle.value = cpucoolerTitle.textContent;
    editCpucoolerContent.value = cpucoolerContent.textContent;
    editCpucoolerRating.value = cpucoolerRating.textContent.trim();
});

    cancelCpucoolerButton.addEventListener('click', () => {
    editCpucoolerRow.style.display = 'none';
    cpucoolerRow.style.display = 'block';
});

    saveCpucoolerButton.addEventListener('click', async () => {
    const updatedTitle = editCpucoolerTitle.value;
    const updatedContent = editCpucoolerContent.value;
    const updatedRating = editCpucoolerRating.value;

    // Update the UI
    const currentTime = getMySQLFormattedTimestamp();
    if (
            document.getElementById("cpucoolerTitle").textContent.trim() !== updatedTitle ||
            document.getElementById("cpucoolerContent").textContent.trim() !== updatedContent ||
            document.getElementById("cpucoolerRating").textContent.trim() !== updatedRating
        ) {
            document.getElementById("cpucoolerDate").textContent = currentTime;
            document.getElementById("cpucoolerDate2").textContent = currentTime;
        }


    cpucoolerTitle.textContent = updatedTitle;
    cpucoolerContent.textContent = updatedContent;
    cpucoolerRating.textContent = updatedRating;
    editCpucoolerRow.style.display = 'none';
    cpucoolerRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: cpucoolerRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating
}),
});
// 리뷰 수정, 별점 관리 함수
function setupStarRating(partName) {
    const starsEditable = document.getElementById(`starsEditable${partName}`);
    const ratingInput = document.getElementById(`editableRating${partName}`);
    const partRow = document.getElementById(`${partName.toLowerCase()}Row`);
    const editPartRow = document.getElementById(`edit${partName}Row`);
    const editButton = document.getElementById(`edit${partName}Button`);
    const saveButton = document.getElementById(`save${partName}Button`);
    const cancelButton = document.getElementById(`cancel${partName}Button`);
    const partTitle = document.getElementById(`${partName.toLowerCase()}Title`);
    const partContent = document.getElementById(`${partName.toLowerCase()}Content`);
    const partRating = document.getElementById(`${partName.toLowerCase()}Rating`);
    const readOnlyStars = document.getElementById(`starsReadOnly${partName}`);
    const editPartTitle = document.getElementById(`edit${partName}Title`);
    const editPartContent = document.getElementById(`edit${partName}Content`);

    // 별점 초기화
    const stars = starsEditable.querySelectorAll(".star");
    stars.forEach((star, index) => {
        star.addEventListener("mouseover", () => {
            stars.forEach((s, i) => s.classList.toggle("filled", i <= index));
        });

        star.addEventListener("mouseout", () => {
            const currentRating = parseInt(ratingInput.value, 10);
            stars.forEach((s, i) => s.classList.toggle("filled", i < currentRating));
        });

        star.addEventListener("click", () => {
            const selectedValue = index + 1;
            ratingInput.value = selectedValue;
            stars.forEach((s, i) => s.classList.toggle("filled", i < selectedValue));
        });
    });

    // Edit 버튼 이벤트
    editButton.addEventListener("click", () => {
        partRow.style.display = "none";
        editPartRow.style.display = "block";

        // 제목, 내용, 별점 초기화
        editPartTitle.value = partTitle.textContent.trim();
        editPartContent.value = partContent.textContent.trim();
        const currentRating = parseInt(partRating.textContent.trim(), 10);
        ratingInput.value = currentRating;
        stars.forEach((s, i) => s.classList.toggle("filled", i < currentRating));
    });
// 읽기 전용 별점 업데이트 함수
    function updateReadOnlyStars(readOnlyStarsContainer, rating) {
        if (!readOnlyStarsContainer) return; // 컨테이너가 없으면 중단

        const stars = readOnlyStarsContainer.querySelectorAll(".star");
        stars.forEach((star, index) => {
            star.classList.toggle("filled", index < rating); // `filled` 클래스로 별점 상태 업데이트
        });
    }

// Save 버튼 이벤트에서 읽기 전용 별점 DOM 업데이트 호출
    saveButton.addEventListener("click", async () => {
        const updatedTitle = editPartTitle.value;
        const updatedContent = editPartContent.value;
        const updatedRating = parseInt(ratingInput.value, 10);

        // UI 업데이트
        partTitle.textContent = updatedTitle;
        partContent.textContent = updatedContent;
        partRating.textContent = updatedRating.toString(); // 별점 텍스트 업데이트
        document.getElementById(`${partName.toLowerCase()}Date`).textContent = getMySQLFormattedTimestamp();

        // 읽기 전용 별점 DOM 업데이트
        updateReadOnlyStars(readOnlyStars, updatedRating);

        partRow.style.display = "block";
        editPartRow.style.display = "none";

        try {
            const response = await fetch('/pcreview/updateParts', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    partsReviewId: partRow.dataset.id,
                    partsReviewTitle: updatedTitle,
                    partsReviewContent: updatedContent,
                    partsReviewRating: updatedRating,
                }),
            });

            if (!response.ok) {
                throw new Error("Failed to update review");
            }
        } catch (error) {
            console.error("Error updating review:", error);
            alert("Failed to save changes.");
        }
    });


    // Cancel 버튼 이벤트
    cancelButton.addEventListener("click", () => {
        editPartRow.style.display = "none";
        partRow.style.display = "block";
    });
}

// 읽기 전용 별점 업데이트 함수
function updateReadOnlyStars(readOnlyStarsContainer, rating) {
    const stars = readOnlyStarsContainer.querySelectorAll(".star");
    stars.forEach((star, index) => {
        star.classList.toggle("filled", index < rating);
    });
}

// 페이지 로드 시 모든 파츠에 대해 별점 초기화
document.addEventListener("DOMContentLoaded", () => {
    const parts = ["Cpu", "Cpucooler", "Motherboard", "Memory", "Storage", "Videocard", "Powersupply", "Cover"];
    parts.forEach(partName => {
        setupStarRating(partName); // 별점 수정 기능 초기화

        // 읽기 전용 별점 초기화
        const readOnlyStarsContainer = document.getElementById(`starsReadOnly${partName}`);
        const rating = parseInt(document.getElementById(`${partName.toLowerCase()}Rating`).textContent.trim(), 10);
        updateReadOnlyStars(readOnlyStarsContainer, rating);
    });
});



// 페이지 로드 시 댓글 목록 불러오기
$(document).ready(function () {
    loadComments();
});