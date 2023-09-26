const SERVER_BASE = 'http://localhost:5000/api/v1'

// TODO check if response is ok in all calls

export function turnNextPage() {
    console.log('turn next page')
    fetch(SERVER_BASE + '/next-page')
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export function turnPrevPage() {
    console.log('turn prev page')
    fetch(SERVER_BASE + '/previous-page')
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export function loadExistingNav(fileName) {
    console.log('load existing nav')
    fetch(SERVER_BASE + '/load-existing?' + new URLSearchParams({
        filename: fileName,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}


export function turnToSpecificPage(pageIdx) {
    console.log('turn to specific page')
    fetch(SERVER_BASE + '/go-to-page?' + new URLSearchParams({
        page_idx: pageIdx,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export async function fetchFileHistory() {
    console.log('fetching file history')
    try {
      const tmp = await fetch(SERVER_BASE + '/file-history');
      return await tmp.json()
    } catch (error) {
      console.error('Error fetching file history:', error);
      throw error;
    }
}