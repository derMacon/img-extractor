import Container from 'react-bootstrap/Container';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import FileMenu from './FileMenu';
import { ConnectionState } from './ConnectionState';
import { SERVER_BASE } from '../logic/ApiFetcher';

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
            <Nav.Link href="#link">
              <ConnectionState isConnected={ isConnected } />
            </Nav.Link>
            <NavDropdown title="Help" id="basic-nav-dropdown">
              <Nav.Link href={settingsUrl} onClick={displayEditingInstructions}>Settings</Nav.Link>
              <Nav.Link href='https://github.com/derMacon/img-extractor'>Github</Nav.Link>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}