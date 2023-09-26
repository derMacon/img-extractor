import React from 'react';
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { loadExistingNav } from '../logic/ApiFetcher';
import Form from 'react-bootstrap/Form';
import { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';

import { fetchFileHistory, uploadFile } from '../logic/ApiFetcher';


function FileMenu() {
  const [show, setShow] = useState(false);
  const [fileHistory, setFileHistory] = useState([]);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);


  const loadHistFiles = () => {
    async function fetchData() {
      try {
        const data = await fetchFileHistory();
        console.log('........Response from fetchFileHistory:', data);
        setFileHistory(data);
      } catch (error) {
        console.error('Error fetching file history:', error);
      }
    }
    fetchData();
  }

  useEffect(loadHistFiles, []);


  const handleFileChange = (e) => {
    const file = e.target.files[0];
    console.log('handleFileChange')
    if (file) {
      uploadFile(file)
      console.log('...... after hist load')
      handleClose()
      setTimeout(loadHistFiles, 2000)
    }
  }

  const files = fileHistory.map((fileName) =>
      <NavDropdown.Item onClick={() => {
        loadExistingNav(fileName)
        loadHistFiles()
      }}>
          {fileName}
      </NavDropdown.Item>
  )

  return (
    <>

        <NavDropdown title="File" id="basic-nav-dropdown">
          {files}
          <NavDropdown.Divider />
          <Nav.Link href='' onClick={handleShow}>New File</Nav.Link>
        </NavDropdown>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Select File</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form.Group controlId="formFile" className="mb-3">
                <Form.Label>Select from disk</Form.Label>
                <Form.Control type="file" onChange={handleFileChange}/>
            </Form.Group>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default FileMenu