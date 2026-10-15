function obterIcone(condicao) {
    const descricao = condicao.toLowerCase();

    if (descricao.includes("tempestade") ||
        descricao.includes("trovoada")) {
        return "⛈️";
    }

    if (descricao.includes("chuva") ||
        descricao.includes("chuvoso") ||
        descricao.includes("garoa")) {
        return "🌧️";
    }

    if (descricao.includes("neve")) {
        return "❄️";
    }

    if (descricao.includes("neblina") ||
        descricao.includes("névoa")) {
        return "🌫️";
    }

    if (descricao.includes("nublado") ||
        descricao.includes("nuvem") ||
        descricao.includes("encoberto")) {
        return "☁️";
    }

    if (descricao.includes("sol") ||
        descricao.includes("limpo")) {
        return "☀️";
    }

    return "🌡️";
}