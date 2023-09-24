const SERVER_BASE = 'http://localhost:5000/api/v1'


export function turnNextPage() {
    fetch(SERVER_BASE + '/next-page')
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export function loadExistingNav(fileName) {
    fetch(SERVER_BASE + '/load-existing?' + new URLSearchParams({
        filename: fileName,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}


export function turnToSpecificPage(pageIdx) {
    fetch(SERVER_BASE + '/go-to-page?' + new URLSearchParams({
        page_idx: pageIdx,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));

}
