const URL_USER_CARD = "/sys/users/card/"

function deleteUser(id){
    $.ajax({
        url: "/sys/users/card/delete/" + id,
        success: function (id) {
            $('#delTitle').text('Удалить пользователя c ID ' + id + ' ?');
            $('#delId').val(id);
            location.reload();
        }
    });
}

function editUser(id){
    $(location).attr('href',URL_USER_CARD+id);

}