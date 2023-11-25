function addProdutoCarrinho(id) {

    $.post("/addProdutoCarrinho", {id: id}, function (retorno) {

        $("#campo_carrinho_" + id).val(retorno);

    });

}

function deleteProdutoCarrinho(id) {

    $.post("/removeProdutoCarrinho", {id: id}, function (retorno) {

        $("#campo_carrinho_" + id).val(retorno);

    });

}

function carregarNumeroProdutosCarrinho() {

    $.get("/carregarNumeroProdutosCarrinho", function (resposta) {

        $("#carrinho-quantidade").text(resposta);

    });

}

function carregarStatusPagamento(txid, qrcode, idCampoRetorno) {

    $.get("/carregarStatusPagamento", {txid: txid, qrcode: qrcode}, function (retorno) {

        $("#" + idCampoRetorno).html(retorno);

        if (retorno === "CONCLUIDA") {

            window.location.href = "/pagina-checkout-aprovado";

        }

        if (retorno === "REPROVADO") {

            window.location.href = "/pagina-checkout-reprovado";

        }

    });

}

$(document).ready(function () {

    setInterval(function () {

        carregarNumeroProdutosCarrinho();

    }, 2000);

});
