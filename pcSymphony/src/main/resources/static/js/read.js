    // 현재 커뮤니티 ID와 사용자 ID를 얻어옵니다.
    const communityId = '[[${community.communityId}]];'  // 서버에서 동적으로 값을 받아옵니다.
    const userId = '[[${#authentication.name}]];' // 로그인한 사용자 ID를 서버에서 받아옵니다.

    // 댓글 목록을 로드하는 함수
    function loadComments() {
    $.ajax({
        url: '/community/communityReplyList?communityId=' + communityId, // GET 방식으로 파라미터 전달
        type: 'GET',
        success: function(data) {
            const replyTableBody = $('#replyTable tbody');
            replyTableBody.empty(); // 기존 댓글 목록을 비웁니다.

            // 서버에서 받은 댓글 데이터를 HTML로 출력
            data.forEach(function(comment) {
                const commentHtml = `
                        <tr id="comment-${comment.communityReplyId}">
                            <td>${comment.memberId}</td>
                            <td>${comment.replyContent}</td>
                            <td>
                                <button onclick="deleteComment(${comment.communityReplyId})">삭제</button>
                            </td>
                        </tr>
                    `;
                replyTableBody.append(commentHtml);
            });
        },
        error: function(xhr, status, error) {
            alert('댓글 목록 불러오기 실패');
        }
    });
}

    // 댓글을 저장하는 함수
    $('#replyWriteButton').click(function() {
    const replyContent = $('#communityReplyContent').val();
    if (!replyContent) {
    alert('댓글을 입력하세요.');
    return;
}

    const replyData = {
    communityId: communityId,
    memberId: userId,
    replyContent: replyContent
};

    $.ajax({
    url: '/community/communityReplyWrite',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(replyData),
    success: function() {
    $('#communityReplyContent').val(''); // 댓글 입력란 초기화
    loadComments(); // 댓글 목록 새로고침
},
    error: function() {
    alert('댓글 저장 실패!');
}
});
});

    // 댓글을 삭제하는 함수
    function deleteComment(commentId) {
    const deleteData = {
    communityId: communityId,
    communityReplyId: commentId,
    memberId: userId
};

    $.ajax({
    url: '/community/communityReplyDelete',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(deleteData),
    success: function() {
    loadComments(); // 댓글 목록 새로고침
},
    error: function() {
    alert('댓글 삭제 실패!');
}
});
}

    // 페이지 로드 시 댓글 목록 불러오기
    $(document).ready(function() {
    loadComments();
});