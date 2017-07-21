function loadStatusFixed() {
    $('#modal_loading').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    });
}

function loadStatusFixedClose() {
    $('#modal_loading').modal({
        backdrop: 'static',
        keyboard: false,
        show: false
    });
}

function loadSelecionarUsuarioMensagemFixed() {
    $('#modal_selecionar_usuario').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    });
}

$(document).ready(function () {
    $('.disabled').on('click', function (e) {
        e.preventDefault();
        return false;
    });
});

function loadConcluirSalvarRemessaFixed() {
    $('#modal_concluir_salvar_remessa').modal({
        backdrop: 'static',
        keyboard: true,
        show: true
    });
}
