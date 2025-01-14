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
                updatePagination(page, totalPages);
            },
            error: function() {
                alert("목록 조회 실패");
            }
        });
    }

    // 페이지 전환 함수
    window.changePage = function(newPage) {  // 전역으로 선언하여 onclick에서 접근할 수 있게 설정
        if (newPage < 0 || newPage >= totalPages) return;
        currentPage = newPage;
        getList(currentPage);
    }

    // 페이지 네비게이션 업데이트
    function updatePagination(currentPage, totalPages) {
        $('#prevPage').prop('disabled', currentPage == 0);
        $('#nextPage').prop('disabled', currentPage == totalPages - 1);
        $('#pagination .currentPage').text(currentPage + 1);
        $('#pagination .totalPages').text(totalPages);

        // 다음 버튼의 onclick 이벤트 수정
        $('#nextPage').attr('onclick', `changePage(${currentPage + 1})`);
        // 이전 버튼의 onclick 이벤트 수정
        $('#prevPage').attr('onclick', `changePage(${currentPage - 1})`);
    }

    // 페이지 로드 시 목록 조회 (초기 페이지 0번)
    getList(currentPage);
});
