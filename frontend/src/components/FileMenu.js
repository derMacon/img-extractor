import React from 'react';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Card from 'react-bootstrap/Card';
import { loadExistingNav } from '../logic/ApiFetcher';
import Form from 'react-bootstrap/Form';
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';


// class FileMenu extends React.Component {

//     constructor(props) {
//         super(props);
//         this.state = {
//             fileHistory: this.fetchFileHistory()
//         };
//     }

//     fetchFileHistory() {
//         return ['test-pdf-1.pdf', 'test-pdf-2.pdf']
//     }

//     appendHistory(newFileName) {
//         // todo
//         console.log("appending file history with: " + newFileName)
//         // this.setState({
//         //     fileHistory: fileHistory.concat([
//         //         {

//         //         }
//         //     ])
//         // })
//     }

//     openNewFile() {
//         console.log("open new file")
//     }

//     handleFileChange(e) {
//         // setSelectedFile(e.target.files[0]);
//         console.log('handleFileChange: ' + e)
//     };

//     handleUpload() {
//         // if (selectedFile) {
//         // You can perform file upload logic here, such as sending the file to a server.
//         // console.log('Uploading file:', selectedFile);
//         // }
//         console.log('handle upload')
//     };


//     render() {
//         // const files = this.state['fileHistory'].map((fileName) =>
//         //     <NavDropdown.Item onClick={() => loadExistingNav(fileName)}>
//         //         {fileName}
//         //     </NavDropdown.Item>
//         // )

//         // console.log('files: ' + files)

//         // return (
//         //     <NavDropdown title="File" id="basic-nav-dropdown">
//         //         {files}
//         //       <NavDropdown.Divider />
//         //       <NavDropdown.Item onClick={this.openNewFile}>
//         //         <Form.Group controlId="formFile">
//         //             <Form.Control type="file" />
//         //         </Form.Group>
//         //       </NavDropdown.Item>
//         //     </NavDropdown>
//         // )


//         return (
//             <Form.Group controlId="formFile" className="mb-3">
//                 <Form.Control type="file" onChange={() => console.log('on change')}/>
//             </Form.Group>
//         )

//     }

// }

function FileMenu() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    console.log('handleFileChange')
    if (file) {
      console.log(file);
      handleClose()
    }
  }

  return (
    <>

      <Nav.Link href='' onClick={handleShow}>New File</Nav.Link>

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