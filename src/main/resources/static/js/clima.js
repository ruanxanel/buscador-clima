const campoCidade = document.getElementById("cidade");
const botaoPesquisar = document.querySelector("button");
const mensagemErro = document.getElementById("mensagemErro");
const resultadoClima = document.getElementById("resultadoClima");
const secaoPrevisao = document.getElementById("secaoPrevisao");
const previsaoDias = document.getElementById("previsaoDias");

botaoPesquisar.addEventListener("click", function() {
    const cidade = campoCidade.value.trim();
    
    mensagemErro.textContent = "";
    mensagemErro.classList.remove("erro", "carregando");
    
    if (cidade === "") {
        mensagemErro.textContent = "Digite o nome da sua cidade.";
        mensagemErro.classList.add("erro");
        return;
    };
    
    resultadoClima.hidden = true;
    secaoPrevisao.hidden = true;
    previsaoDias.textContent = "";

    botaoPesquisar.disabled = true;
    botaoPesquisar.textContent = "Consultando...";

    mensagemErro.textContent = "Consultando o clima...";
    mensagemErro.classList.add("carregando");

    fetch(`/api/clima?cidade=${encodeURIComponent(cidade)}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Falha ao consultar a API");
        }

        return response.json();
    })
    .then(dados => {
        document.getElementById("cidadeResultado").textContent =
            dados.cidade;

        document.getElementById("pais").textContent =
            dados.pais;

        document.getElementById("temperatura").textContent =
            dados.temperaturaCelsius + " °C";

        document.getElementById("sensacaoTermica").textContent =
            dados.sensacaoTermica + " °C";

        document.getElementById("umidade").textContent =
            dados.umidade + " %";

        document.getElementById("velocidadeVento").textContent =
            dados.velocidadeVento + " km/h";

        document.getElementById("condicao").textContent = 
            dados.condicao;

        document.getElementById("iconeClima").textContent = 
            obterIcone(dados.condicao);

        const dataConsulta = new Date(dados.consultadoEm);

        document.getElementById("consultadoEm").textContent = 
            dataConsulta.toLocaleString("pt-BR");
        
        resultadoClima.hidden = false;
        
        mensagemErro.textContent = "";
        mensagemErro.classList.remove("erro", "carregando");

        return buscarPrevisao(cidade);
    }).catch(erro => {
        console.error("Erro ao conectar o clima:", erro);

        resultadoClima.hidden = true;
        secaoPrevisao.hidden = true;

        mensagemErro.textContent =
            "Não foi possível conectar o clima. Verifique o nome da cidade.";

        mensagemErro.classList.remove("carregando");
        mensagemErro.classList.add("erro");
    })
    .finally(() => {
        botaoPesquisar.disabled = false;
        botaoPesquisar.textContent = "Pesquisar";
    });
});

function buscarPrevisao(cidade) {

    secaoPrevisao.hidden = false;
    previsaoDias.textContent = "Consultando previsão...";

    return fetch(
        `/api/previsao?cidade=${encodeURIComponent(cidade)}&dias=3`
    )
        .then(response => {
            if (!response.ok) {
                throw new Error("Falha ao consultar a API");
            }

            return response.json();
        })
        .then(dados => {
            previsaoDias.textContent = "";

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
        })
        .catch(erro => {
            console.error("Erro ao consultar a previsão:", erro);

            previsaoDias.textContent = "";

            const aviso = document.createElement("p");
            aviso.classList.add("previsao-inicial");
            aviso.textContent = "Não foi possível carregar a previsão.";

            previsaoDias.appendChild(aviso);
        });
};