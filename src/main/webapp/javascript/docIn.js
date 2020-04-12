$.dateCut = function(date) {
    var d = new Date(date);
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear();
    if (day < 10) {
        day = "0" + day;
    }
    if (month < 10) {
        month = "0" + month;
    }
    var date = year + "-" + month + "-" + day;
    return date;
};

function deleteDoc(id){
    $.ajax({
        url: "/docs/in/card/" + id,
        success: function (doc) {
            $('#delIdDocIn').val(doc.id);
            addDelTxt('.txtDocIn', 'Входящий документ №' + doc.regNumber + ' будет удален, вы настаиваете на удалении?');
            if (doc.taskId != null) {
                $('#delIdTask').val(doc.taskId);
                addDelTxt('.txtTask', 'Входящий документ №:' + doc.regNumber + ' имеет связанное поручение №' + doc.taskId);
            }
            if (doc.docOutId != null) {
                $('#delIdDocOut').val(doc.docOutId);
                addDelTxt('.txtDocOut', 'Входящий документ №:' + doc.regNumber + ' имеет связанный исходящий документ №' + doc.docOutNumber);
            }
        }
    });
}

function addDelTxt(dest, txt) {
    $(dest).empty();
    $(dest).append('<h5 class="modal-title">' + txt + '</h5>');
}

function clearFilter() {
    $('.reset').val('');
<!--        $('.reset').prop('checked', false);-->
}

function closeModal() {
    var modal  = document.querySelector('#showDoc');
    modal.classList.toggle('show');
    modal.style.display = "none";
}

function openModal(id){
        var modal  = document.querySelector('#showDoc');
        modal.classList.toggle('show');
        modal.style.display = "block";
    $.ajax({
        url: "/docs/in/card/" + id,
        success: function (doc) {
            if (doc.id == null) {
                $('#titleM').text('Входящий документ');
                $('#regDateT').text($.dateCut(new Date()));
            } else {
                $('#titleM').text('Входящий документ №:' + doc.regNumber + ' ' + doc.stateName);
                $('#regDate').val(doc.regDate);
                $('#regDateT').text($.dateCut(doc.regDate));
                $('#outgoingDate').val($.dateCut(doc.outgoingDate));
            }
            $('#id').val(doc.id);
            $('#stateId').val(doc.stateId);
            $('#regNumber').val(doc.regNumber);
            $('#userId').val(doc.userId);
            $('#userFIO').text(doc.userFIO);
            $('#departmentId').val(doc.departmentId);
            $('#docTypeId').val(doc.docTypeId);
            $('#sender').val(doc.sender);
            $('#outgoingNumber').val(doc.outgoingNumber);
            $('#content').text(doc.content);
            $('#pages').val(doc.pages);
            $('#appendix').val(doc.appendix);
            $('#note').val(doc.note);
            deleteButton();
            if (doc.id != null) {
                $('#communication').text('Связи');
                if (doc.taskId != null) {
                    addButton('.addBtnTask', 'taskBtn', '/tasks/card/' + doc.taskId, 'Поручение', doc.taskId);
                } else {
                    addButton('.addBtnTask', 'taskBtn', '/tasks/card/?type=1&docId=' + doc.id, 'Поручение', 'Создать');
                }
                if (doc.docOutId != null) {
                    addButton('.addBtnDO', 'docOutBtn', '/docs/out?openDO=' + doc.docOutId, 'Ответ', doc.docOutNumber);
                } else {
                    addButton('.addBtnDO', 'docOutBtn', '/docs/out?openDO=-1', 'Ответ', 'Создать');<!-- добавить ссыль на нужный метод -->
                }
            }
        }
    });
}

function deleteButton() {
    $('#communication').text('');
    $('.addBtnTask').empty();
    $('.addBtnDO').empty();
}

function addButton(dest, linkId, urlMethod, linkTxt, aTxt) {
        $(dest).append('<label for="' + linkId + '" class="col-sm-3 col-form-label">' + linkTxt + '</label>');
        $(dest).append('<a id="' + linkId + '" href="' + urlMethod + '" role="button" aria-pressed="true">' + aTxt + '</a>');
}

$(document).ready(function() {
    var url = new URLSearchParams(window.location.search);
    var openDI = url.get('openDI');
    if (openDI != null) {
        openModal(openDI)
    }
});