import React from 'react';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Pagination from 'react-bootstrap/Pagination';

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
        <Pagination>
        <Pagination.Prev onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_PREV_PAGE)} />
        <InputGroup>
          <Form.Control
            type="text"
            placeholder="1"
            aria-label="Input group example"
            aria-describedby="btnGroupAddon"
          />
          <InputGroup.Text id="btnGroupAddon"> / 42</InputGroup.Text>
        </InputGroup>
        <Pagination.Next onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_NEXT_PAGE)} />
        </Pagination>
    )
  }
}

export default DocNavigation