// pcReviewId 값 가져오기
const pcReviewId = document.getElementById('pcReviewId').textContent.trim();

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
    return `${year}.${month}.${day} ${hours}:${minutes}`;
}

// 댓글 목록 로드 함수
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

        document.getElementById("pcReviewTitle").textContent = updatedTitle;
        document.getElementById("pcReviewContent").textContent = updatedContent;
        document.getElementById("pcReviewDate").textContent = getMySQLFormattedTimestamp();

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