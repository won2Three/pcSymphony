    // 폼 제출 시 제목과 내용이 빈 값인지를 확인하는 함수
    function validateForm() {
    var title = document.getElementById("communityTitle").value;
    var content = document.getElementById("communityContent").value;

    if (title.trim() === "") {
    alert("제목을 입력해주세요.");
    return false;
}
    if (content.trim() === "") {
    alert("내용을 입력해주세요.");
    return false;
}
    return true; // 유효성 검사 통과
}