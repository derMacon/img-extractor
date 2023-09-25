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

export async function fetchFileHistory() {

    console.log('======> before')
    // fetch(SERVER_BASE + '/test-log')
    //     .then(response => response)
    //     .then(data => console.log('---- inner: ', data))
    //     .catch(error => console.error(error));

    try {
      const tmp = await fetch(SERVER_BASE + '/file-history');
      const text = await tmp.text()
      console.log(text)
    //   const response = await fetch(SERVER_BASE + '/file-history');
    //   console.log('response: ', response);
  
    //   if (!response.ok) {
    //     throw new Error('Network response was not ok');
    //   }
  
    //   const data = await response.json();
    //   return data;
    } catch (error) {
      console.error('Error fetching file history:', error);
      throw error;
    }
    return ['test-1.pdf']

  }