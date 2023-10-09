import React from 'react';
import Badge from 'react-bootstrap/Badge';

export function ConnectionState({ isConnected }) {
  return <div>
      {isConnected ? (
        <Badge bg="success">Backend Running</Badge>
      ) : (
        <Badge bg="danger">Backend Offline</Badge>
      )}
    </div>
}