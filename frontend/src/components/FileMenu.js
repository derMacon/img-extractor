import React from 'react';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Card from 'react-bootstrap/Card';
import { loadExistingNav } from '../logic/ApiFetcher';


class FileMenu extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            fileHistory: this.fetchFileHistory()
        };
    }

    fetchFileHistory() {
        return ['test-pdf-1.pdf', 'test-pdf-2.pdf']
    }

    appendHistory(newFileName) {
        // todo
        console.log("appending file history with: " + newFileName)
        // this.setState({
        //     fileHistory: fileHistory.concat([
        //         {

        //         }
        //     ])
        // })
    }

    selectExistingFile(fileName) {
        console.log('clicking filename: ' + fileName)
        loadExistingNav(fileName)
    }

    render() {
        const files = this.state['fileHistory'].map((fileName) =>
            <NavDropdown.Item onClick={() => this.selectExistingFile(fileName)}>
                {fileName}
            </NavDropdown.Item>
        )

        console.log('files: ' + files)

        return (
            <NavDropdown title="File" id="basic-nav-dropdown">
                {files}
              <NavDropdown.Divider />
              <NavDropdown.Item onClick={() => (console.log('on click test'))}>
                Open New File
              </NavDropdown.Item>
            </NavDropdown>
        )
    }

}

export default FileMenu