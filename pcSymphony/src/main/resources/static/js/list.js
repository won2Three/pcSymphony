$(document).ready(function() {
    // 페이지 번호 초기화
    let currentPage = 0;
    let totalPages = 0;

    // 글쓰기 버튼
    $('#writeButton').click(function(){
        location.href = 'write'; // 글쓰기 페이지로 이동
    });

    // 날짜 형식을 yyyy-MM-dd HH:mm 형태로 변환하는 함수
    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    // 목록 조회 함수
    function getList(page) {
        $.ajax({
            url: 'getList',
            method: 'POST',
            data: { page: page },
            success: function(response) {
                const list = response.content;
                totalPages = response.totalPages;

                if (list.length === 0) {
                    $('#tbody').html('<tr><td colspan="6" style="text-align: center;">목록이 없습니다.</td></tr>');
                    return;
                }
                $('#tbody').empty(); // 기존 내용 제거

                // 목록을 테이블에 추가
                $(list).each(function(idx, board) {
                    const formattedDate = formatDate(board.communityDate);
                    let html = `
                        <tr>
                            <td>${board.communityId}</td>
                            <td><a href="read?communityId=${board.communityId}">${board.communityTitle}</a></td>
                            <td>${board.memberId}</td>
                            <td>${board.communityView}</td>
                            <td>${formattedDate}</td>
                        </tr>
                    `;
                    $('#tbody').append(html); // 테이블에 추가
                });

                // 페이지 네비게이션 업데이트
                updatePagination(currentPage, totalPages);
            },
            error: function() {
                alert("목록 조회 실패");
            }
        });
    }

    // 페이지 네비게이션 업데이트
    function updatePagination(currentPage, totalPages) {
        $('#prevPage').prop('disabled', currentPage == 0);
        $('#nextPage').prop('disabled', currentPage == totalPages - 1);
        $('#pageNumbers').empty();

        // 페이지 번호 버튼 생성
        for (let i = 0; i < totalPages; i++) {
                    let pageButton = $('<button>')
                        .addClass('pageButton')
                        .text(i + 1)
                        .click(function() {
                            changePage(i);  // 클릭 시 해당 페이지로 이동
                        });

                    if (i === currentPage) {
                        pageButton.prop('disabled', true);  // 현재 페이지는 비활성화
                    }

                    $('#pageNumbers').append(pageButton);
                }
            }

            // 페이지 전환 함수
                window.changePage = function(newPage) {
                    if (newPage < 0 || newPage >= totalPages) return;  // 유효한 페이지 번호인지 확인
                    currentPage = newPage;  // 현재 페이지 업데이트
                    getList(currentPage);  // 목록 갱신
                };

    // 페이지 로드 시 목록 조회 (초기 페이지 0번)
    getList(currentPage);

    // Prev 버튼 클릭 이벤트
        $('#prevPage').click(function() {
            if (currentPage > 0) {
                currentPage--; // 현재 페이지 감소
                getList(currentPage);
            }
        });

        // Next 버튼 클릭 이벤트
        $('#nextPage').click(function() {
            if (currentPage < totalPages - 1) {
                currentPage++; // 현재 페이지 증가
                getList(currentPage);
            }
        });
});
