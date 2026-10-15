const formulario = document.getElementById("formPrevisao");
const botaoPesquisar = formulario.querySelector('button[type="submit"]');
const campoCidade = document.getElementById("cidade");
const campoDias = document.getElementById("dias");
const mensagemErro = document.getElementById("mensagemErro");
const resultadoPrevisao = document.getElementById("resultadoPrevisao");
const tituloPrevisao = document.getElementById("tituloPrevisao");
const previsaoDias = document.getElementById("previsaoDias");

formulario.addEventListener("submit", function(event) {
    event.preventDefault();

    const cidade = campoCidade.value.trim();
    const dias = campoDias.value;


    mensagemErro.textContent = "";
    mensagemErro.classList.remove("erro");

    if (cidade === "") {
        mensagemErro.textContent = "Digite o nome da sua cidade.";
        mensagemErro.classList.add("erro");
        return;
    };
    
    botaoPesquisar.disabled = true;
    botaoPesquisar.textContent = "Consultando...";

    resultadoPrevisao.hidden = true;
    previsaoDias.textContent = "";
    tituloPrevisao.textContent = "";

    fetch(
        `/api/previsao?cidade=${encodeURIComponent(cidade)}&dias=${dias}`
    )
        .then(response => {
            if (!response.ok) {
                throw new Error("Falha ao consultar a API");
            }

            return response.json();
        })
        .then(dados => {
            tituloPrevisao.textContent = 
                `${dados.cidade} - ${dados.pais}`;

            dados.dias.forEach(dia => {
                const cartao = document.createElement("article");
                cartao.classList.add("cartao-previsao");

                const data = document.createElement("h3");
                data.textContent = new Date(dia.data + "T12:00:00")
                    .toLocaleDateString("pt-BR", {
                        weekday: "long",
                        day: "2-digit",
                        month: "2-digit",
                    });
                
                const icone = document.createElement("p");
                icone.classList.add("icone-previsao");
                icone.textContent = obterIcone(dia.condicao);

                const condicao = document.createElement("p");
                condicao.textContent = dia.condicao;

                const temperatura = document.createElement("p");
                temperatura.classList.add("temperatura");
                temperatura.textContent = 
                    `Máxima: ${dia.temperaturaMaxima} °C | Mínima: ${dia.temperaturaMinima} °C`;
                
                
                cartao.append(data, icone, condicao, temperatura);
                previsaoDias.appendChild(cartao);
            });

            resultadoPrevisao.hidden = false;
        })
        .catch(erro => {
            console.error("Erro ao consultar a previsão:", erro);

            mensagemErro.textContent =
                "Não foi possível consultar a previsão. Verifique a cidade e tente novamente.";

            mensagemErro.classList.add("erro");

            resultadoPrevisao.hidden = true;
            previsaoDias.textContent = "";
            tituloPrevisao.textContent = "";
        })
        .finally(() => {
            botaoPesquisar.disabled = false;
            botaoPesquisar.textContent = "Pesquisar previsão";
        });
});