    function addToCart(button) {
    const tableName = button.getAttribute('data-table-name');
    const id = button.getAttribute('data-id');

    fetch(`/cart/add`, {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
},
    body: JSON.stringify({ tableName: tableName, id: id }),
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