
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
                $('#delTitle').text('Входящий документ №:' + doc.regNumber + ' будет удален, вы настаиваете на удалении?');
                $('#delId').val(doc.id);
            }
        });
    }

    function clearFilter() {
        $('.reset').val('');
<!--        $('.reset').prop('checked', false);-->
    }


    function openModal(id){
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
                if (doc.id != null) {
                    $('#communication').text('Связи');
                    if (doc.taskId != null) {
                        addButton('taskId', '/tasks/' + doc.id, 'Поручение', doc.taskId);<!-- добавить ссыль на нужный метод -->
                    } else {
                        addButton('taskId', '/tasks/' + doc.id, 'Поручение', 'Создать');<!-- добавить ссыль на нужный метод -->
                    }
<!--                    if (doc.taskId != null) {-->
<!--                        addButton('docOutId', '/docs/out/' + doc.id, 'Ответ', doc.docOutId);-->
<!--                    } else {-->
<!--                        addButton('docOutId', '/docs/out/' + doc.id, 'Ответ', 'Создать');-->
<!--                    }-->
                }
            }
        });
    }

    function addButton(linkId, urlMethod, linkTxt, aTxt) {
        $('.addBtn').append('<label for="' + linkId + '" class="col-sm-3 col-form-label">' + linkTxt + '</label>');
        $('.addBtn').append('<a id="' + linkId + '" href="' + urlMethod + '" role="button" aria-pressed="true">' + aTxt + '</a>');
    }