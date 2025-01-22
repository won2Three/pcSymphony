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
                alert('Added to the cart!');
            } else {
                alert('Failed to add to the cart.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to save the review.');
        });
}

//-----------------------------------------------
// 리뷰 작성 script
document.addEventListener('DOMContentLoaded', function () {
    // URL에서 부품 정보 추출
    const urlParts = window.location.pathname.split('/');
    const partType = urlParts[1]; // 예: 'part'
    const partCategory = urlParts[2]; // 예: 'cpu', 'motherboard', ...
    const partDetailId = urlParts[3]; // 예: 부품 ID (예: 1)

    // 각 부품에 해당하는 'hidden' input 필드를 자동으로 설정
    const partMapping = {
        cpu: 'cpuId',
        cpucooler: 'cpucoolerId',
        motherboard: 'motherboardId',
        memory: 'memoryId',
        storage: 'storageId',
        videocard: 'videocardId',
        powersupply: 'powersupplyId',
        cover: 'coverId',
    };

    if (partMapping[partCategory]) {
        const inputElement = document.getElementById(partMapping[partCategory]);
        if (inputElement) inputElement.value = partDetailId;
    }

    // 별점 기능 구현
    const stars = document.querySelectorAll('#ratingStars span');
    const ratingInput = document.getElementById('partsReviewRating');

    stars.forEach((star, index) => {
        // 마우스를 올렸을 때
        star.addEventListener('mouseover', () => {
            // Hover된 별과 그 이전 별들까지 색상 변경
            stars.forEach((s, i) => {
                s.style.color = i <= index ? 'gold' : 'lightgray'; // Hover 상태
            });
        });

        // 마우스를 뗐을 때
        star.addEventListener('mouseout', () => {
            // 현재 저장된 별점 값(ratingInput.value)에 따라 색상 복원
            stars.forEach((s, i) => {
                // i < ratingInput.value가 true인 별들만 선택 상태 유지
                s.style.color = i < ratingInput.value ? 'gold' : 'lightgray'; // 저장된 값 기준
            });
        });

        // 별 클릭 시
        star.addEventListener('click', () => {
            // 별점 값을 저장
            ratingInput.value = index + 1; // 별점은 1부터 시작
            // 클릭한 상태로 색상 고정
            stars.forEach((s, i) => {
                s.style.color = i <= index ? 'gold' : 'lightgray'; // 클릭한 별까지 고정
            });
        });
    });

    // 리뷰 작성 폼의 submit 이벤트 처리
    document.getElementById('reviewForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

        // 폼 데이터를 객체로 변환
        const formData = {
            partsReviewTitle: document.getElementById('partsReviewTitle').value,
            partsReviewRating: document.getElementById('partsReviewRating').value,
            partsReviewContent: document.getElementById('partsReviewContent').value,
            cpuId: document.getElementById('cpuId')?.value || null,
            cpucoolerId: document.getElementById('cpucoolerId')?.value || null,
            motherboardId: document.getElementById('motherboardId')?.value || null,
            memoryId: document.getElementById('memoryId')?.value || null,
            storageId: document.getElementById('storageId')?.value || null,
            videocardId: document.getElementById('videocardId')?.value || null,
            powersupplyId: document.getElementById('powersupplyId')?.value || null,
            coverId: document.getElementById('coverId')?.value || null,
        };

        console.log(formData); // 디버깅용: 폼 데이터가 제대로 들어오는지 확인

        // POST 요청 보내기 (서버 API로)
        fetch('/part/partsReviewWrite', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to save the review.');
                }
                return response.json(); // 응답을 JSON으로 처리
            })
            .then(data => {
                // alert('The review was saved successfully.');
                document.getElementById('reviewForm').reset(); // 폼 초기화

                // 리뷰 목록을 새로 갱신
                const pathParts = window.location.pathname.split('/');
                const partType = pathParts[2]; // 예: 'cpu', 'memory' 등
                const partId = parseInt(pathParts[3]); // 1, 2, 3 등

                // 리뷰 목록을 다시 가져와서 갱신
                fetchReviews(partType, partId);
            })
            .catch(error => {
                console.error('Error:', error); // 오류를 로그로 출력
                alert('Failed to save the review.');
            });
    });
});


//--------------------------------------------------------
// 리뷰 목록, 수정
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
                authorCell.classList.add('table-cell'); // CSS 클래스 추가
                row.appendChild(authorCell);

                // 리뷰 제목 (수정 가능하도록 input 추가)
                const titleCell = document.createElement('td');
                titleCell.innerHTML = `<span class="review-title">${review.partsReviewTitle}</span>
                                           <input class="edit-title" type="text" value="${review.partsReviewTitle}" style="display:none;" />`;
                titleCell.classList.add('table-cell');
                row.appendChild(titleCell);

                // 리뷰 내용 (수정 가능하도록 만들기)
                const contentCell = document.createElement('td');
                contentCell.innerHTML = `<span class="review-content">${review.partsReviewContent}</span>
                                             <textarea class="edit-content" style="display:none;">${review.partsReviewContent}</textarea>`;
                contentCell.classList.add('table-cell');
                row.appendChild(contentCell);

                // 별점 (수정 가능하도록 만들기)
                const ratingCell = document.createElement('td');
                ratingCell.innerHTML = `
    <div class="stars review-rating edit-rating" data-review-id="${review.partsReviewId}">
        ${[...Array(5)].map((_, i) => `
            <span class="star ${i < review.partsReviewRating ? 'filled' : 'empty'}" data-value="${i + 1}">&#9733;</span>
        `).join('')}
    </div>
    <input type="hidden" class="edit-rating" value="${review.partsReviewRating}">
`;
                ratingCell.classList.add('table-cell');
                row.appendChild(ratingCell);

                // 작성 날짜
                const dateCell = document.createElement('td');

                // 날짜 포맷팅 (yyyy.mm.dd hh:mm)
                const date = new Date(review.partsReviewDate);
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0');
                const day = String(date.getDate()).padStart(2, '0');
                const hours = String(date.getHours()).padStart(2, '0');
                const minutes = String(date.getMinutes()).padStart(2, '0');
                dateCell.textContent = `${year}.${month}.${day} ${hours}:${minutes}`; // 원하는 형식으로 설정
                dateCell.classList.add('table-cell');
                row.appendChild(dateCell);

                // 수정 버튼 (작성자와 로그인한 사용자 비교 후 표시 여부 결정)
                // check...
                const editCell = document.createElement('td');
                const editButton = document.createElement('button');
                editButton.textContent = 'Edit';
                // 클래스 추가
                editCell.classList.add('table-cell');
                editButton.classList.add('main-small-button');

                checkPermissionAndShowButton(review.partsReviewId, 'edit', editButton);  // 권한 확인 후 버튼 보이기
                editCell.appendChild(editButton);
                row.appendChild(editCell);

                // 삭제 버튼 (작성자와 로그인한 사용자 비교 후 표시 여부 결정)
                const deleteCell = document.createElement('td');
                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                // 클래스 추가
                deleteCell.classList.add('table-cell');
                deleteButton.classList.add('delete-small-button');
                checkPermissionAndShowButton(review.partsReviewId, 'delete', deleteButton);  // 권한 확인 후 버튼 보이기
                // check...
                deleteCell.appendChild(deleteButton);
                row.appendChild(deleteCell);

                // 테이블에 행 추가
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('An error occurred while fetching the review list.:', error);
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
            console.error('An error occurred while checking permissions.:', error);
            alert('A problem occurred while verifying permissions.');
        });
}
// check...
// 수정 버튼 클릭 시 리뷰 수정
function editReview(partsReviewId) {
    const row = document.querySelector(`tr[data-review-id='${partsReviewId}']`);
    const titleSpan = row.querySelector('.review-title');
    const contentSpan = row.querySelector('.review-content');
    const titleInput = row.querySelector('.edit-title');
    const contentTextarea = row.querySelector('.edit-content');
    const starsContainer = row.querySelector('.stars');
    const ratingInput = row.querySelector('.edit-rating');
    const editButton = row.querySelector('.main-small-button')
    if (!titleSpan || !contentSpan || !titleInput || !contentTextarea || !starsContainer || !ratingInput) {
        console.error("One or more elements are missing in the row for review ID:", partsReviewId);
        return;
    }

    // 제목, 내용, 별점 수정 가능하게 전환
    editButton.style.display = 'none';
    titleSpan.style.display = 'none';
    contentSpan.style.display = 'none';
    titleInput.style.display = 'inline-block';
    contentTextarea.style.display = 'inline-block';

    // 별점 수정 기능 추가
    const stars = starsContainer.querySelectorAll('.star');

    stars.forEach(star => {
        star.addEventListener('click', () => {
            const selectedValue = parseInt(star.getAttribute('data-value'), 10);
            ratingInput.value = selectedValue;

            // 선택한 별까지 색상 채우기
            stars.forEach((s, i) => {
                s.classList.toggle('filled', i < selectedValue);
                s.classList.toggle('empty', i >= selectedValue);
            });
        });

        star.addEventListener('mouseover', () => {
            const hoverValue = parseInt(star.getAttribute('data-value'), 10);
            stars.forEach((s, i) => {
                s.classList.toggle('filled', i < hoverValue);
                s.classList.toggle('empty', i >= hoverValue);
            });
        });

        star.addEventListener('mouseout', () => {
            const currentRating = parseInt(ratingInput.value, 10);
            stars.forEach((s, i) => {
                s.classList.toggle('filled', i < currentRating);
                s.classList.toggle('empty', i >= currentRating);
            });
        });
    });

    // 저장 버튼 추가
    const editCell = row.querySelector('td:last-child');
    let saveButton = editCell.querySelector('.save-button');
    if (!saveButton) {
        saveButton = document.createElement('button');
        saveButton.textContent = 'Save';
        saveButton.classList.add('save-button', 'lightblue-small-button');
        saveButton.onclick = function () {
            saveReview(partsReviewId, titleInput.value, contentTextarea.value, ratingInput.value);
        };
        editCell.appendChild(saveButton);
    }

    saveButton.style.display = 'inline-block';
}


// 리뷰 수정 후 저장
function saveReview(partsReviewId, updatedTitle, updatedContent, updatedRating) {
    const pathParts = window.location.pathname.split('/');
    const partType = pathParts[2];  // 'cpu', 'memory' 등
    const partId = parseInt(pathParts[3]);  // 1, 2, 3 등
    console.log("updatedRating" + updatedRating);
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
            alert('The review has been updated.');
            fetchReviews(partType, partId);  // 리뷰 목록 새로 고침
        })
        .catch(error => {
            alert('Failed to update the review.');
            console.error('Error:', error);  // 에러 로그 확인
        });
}

// 리뷰 삭제 함수
function deleteReview(partsReviewId) {
    const confirmDelete = confirm('Are you sure you want to delete this review?');
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
                    alert('The review has been deleted.');
                    window.location.reload(); // 페이지를 새로 고쳐서 리뷰 목록 업데이트
                } else {
                    alert('Failed to delete the review.');
                }
            })
            .catch(error => {
                console.error('An error occurred while deleting the review.:', error);
                alert('Failed to delete the review.');
            });
    }
}