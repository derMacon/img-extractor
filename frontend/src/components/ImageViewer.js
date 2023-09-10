import React from 'react';

export function ImageViewer({ imageData }) {
  return <img src={'data:image/jpeg;base64,' + imageData}></img>;
}