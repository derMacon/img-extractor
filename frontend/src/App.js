import React, { useState, useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css' 
import { socket } from './logic/ApiFetcher';
import { ImageViewer } from './components/ImageViewer';
import { MainNavbar } from './components/MainNavbar';
import { setSystemClipboard } from './logic/ClipboardUtils';

export default function App() {
  
  const [isConnected, setIsConnected] = useState(socket.connected);
  const [imageData, setImageData] = useState({
    doc: 'no-data',
    page_cnt: 0,
    curr_page_idx: 0,
    curr_page_img: '',
  });

  useEffect(() => {
    document.title = "img-viewer"
    function onConnect() {
      setIsConnected(true);
    }

    function onDisconnect() {
      setIsConnected(false);
      alert('backend offline - check the docker compose setup on the host')
    }

    function onImageData(value) {
      setSystemClipboard(value.curr_page_img)
      setImageData(value)
    }

    socket.on('connect', onConnect);
    socket.on('disconnect', onDisconnect);
    socket.on('update_image', onImageData);

    return () => {
      socket.off('connect', onConnect);
      socket.off('disconnect', onDisconnect);
      socket.off('update_image', onImageData);
    };
  }, []);


  return (
    <div className="App">
      <MainNavbar isConnected={ isConnected } title={ imageData.doc }/>
      <Container fluid="md">
        <Row>
          <Col>
            <ImageViewer imageData={ imageData } />
          </Col>
        </Row>
      </Container>
    </div>
  );
}