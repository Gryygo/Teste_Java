function fakeLinkSocio (id) {
    return window.location.href = `http://localhost:8080/socios/${id}`; // !! Deve ser atualizado no deploy
}

function fakeLinkDep (id) {
    return window.location.href = `http://localhost:8080/dependentes/${id}`; // !! Deve ser atualizado no deploy
}

