const SERVER_BASE = 'http://localhost:5000/api/v1'

// TODO check if response is ok in all calls

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

export function fetchFileHistory() {
    console.log('fetchFileHistory called')
    const fileHistory = [];

    // TODO await this
    fetch(SERVER_BASE + '/file-history')
        .then(response => response.json())
        .then(response => fileHistory.push(response))
        .then(data => console.log('out: ', data))
        .catch(error => console.error(error));


    return fileHistory

    // return ['test-pdf-1.pdf', 'test-pdf-2.pdf']
}
