import React from 'react';
import Card from 'react-bootstrap/Card';
import DocNavigation from './DocNavigation';

export function ImageViewer({ imageData }) {
  // return <img src={'data:image/jpeg;base64,' + imageData}></img>;
  return (
    <Card style={{ width: '50rem' }}>
      <Card.Body>
        <Card.Title>Card Title</Card.Title>
        <DocNavigation />
      </Card.Body>
      <Card.Img variant="top" src={'data:image/jpeg;base64,' + imageData} />
      <Card.Body>
        <Card.Title>Card Title</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </Card.Text>
      </Card.Body>
    </Card>
  );
}