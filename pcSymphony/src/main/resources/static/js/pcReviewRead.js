
    // pcReviewId 값 가져오기
    const pcReviewId = document.getElementById('pcReviewId').textContent.trim();

    // userName 값 가져오기
    const userId = document.getElementById('userId').value;

    function getMySQLFormattedTimestamp() {
    const now = new Date();

    // 로컬 시간 계산
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    // MySQL 형식 (로컬 시간)
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
}


    console.log("pcReviewId 값:", pcReviewId);
    console.log("userId 값:", userId);

    function loadComments() {
    $.ajax({
        url: '/pcreview/commentList?pcreviewId=' + pcReviewId, // GET 방식으로 파라미터 전달
        type: 'GET',
        success: function (data) {
            const commentTableBody = $('#commentTable tbody');
            commentTableBody.empty(); // 기존 댓글 목록을 비웁니다.

            // 서버에서 받은 댓글 데이터를 HTML로 출력
            data.forEach(function (comment) {
                let deleteButton = '';
                if (comment.userId === userId) {
                    // 현재 로그인한 사용자의 댓글일 때만 삭제 버튼 표시
                    deleteButton = `<button class="gray-button" onclick="deleteComment(${comment.pcreviewCommentId})">Delete</button>`;
                }

                const commentHtml = `
                        <tr id="comment-${comment.pcreviewCommentId}">
                            <td>${comment.userId}</td>
                            <td>${comment.pcreviewCommentContent}</td>
                            <td>${deleteButton}</td>
                        </tr>
                    `;
                commentTableBody.append(commentHtml);
            });
        },
        error: function (xhr, status, error) {
            alert('댓글 목록 불러오기 실패');
        }
    });
}

    // 댓글을 삭제하는 함수
    function deleteComment(commentId) {
    const deleteData = {
    pcreviewId: pcReviewId, // 리뷰 ID
    pcreviewCommentId: commentId, // 삭제할 댓글 ID
    userId: userId // 사용자 ID
};

    $.ajax({
    url: '/pcreview/commentDelete', // 적절한 URL로 변경
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(deleteData),
    success: function () {
    loadComments(); // 댓글 목록 새로고침
},
    error: function () {
    alert('댓글 삭제 실패!');
}
});
}

    // 댓글을 저장하는 함수
    $('#commentWriteButton').click(function () {
    const commentContent = $('#pcReviewCommentContent').val();
    if (!commentContent) {
    alert('댓글을 입력하세요.');
    return;
}

    const commentData = {
    pcreviewId: pcReviewId,
    userId: userId,
    pcreviewCommentContent: commentContent
};

    $.ajax({
    url: '/pcreview/commentWrite',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(commentData),
    success: function () {
    $('#pcReviewCommentContent').val(''); // 댓글 입력란 초기화
    loadComments(); // 댓글 목록 새로고침
},
    error: function () {
    alert('댓글 저장 실패!');
}
});
});

    // 페이지 로드 시 댓글 목록 불러오기
    $(document).ready(function () {
    loadComments();
});

    // PcReviewHandling
    document.addEventListener("DOMContentLoaded", function () {
    const pcReviewRow = document.getElementById("pcReviewRow");
    const editPcReviewRow = document.getElementById("editPcReviewRow");

    const editButton = document.getElementById("editPcReviewButton");
    const saveButton = document.getElementById("savePcReviewButton");
    const cancelButton = document.getElementById("cancelPcReviewButton");

    editPcReviewRow.style.display = "none";

    editButton.addEventListener("click", function () {
    console.log("도달")
    pcReviewRow.style.display = "none";
    editPcReviewRow.style.display = "block";

    document.getElementById("editPcReviewTitle").value = document.getElementById("pcReviewTitle").textContent;
    document.getElementById("editPcReviewContent").value = document.getElementById("pcReviewContent").textContent;
});

    // Handle save button click
    saveButton.addEventListener("click", async function () {

    const updatedTitle = document.getElementById("editPcReviewTitle").value;
    const updatedContent = document.getElementById("editPcReviewContent").value;

    document.getElementById("pcReviewTitle").textContent = updatedTitle;
    document.getElementById("pcReviewContent").textContent = updatedContent;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("pcReviewDate").textContent = currentTime;


    pcReviewRow.style.display = "block";
    editPcReviewRow.style.display = "none";
    try {
    const response = await fetch('/pcreview/updatePcReview', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    pcreviewId: pcReviewRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    pcreviewTitle: updatedTitle,
    pcreviewContent: updatedContent,
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

    // Handle cancel button click
    cancelButton.addEventListener("click", function () {
    // Switch back to display mode without saving changes
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
    cpuTitle.textContent = updatedTitle;
    cpuContent.textContent = updatedContent;
    cpuRating.textContent = updatedRating; // 평점 업데이트
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("cpuDate").textContent = currentTime;
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
    cpucoolerTitle.textContent = updatedTitle;
    cpucoolerContent.textContent = updatedContent;
    cpucoolerRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("cpucoolerDate").textContent = currentTime;
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

    if (!response.ok) {
    console.error('Failed to update the database');
    alert('데이터베이스 업데이트에 실패했습니다.');
}
} catch (error) {
    console.error('Error:', error);
    alert('서버와의 통신 중 문제가 발생했습니다.');
}
});

    // motherboard event handling
    const editMotherboardButton = document.getElementById('editMotherboardButton');
    const cancelMotherboardButton = document.getElementById('cancelMotherboardButton');
    const saveMotherboardButton = document.getElementById('saveMotherboardButton');
    const motherboardRow = document.getElementById('motherboardRow');
    const editMotherboardRow = document.getElementById('editMotherboardRow');
    const motherboardTitle = document.getElementById('motherboardTitle');
    const motherboardContent = document.getElementById('motherboardContent');
    const motherboardRating = document.getElementById('motherboardRating');
    const editMotherboardTitle = document.getElementById('editMotherboardTitle');
    const editMotherboardContent = document.getElementById('editMotherboardContent');
    const editMotherboardRating = document.getElementById('editMotherboardRating');

    editMotherboardButton.addEventListener('click', () => {
    motherboardRow.style.display = 'none';
    editMotherboardRow.style.display = 'block';
    editMotherboardTitle.value = motherboardTitle.textContent;
    editMotherboardContent.value = motherboardContent.textContent;
    editMotherboardRating.value = motherboardRating.textContent.trim();
});

    cancelMotherboardButton.addEventListener('click', () => {
    editMotherboardRow.style.display = 'none';
    motherboardRow.style.display = 'block';
});

    saveMotherboardButton.addEventListener('click', async () => {
    const updatedTitle = editMotherboardTitle.value;
    const updatedContent = editMotherboardContent.value;
    const updatedRating = editMotherboardRating.value;

    // Update the UI
    motherboardTitle.textContent = updatedTitle;
    motherboardContent.textContent = updatedContent;
    motherboardRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("motherboardDate").textContent = currentTime;
    editMotherboardRow.style.display = 'none';
    motherboardRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: motherboardRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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


    // Memory event handling
    const editMemoryButton = document.getElementById('editMemoryButton');
    const cancelMemoryButton = document.getElementById('cancelMemoryButton');
    const saveMemoryButton = document.getElementById('saveMemoryButton');
    const memoryRow = document.getElementById('memoryRow');
    const editMemoryRow = document.getElementById('editMemoryRow');
    const memoryTitle = document.getElementById('memoryTitle');
    const memoryContent = document.getElementById('memoryContent');
    const memoryRating = document.getElementById('memoryRating');
    const editMemoryTitle = document.getElementById('editMemoryTitle');
    const editMemoryContent = document.getElementById('editMemoryContent');
    const editMemoryRating = document.getElementById('editMemoryRating');

    editMemoryButton.addEventListener('click', () => {
    memoryRow.style.display = 'none';
    editMemoryRow.style.display = 'block';
    editMemoryTitle.value = memoryTitle.textContent;
    editMemoryContent.value = memoryContent.textContent;
    editMemoryRating.value = memoryRating.textContent.trim();
});

    cancelMemoryButton.addEventListener('click', () => {
    editMemoryRow.style.display = 'none';
    memoryRow.style.display = 'block';
});

    saveMemoryButton.addEventListener('click', async () => {
    const updatedTitle = editMemoryTitle.value;
    const updatedContent = editMemoryContent.value;
    const updatedRating = editMemoryRating.value;

    // Update the UI
    memoryTitle.textContent = updatedTitle;
    memoryContent.textContent = updatedContent;
    memoryRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("memoryDate").textContent = currentTime;
    editMemoryRow.style.display = 'none';
    memoryRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: memoryRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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

    // Storage event handling
    const editStorageButton = document.getElementById('editStorageButton');
    const cancelStorageButton = document.getElementById('cancelStorageButton');
    const saveStorageButton = document.getElementById('saveStorageButton');
    const storageRow = document.getElementById('storageRow');
    const editStorageRow = document.getElementById('editStorageRow');
    const storageTitle = document.getElementById('storageTitle');
    const storageContent = document.getElementById('storageContent');
    const storageRating = document.getElementById('storageRating');
    const editStorageTitle = document.getElementById('editStorageTitle');
    const editStorageContent = document.getElementById('editStorageContent');
    const editStorageRating = document.getElementById('editStorageRating');

    editStorageButton.addEventListener('click', () => {
    storageRow.style.display = 'none';
    editStorageRow.style.display = 'block';
    editStorageTitle.value = storageTitle.textContent;
    editStorageContent.value = storageContent.textContent;
    editStorageRating.value = storageRating.textContent.trim();
});

    cancelStorageButton.addEventListener('click', () => {
    editStorageRow.style.display = 'none';
    storageRow.style.display = 'block';
});

    saveStorageButton.addEventListener('click', async () => {
    const updatedTitle = editStorageTitle.value;
    const updatedContent = editStorageContent.value;
    const updatedRating = editStorageRating.value;

    // Update the UI
    storageTitle.textContent = updatedTitle;
    storageContent.textContent = updatedContent;
    storageRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("storageDate").textContent = currentTime;
    editStorageRow.style.display = 'none';
    storageRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: storageRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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


    // Video Card event handling
    const editVideocardButton = document.getElementById('editVideocardButton');
    const cancelVideocardButton = document.getElementById('cancelVideocardButton');
    const saveVideocardButton = document.getElementById('saveVideocardButton');
    const videocardRow = document.getElementById('videocardRow');
    const editVideocardRow = document.getElementById('editVideocardRow');
    const videocardTitle = document.getElementById('videocardTitle');
    const videocardContent = document.getElementById('videocardContent');
    const videocardRating = document.getElementById('videocardRating');
    const editVideocardTitle = document.getElementById('editVideocardTitle');
    const editVideocardContent = document.getElementById('editVideocardContent');
    const editVideocardRating = document.getElementById('editVideocardRating');

    editVideocardButton.addEventListener('click', () => {
    videocardRow.style.display = 'none';
    editVideocardRow.style.display = 'block';
    editVideocardTitle.value = videocardTitle.textContent;
    editVideocardContent.value = videocardContent.textContent;
    editVideocardRating.value = videocardRating.textContent.trim();
});

    cancelVideocardButton.addEventListener('click', () => {
    editVideocardRow.style.display = 'none';
    videocardRow.style.display = 'block';
});

    saveVideocardButton.addEventListener('click', async () => {
    const updatedTitle = editVideocardTitle.value;
    const updatedContent = editVideocardContent.value;
    const updatedRating = editVideocardRating.value;

    // Update the UI
    videocardTitle.textContent = updatedTitle;
    videocardContent.textContent = updatedContent;
    videocardRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("videocardDate").textContent = currentTime;
    editVideocardRow.style.display = 'none';
    videocardRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: videocardRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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


    // Power Supply event handling
    const editPowersupplyButton = document.getElementById('editPowersupplyButton');
    const cancelPowersupplyButton = document.getElementById('cancelPowersupplyButton');
    const savePowersupplyButton = document.getElementById('savePowersupplyButton');
    const powersupplyRow = document.getElementById('powersupplyRow');
    const editPowersupplyRow = document.getElementById('editPowersupplyRow');
    const powersupplyTitle = document.getElementById('powersupplyTitle');
    const powersupplyContent = document.getElementById('powersupplyContent');
    const powersupplyRating = document.getElementById('powersupplyRating');
    const editPowersupplyTitle = document.getElementById('editPowersupplyTitle');
    const editPowersupplyContent = document.getElementById('editPowersupplyContent');
    const editPowersupplyRating = document.getElementById('editPowersupplyRating');

    editPowersupplyButton.addEventListener('click', () => {
    powersupplyRow.style.display = 'none';
    editPowersupplyRow.style.display = 'block';
    editPowersupplyTitle.value = powersupplyTitle.textContent;
    editPowersupplyContent.value = powersupplyContent.textContent;
    editPowersupplyRating.value = powersupplyRating.textContent.trim();
});

    cancelPowersupplyButton.addEventListener('click', () => {
    editPowersupplyRow.style.display = 'none';
    powersupplyRow.style.display = 'block';
});

    savePowersupplyButton.addEventListener('click', async () => {
    const updatedTitle = editPowersupplyTitle.value;
    const updatedContent = editPowersupplyContent.value;
    const updatedRating = editPowersupplyRating.value;

    // Update the UI
    powersupplyTitle.textContent = updatedTitle;
    powersupplyContent.textContent = updatedContent;
    powersupplyRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("powersupplyDate").textContent = currentTime;
    editPowersupplyRow.style.display = 'none';
    powersupplyRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: powersupplyRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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


    // Cover event handling
    const editCoverButton = document.getElementById('editCoverButton');
    const cancelCoverButton = document.getElementById('cancelCoverButton');
    const saveCoverButton = document.getElementById('saveCoverButton');
    const coverRow = document.getElementById('coverRow');
    const editCoverRow = document.getElementById('editCoverRow');
    const coverTitle = document.getElementById('coverTitle');
    const coverContent = document.getElementById('coverContent');
    const coverRating = document.getElementById('coverRating');
    const editCoverTitle = document.getElementById('editCoverTitle');
    const editCoverContent = document.getElementById('editCoverContent');
    const editCoverRating = document.getElementById('editCoverRating');

    editCoverButton.addEventListener('click', () => {
    coverRow.style.display = 'none';
    editCoverRow.style.display = 'block';
    editCoverTitle.value = coverTitle.textContent;
    editCoverContent.value = coverContent.textContent;
    editCoverRating.value = coverRating.textContent.trim();
});

    cancelCoverButton.addEventListener('click', () => {
    editCoverRow.style.display = 'none';
    coverRow.style.display = 'block';
});

    saveCoverButton.addEventListener('click', async () => {
    const updatedTitle = editCoverTitle.value;
    const updatedContent = editCoverContent.value;
    const updatedRating = editCoverRating.value;

    // Update the UI
    coverTitle.textContent = updatedTitle;
    coverContent.textContent = updatedContent;
    coverRating.textContent = updatedRating;
    const currentTime = getMySQLFormattedTimestamp();
    document.getElementById("coverDate").textContent = currentTime;
    editCoverRow.style.display = 'none';
    coverRow.style.display = 'block';

    // Send the updated data to the server
    try {
    const response = await fetch('/pcreview/updateParts', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({
    partsReviewId: coverRow.dataset.id, // HTML에 `data-id` 속성으로 ID를 포함
    partsReviewTitle: updatedTitle,
    partsReviewContent: updatedContent,
    partsReviewRating: updatedRating // 서버로 평점 값 전달
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