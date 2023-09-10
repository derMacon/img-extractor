import React from 'react';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

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
      <ButtonGroup aria-label="doc-nav">
        <Button variant="secondary" onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_PREV_PAGE)}>prev</Button>
        <Button variant="secondary" onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_NEXT_PAGE)}>next</Button>
      </ButtonGroup>
    )
  }
}

export default DocNavigation