import React, { useState, useEffect } from 'react';
import { socket } from './socket';
import { ImageViewer } from './components/ImageViewer';
import { MainNavbar } from './components/MainNavbar';
import 'bootstrap/dist/css/bootstrap.min.css'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import './App.css' 
import DummyComponent from './components/DummyComponent';

export default function App() {
  
  const [isConnected, setIsConnected] = useState(socket.connected);
  const [imageData, setImageData] = useState({
    doc: 'no-data',
    page_cnt: 0,
    curr_page_idx: 0,
    curr_page_img: 'no-data',
  });

  useEffect(() => {
    function onConnect() {
      setIsConnected(true);
    }

    function onDisconnect() {
      setIsConnected(false);
    }

    function onImageData(value) {
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
      <DummyComponent imageData={imageData} />
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