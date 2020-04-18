
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


function deleteDocOut(id){
    $.ajax({
        url: "/docs/out/card/" + id,
        success: function (doc) {
            $('#delTitle').text('Удалить исходящий № ' + doc.number + ' ?');
            $('#delId').val(doc.id);
        }
    });
}

function closeModal() {
    var modal= document.querySelector('#showDoc');
    modal.classList.toggle('show');
    modal.style.display = "none";

    var modal1 = document.querySelector('#newDoc');
    modal1.classList.toggle('show');
    modal1.style.display = "none";
}


$(document).ready(function() {
    var url = new URLSearchParams(window.location.search);
    var openDO = url.get('openDO');
    var docinid=url.get('docinid');
    if (openDO!=null) {
        openModal(openDO, docinid)
    }
});

function openModal(id, docinid){
    var modal
    if(id>0) {
        modal= document.querySelector('#showDoc');
    } else {
        modal = document.querySelector('#newDoc');
    }
    modal.classList.toggle('show');
    modal.style.display = "block";
    console.log(docinid);

    if(id>0) {
        $.ajax({
            url: "/docs/out/card/" + id,

            docOutDTO: {docOutDTO: 'docOutDTO'},
            success: function (docOutDTO) {
                $('#titleM').text('Исходящий №: ' + docOutDTO.number + '   ' + docOutDTO.state.name);
                $('#createDate').val(docOutDTO.createDate);
                $('#createDateT').text($.dateCut(docOutDTO.createDate));
                $('#id').val(docOutDTO.id);
                $('#creator').val(docOutDTO.creator);
                $('#creatorFIO').text(docOutDTO.creatorFIO);
                $('#docTypeId').val(docOutDTO.docTypeId);
                $('#stateT').text(docOutDTO.state.name);
                $('#stateS').val(docOutDTO.state);
                $('#stateId').val(docOutDTO.stateId);
                $('#signer').val(docOutDTO.signer);
                $('#signerId').val(docOutDTO.signerId);
                $('#content').val(docOutDTO.content);
                $('#pages').val(docOutDTO.pages);
                $('#appendix').val(docOutDTO.appendix);
                $('#noteN').val(docOutDTO.note);
                $('#isGenerated').val(docOutDTO.isGenerated);
                $('#number').val(docOutDTO.number);

                if (docOutDTO.taskId != null || docOutDTO.docInId != null) {
                    $('#communication').text('Связи');
                    if (docOutDTO.taskId != null) {
                        linkButton('.linkTask', 'taskBtn', '/tasks/card/' + docOutDTO.taskId, 'Поручение', docOutDTO.task.taskName);
                    }
                    if (docOutDTO.docInId != null) {
                        linkButton('.linkDocIn', 'docInBtn', '/docs/in?openDI=' + docOutDTO.docInId, 'Входящий', docOutDTO.docInRegNumber);
                    }
                }
            }
        });
    }else{
        $.ajax({

            url: "/docs/out/newcard/" + "?docinid=" + docinid,

            success: function (docOutDTO) {

                $('#creatorNew').val(docOutDTO.creator);
                $('#docTypeIdNew').val(docOutDTO.docTypeId);
                $('#signerNew').val(docOutDTO.signer);
                $('#contentNew').val(docOutDTO.content);
                $('#pagesNew').val(docOutDTO.pages);
                $('#regDate').val(docOutDTO.regDate);
                $('#appendixNew').val(docOutDTO.appendix);
                $('#noteNew').val(docOutDTO.note);
                $('#isGeneratedNew').val(docOutDTO.isGenerated);
                $('#numberNew').val(docOutDTO.number);
                $('#stateNew').val(docOutDTO.state);


                if (docOutDTO.taskId != null || docinid != null) {
                    $('#communicates').text('Связи');
                }
                if (docOutDTO.taskId != null) {
                    linkButton('.linkTaskNew', 'taskBtnNew', '/tasks/card/' + docOutDTO.taskId, 'Поручение', docOutDTO.task.taskName);
                }
                if (docinid != null) {
                    linkButton('.linkDocInNew', 'docInBtnNew', '/docs/in?openDI=' + docinid, 'Входящий', docOutDTO.docInRegNumber);
                }
                $('#docInIdNew').val(docOutDTO.docInId);
            }
        });
    }
}

function linkButton(dest, linkId, urlMethod, linkTxt, aTxt) {
    $(dest).empty();
    $(dest).append('<label for="' + linkId + '" class="col-sm-3 col-form-label">' + linkTxt + '</label>');
    $(dest).append('<a id="' + linkId + '" href="' + urlMethod + '" role="button" aria-pressed="true">' + aTxt + '</a>');
}

