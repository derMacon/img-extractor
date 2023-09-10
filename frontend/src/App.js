import React, { useState, useEffect } from 'react';
import { socket } from './socket';
import { ConnectionState } from './components/ConnectionState';
import { ImageViewer } from './components/ImageViewer';
import MainNavbar from './components/MainNavbar';
import 'bootstrap/dist/css/bootstrap.min.css'

export default function App() {
  const [isConnected, setIsConnected] = useState(socket.connected);
  const [imageData, setImageData] = useState('no-data');

  useEffect(() => {
    function onConnect() {
      setIsConnected(true);
    }

    function onDisconnect() {
      setIsConnected(false);
    }

    function onImageData(value) {
      setImageData(value)
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
      <MainNavbar />
      <ConnectionState isConnected={ isConnected } />
      <ImageViewer imageData={ imageData } />
    </div>
  );
}