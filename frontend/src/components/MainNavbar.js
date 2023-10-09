import Container from 'react-bootstrap/Container';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import FileMenu from './FileMenu';
import { ConnectionState } from './ConnectionState';
import { SERVER_BASE, teardown } from '../logic/ApiFetcher';

export function MainNavbar({ isConnected, title }) {

  const settingsUrl = SERVER_BASE + '/settings'
  const displayEditingInstructions = () => alert('To update the settings edit ./res/runtime/config.ini and restart the application.')

  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">{ title }</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <FileMenu />
            <NavDropdown title="Help" id="basic-nav-dropdown">
              <Nav.Link href={settingsUrl} onClick={displayEditingInstructions}>Settings</Nav.Link>
              <Nav.Link href='https://gitlab.com/s.hoffmann/projects/programming/msc/img-extractor.git'>Gitlab</Nav.Link>
            </NavDropdown>
            <Nav.Link href='' onClick={teardown}>
              clear-data
            </Nav.Link>
            <Nav.Link href="">
              <ConnectionState isConnected={ isConnected } />
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}