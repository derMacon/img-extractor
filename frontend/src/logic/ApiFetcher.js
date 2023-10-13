import { io } from 'socket.io-client';

const SERVER_ADDRESS = 'http://localhost:5000'
export const SERVER_BASE = SERVER_ADDRESS + '/api/v1'
export const socket = io(SERVER_ADDRESS)

// TODO check if response is ok in all calls

export function turnNextPage() {
    fetch(SERVER_BASE + '/next-page')
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export function turnPrevPage() {
    fetch(SERVER_BASE + '/previous-page')
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export async function loadExistingNav(fileName) {
    try {
    await fetch(SERVER_BASE + '/load-existing?' + new URLSearchParams({
        filename: fileName,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
    } catch (error) {
        console.error('could not load existing document')
    }
}

export function turnToSpecificPage(pageIdx) {
    fetch(SERVER_BASE + '/go-to-page?' + new URLSearchParams({
        page_idx: pageIdx,
    }))
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

export async function fetchFileHistory() {
    try {
      const tmp = await fetch(SERVER_BASE + '/file-history');
      return await tmp.json()
    } catch (error) {
      console.error('Error fetching file history:', error);
      throw error;
    }
}

export async function uploadFile(file) {
    const formData = new FormData();
    formData.append('doc', file);

    try {
        const tmp = await fetch(SERVER_BASE + '/load-new', {
            method: 'POST',
            body: formData,
        })
        await tmp.text()
    } catch (error) {
        console.log(error)
    }
}

export function teardown() {
    fetch(SERVER_BASE + '/teardown')
        .then(data => console.log(data))
        .catch(error => console.error(error));
}
