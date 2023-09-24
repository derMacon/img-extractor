import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import FileMenu from './FileMenu';
import { ConnectionState } from './ConnectionState';

export function MainNavbar({ isConnected, title }) {
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">{ title }</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Navbar.Text>
              Home
            </Navbar.Text>
            <FileMenu />
            <Nav.Link href="#link">
              <ConnectionState isConnected={ isConnected } />
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}