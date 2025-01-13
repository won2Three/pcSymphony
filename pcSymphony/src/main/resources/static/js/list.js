    $(document).ready(function() {
    // 글쓰기 버튼
    $('#writeButton').click(function(){
        location.href = 'write'; // 글쓰기 페이지로 이동
    });

  // 날짜 형식을 yyyy-MM-dd HH:mm 형태로 변환하는 함수
    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    // 목록 조회 함수
    function getList() {
    $.ajax({
    url: 'getList', // 서버에서 목록을 가져오는 URL
    method: 'POST',
    success: function(list) {
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
},
    error: function() {
    alert("목록 조회 실패");
}
});
}

    // 페이지 로드 시 목록 조회
    getList();
});