$(document).ready(function () {

    var elemento = document.getElementById("idCampoRetornoPagamentoQrCode");

    if (elemento) {

        var txid = elemento.getAttribute("data-txid");
        var qrcode = elemento.getAttribute("data-qrcode");

        setInterval(function () {

            carregarStatusPagamento(txid, qrcode, "idCampoRetornoPagamentoQrCode");

        }, 2000);

    }

});

