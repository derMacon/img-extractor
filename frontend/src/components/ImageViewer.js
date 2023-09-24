import React from 'react';
import Card from 'react-bootstrap/Card';
import DocNavigation from './DocNavigation';

export function ImageViewer({ imageData }) {
  // return <img src={'data:image/jpeg;base64,' + imageData}></img>;
  return (
    <Card>
      <Card.Header>
        <DocNavigation />
      </Card.Header>
      <Card.Body>
        <Card.Img variant="top" src={'data:image/jpeg;base64,' + imageData} />
      </Card.Body>
    </Card>
  );
}