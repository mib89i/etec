$(document).ready(load_masks);

function load_masks() {
    $('.mask_data').mask('00/00/0000', {clearIfNotMatch: true});
    $('.mask_hora').mask('00:00', {clearIfNotMatch: true});
    $('.mask_telefone').mask('(99) 9999?9-9999');
    $('.mask_telefone').on('blur', function () {
        var last = $(this).val().substr($(this).val().indexOf("-") + 1);

        if (last.length === 3) {
            var move = $(this).val().substr($(this).val().indexOf("-") - 1, 1);
            var lastfour = move + last;

            var first = $(this).val().substr(0, 9);

            $(this).val(first + '-' + lastfour);
        }
    });
    $('.mask_cpf').mask('999.999.999-99');
}