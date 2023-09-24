import React from 'react';
import Card from 'react-bootstrap/Card';
import DocNavigation from './DocNavigation';

export function ImageViewer({ imageData }) {
  console.log("in viewer")
  console.log(imageData)
  console.log(imageData.curr_page_idx)
  return (
    <Card>
      <Card.Header>
        <DocNavigation imageData={imageData} />
      </Card.Header>
      <Card.Body>
        <Card.Img variant="top" src={'data:image/jpeg;base64,' + imageData.curr_page_img} />
      </Card.Body>
    </Card>
  );
}