function deleteUser(id){
    $.ajax({
        url: "/sys/users/card/delete/" + id,
        success: function (id) {
            $('#delTitle').text('Удалить пользователя № ' + id + ' ?');
            $('#delId').val(id);
            location.reload();
        }
    });
}