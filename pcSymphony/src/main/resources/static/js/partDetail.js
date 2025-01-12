function addToCart(button) {
    const tableName = button.getAttribute('data-table-name');
    const id = button.getAttribute('data-id');

    fetch(`/cart/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tableName: tableName, id: id}),
    })
        .then(response => {
            if (response.ok) {
                alert('장바구니에 추가되었습니다!');
            } else {
                alert('장바구니 추가에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('장바구니 추가 중 오류가 발생했습니다.');
        });
}

//-----------------------------------------------
    // URL에서 부품 정보 추출
    document.addEventListener('DOMContentLoaded', function() {
    const urlParts = window.location.pathname.split('/');
    const partType = urlParts[1]; // 예: 'part'
    const partCategory = urlParts[2]; // 예: 'cpu', 'motherboard', ...
    const partDetailId = urlParts[3]; // 예: 부품 ID (예: 1)

    // 각 부품에 해당하는 'hidden' input 필드를 자동으로 설정
    if (partCategory === 'cpu') {
    document.getElementById('cpuId').value = partDetailId;
} else if (partCategory === 'cpucooler') {
    document.getElementById('cpucoolerId').value = partDetailId;
} else if (partCategory === 'motherboard') {
    document.getElementById('motherboardId').value = partDetailId;
} else if (partCategory === 'memory') {
    document.getElementById('memoryId').value = partDetailId;
} else if (partCategory === 'storage') {
    document.getElementById('storageId').value = partDetailId;
} else if (partCategory === 'videocard') {
    document.getElementById('videocardId').value = partDetailId;
} else if (partCategory === 'powersupply') {
    document.getElementById('powersupplyId').value = partDetailId;
} else if (partCategory === 'cover') {
    document.getElementById('coverId').value = partDetailId;
}
});

    // 리뷰 작성 폼의 submit 이벤트 처리
    document.getElementById('reviewForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼 제출 기본 동작 방지

    // 폼 데이터를 객체로 변환
    const formData = {
    partsReviewTitle: document.getElementById('partsReviewTitle').value,
    partsReviewRating: document.getElementById('partsReviewRating').value,
    partsReviewContent: document.getElementById('partsReviewContent').value,
    cpuId: document.getElementById('cpuId').value || null,
    cpucoolerId: document.getElementById('cpucoolerId').value || null,
    motherboardId: document.getElementById('motherboardId').value || null,
    memoryId: document.getElementById('memoryId').value || null,
    storageId: document.getElementById('storageId').value || null,
    videocardId: document.getElementById('videocardId').value || null,
    powersupplyId: document.getElementById('powersupplyId').value || null,
    coverId: document.getElementById('coverId').value || null
};

    console.log(formData);  // 디버깅용: 폼 데이터가 제대로 들어오는지 확인

    // POST 요청 보내기 (서버 API로)
    fetch('/part/partsReviewWrite', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(formData)
})
    .then(response => {
    if (!response.ok) {
    throw new Error('리뷰 저장에 실패했습니다.');
}
    return response.json(); // 응답을 JSON으로 처리
})
    .then(data => {
    alert('리뷰가 성공적으로 저장되었습니다.');
    document.getElementById('reviewForm').reset(); // 폼 초기화

    // 리뷰 목록을 새로 갱신
    const pathParts = window.location.pathname.split('/');
    const partType = pathParts[2];  // 예: 'cpu', 'memory' 등
    const partId = parseInt(pathParts[3]);  // 1, 2, 3 등

    // 리뷰 목록을 다시 가져와서 갱신
    fetchReviews(partType, partId);
})
    .catch(error => {
    console.error('Error:', error);  // 오류를 로그로 출력
    alert('리뷰 저장에 실패했습니다.');
});
});

//--------------------------------------------------------
// 페이지 로드 시, 리뷰 데이터를 불러오는 함수
window.onload = function () {
    // 현재 URL 경로에서 partType과 partId 추출
    const pathParts = window.location.pathname.split('/');  // 경로를 '/'로 분리
    const partType = pathParts[2];  // 'cpu', 'memory' 등
    const partId = parseInt(pathParts[3]);  // 1, 2, 3 등

    // review를 가져오는 함수 호출
    fetchReviews(partType, partId);
};

// 리뷰 목록을 가져오는 함수
function fetchReviews(partType, partId) {
    // 서버에서 리뷰 데이터를 가져오는 GET 요청
    fetch(`/part/${partType}/${partId}/reviews`)  // 수정된 API 엔드포인트로 요청
        .then(response => response.json())  // JSON 형태로 응답 받기
        .then(reviews => {
            const tbody = document.getElementById('review-list-body');
            tbody.innerHTML = '';  // 테이블 초기화
            console.log(reviews);
            reviews.forEach(review => {
                // 리뷰 데이터를 기반으로 테이블 행을 동적으로 생성
                const row = document.createElement('tr');
                row.setAttribute('data-review-id', review.partsReviewId);  // data-review-id 속성 추가

                // 작성자
                const authorCell = document.createElement('td');
                authorCell.textContent = review.memberId;  // 작성자 ID 표시, 나중에 이름으로 수정 가능
                row.appendChild(authorCell);

                // 리뷰 제목 (수정 가능하도록 input 추가)
                const titleCell = document.createElement('td');
                titleCell.innerHTML = `<span class="review-title">${review.partsReviewTitle}</span>
                                           <input class="edit-title" type="text" value="${review.partsReviewTitle}" style="display:none;" />`;
                row.appendChild(titleCell);

                // 리뷰 내용 (수정 가능하도록 만들기)
                const contentCell = document.createElement('td');
                contentCell.innerHTML = `<span class="review-content">${review.partsReviewContent}</span>
                                             <textarea class="edit-content" style="display:none;">${review.partsReviewContent}</textarea>`;
                row.appendChild(contentCell);

                // 별점 (수정 가능하도록 만들기)
                const ratingCell = document.createElement('td');
                ratingCell.innerHTML = `
                        <span class="review-rating">${review.partsReviewRating}</span>
                        <select class="edit-rating" style="display:none;">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    `;
                row.appendChild(ratingCell);

                // 작성 날짜
                const dateCell = document.createElement('td');
                dateCell.textContent = new Date(review.partsReviewDate).toLocaleString();  // 날짜 포맷팅
                row.appendChild(dateCell);

                // 수정 버튼 (작성자와 로그인한 사용자 비교 후 표시 여부 결정)
                const editCell = document.createElement('td');
                const editButton = document.createElement('button');
                editButton.textContent = '수정';
                checkPermissionAndShowButton(review.partsReviewId, 'edit', editButton);  // 권한 확인 후 버튼 보이기
                editCell.appendChild(editButton);
                row.appendChild(editCell);

                // 삭제 버튼 (작성자와 로그인한 사용자 비교 후 표시 여부 결정)
                const deleteCell = document.createElement('td');
                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                checkPermissionAndShowButton(review.partsReviewId, 'delete', deleteButton);  // 권한 확인 후 버튼 보이기
                deleteCell.appendChild(deleteButton);
                row.appendChild(deleteCell);

                // 테이블에 행 추가
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('리뷰 목록을 가져오는 중 오류 발생:', error);
        });
}

// 권한 확인 후 버튼을 보이거나 숨기기
function checkPermissionAndShowButton(partsReviewId, action, button) {
    fetch(`/part/partsReview/permission/${partsReviewId}?action=${action}`, {
        method: 'GET',
    })
        .then(response => response.json())
        .then(data => {
            if (data.isAllowed) {
                // 권한이 있으면 버튼 클릭 이벤트 핸들러 추가
                if (action === 'edit') {
                    button.onclick = function () {
                        editReview(partsReviewId);  // 권한이 있으면 수정
                    };
                } else if (action === 'delete') {
                    button.onclick = function () {
                        deleteReview(partsReviewId);  // 권한이 있으면 삭제
                    };
                }
            } else {
                // 권한이 없으면 버튼을 숨김
                button.style.display = 'none';
            }
        })
        .catch(error => {
            console.error('권한 확인 중 오류 발생:', error);
            alert('권한 확인 중 문제가 발생했습니다.');
        });
}

// 수정 버튼 클릭 시 리뷰 수정
function editReview(partsReviewId) {
    const row = document.querySelector(`tr[data-review-id='${partsReviewId}']`);
    const titleSpan = row.querySelector('.review-title');
    const contentSpan = row.querySelector('.review-content');
    const titleInput = row.querySelector('.edit-title');
    const contentTextarea = row.querySelector('.edit-content');
    const ratingSelect = row.querySelector('.edit-rating');
    const ratingSpan = row.querySelector('.review-rating');  // 별점 span

    // 현재 리뷰의 별점 값을 가져와서 select 태그의 기본값으로 설정
    const currentRating = ratingSpan.textContent.trim();
    ratingSelect.value = currentRating;

    // 제목, 내용, 별점을 수정 가능하도록 전환
    titleSpan.style.display = 'none';
    contentSpan.style.display = 'none';
    ratingSpan.style.display = 'none';
    titleInput.style.display = 'inline-block';
    contentTextarea.style.display = 'inline-block';
    ratingSelect.style.display = 'inline-block';

    // 수정 후 저장 버튼 추가
    const editCell = row.querySelector('td:last-child'); // 마지막 수정 버튼 셀 찾기
    let saveButton = editCell.querySelector('.save-button');
    if (!saveButton) {
        saveButton = document.createElement('button');
        saveButton.textContent = '저장';
        saveButton.classList.add('save-button');
        saveButton.onclick = function () {
            saveReview(partsReviewId, titleInput.value, contentTextarea.value, ratingSelect.value);
        };
        editCell.appendChild(saveButton); // 수정 버튼 셀에 저장 버튼 추가
    }

    // 저장 버튼만 보이게 함
    saveButton.style.display = 'inline-block'; // 보이게 설정
}

// 리뷰 수정 후 저장
function saveReview(partsReviewId, updatedTitle, updatedContent, updatedRating) {
    const pathParts = window.location.pathname.split('/');
    const partType = pathParts[2];  // 'cpu', 'memory' 등
    const partId = parseInt(pathParts[3]);  // 1, 2, 3 등

    fetch(`/part/partsReviewUpdate/${partsReviewId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            partsReviewTitle: updatedTitle,
            partsReviewContent: updatedContent,
            partsReviewRating: updatedRating,  // 수정된 별점도 함께 전송
        }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            alert('리뷰가 수정되었습니다.');
            fetchReviews(partType, partId);  // 리뷰 목록 새로 고침
        })
        .catch(error => {
            alert('리뷰 수정에 실패했습니다.');
            console.error('Error:', error);  // 에러 로그 확인
        });
}

// 리뷰 삭제 함수
function deleteReview(partsReviewId) {
    const confirmDelete = confirm('이 리뷰를 삭제하시겠습니까?');
    if (confirmDelete) {
        // 서버로 DELETE 요청 보내기
        fetch(`/part/partsReviewDelete`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({partsReviewId: partsReviewId})
        })
            .then(response => {
                if (response.ok) {
                    alert('리뷰가 삭제되었습니다.');
                    window.location.reload(); // 페이지를 새로 고쳐서 리뷰 목록 업데이트
                } else {
                    alert('리뷰 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('리뷰 삭제 중 오류 발생:', error);
                alert('리뷰 삭제에 실패했습니다.');
            });
    }
}