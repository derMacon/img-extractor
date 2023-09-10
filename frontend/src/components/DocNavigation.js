import React from 'react';

class DocNavigation extends React.Component {

  static SERVER_BASE = 'http://localhost:5000/api/v1'
  static ENDPOINT_NEXT_PAGE = DocNavigation.SERVER_BASE + '/next-page'
  static ENDPOINT_PREV_PAGE = DocNavigation.SERVER_BASE + '/previous-page'


  turnNextPage(endpoint) {
    fetch(endpoint)
      .then(response => response.json())
      .then(data => console.log(data))
      .catch(error => console.error(error));
  }

  render() {
    return (
      <div>
        <button onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_PREV_PAGE)}>prev</button>
        <button onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_NEXT_PAGE)}>next</button>
      </div>
    )
  }
}

export default DocNavigation