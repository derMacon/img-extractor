import React from 'react';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Pagination from 'react-bootstrap/Pagination';

import { turnToSpecificPage } from '../logic/ApiFetcher';

class DocNavigation extends React.Component {

  constructor(props) {
    super(props);
  }

  // TODO use APIFetcher class
  static SERVER_BASE = 'http://localhost:5000/api/v1'
  static ENDPOINT_NEXT_PAGE = DocNavigation.SERVER_BASE + '/next-page'
  static ENDPOINT_PREV_PAGE = DocNavigation.SERVER_BASE + '/previous-page'


  turnNextPage(endpoint) {
    fetch(endpoint)
      .then(response => response.json())
      .then(data => console.log(data))
      .catch(error => console.error(error));
  }

  handleKeyPress(event) {
    if (event.key === 'Enter') {
      const inputValue = event.target.value
      const currPage = parseInt(inputValue, 10)

      if (!isNaN(currPage)) {
        console.log('go to page: ' + currPage);
        turnToSpecificPage(currPage)
        event.target.value = currPage
      }

    }
  }

  render() {

    const currPage = String(this.props.imageData.curr_page_idx + 1)
    const pageCnt = String(this.props.imageData.page_cnt)

    return (
        <Pagination>
        <Pagination.Prev onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_PREV_PAGE)} />
        <InputGroup>
          <Form.Control
            type="text"
            placeholder={currPage}
            aria-label="Input group example"
            aria-describedby="btnGroupAddon"
            onKeyPress={this.handleKeyPress}
          />
          <InputGroup.Text id="btnGroupAddon"> / {pageCnt}</InputGroup.Text>
        </InputGroup>
        <Pagination.Next onClick={() => this.turnNextPage(DocNavigation.ENDPOINT_NEXT_PAGE)} />
        </Pagination>
    )
  }
}

export default DocNavigation