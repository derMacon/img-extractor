import React from 'react';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Pagination from 'react-bootstrap/Pagination';
import { turnToSpecificPage, turnNextPage, turnPrevPage } from '../logic/ApiFetcher';

class DocNavigation extends React.Component {

  constructor(props) {
    super(props);
  }

  handleKeyPress(event) {
    if (event.key === 'Enter') {
      const inputValue = event.target.value
      const currPage = parseInt(inputValue, 10)

      if (!isNaN(currPage)) {
        console.log('go to page: ' + currPage);
        turnToSpecificPage(currPage)
        event.target.placeholder = currPage 
        event.target.value = ''
      }

    }
  }

  render() {

    const currPage = String(this.props.imageData.curr_page_idx + 1)
    const pageCnt = String(this.props.imageData.page_cnt)

    return (
        <Pagination>
        <Pagination.Prev onClick={turnPrevPage} />
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
        <Pagination.Next onClick={turnNextPage} />
        </Pagination>
    )
  }
}

export default DocNavigation