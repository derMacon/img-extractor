import React, { Component } from 'react';
import { fetchFileHistory } from '../logic/ApiFetcher';

class DummyComponent extends Component {
    constructor(props) {
        super(props)

        const data = fetchFileHistory()
        console.log('--------- in dummy: ', typeof props.imageData.curr_page_img)

    }

    /**
     * src: https://stackoverflow.com/questions/16245767/creating-a-blob-from-a-base64-string-in-javascript
     * @param {*} b64Data 
     * @param {*} contentType 
     * @param {*} sliceSize 
     * @returns 
     */
    b64toBlob(b64Data, contentType='image/png', sliceSize=512) {
        const byteCharacters = atob(b64Data);
        const byteArrays = [];

        for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            const slice = byteCharacters.slice(offset, offset + sliceSize);

            const byteNumbers = new Array(slice.length);
            for (let i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
            }

            const byteArray = new Uint8Array(byteNumbers);
            byteArrays.push(byteArray);
        }

        const blob = new Blob(byteArrays, {type: contentType});
        return blob;
    }


    render() {
        return (<button onClick={() => {
                try {
                    const binaryData = this.props.imageData.curr_page_img
                    const byteArray = new Uint8Array(binaryData.length);

                    for (let i = 0; i < binaryData.length; i++) {
                        byteArray[i] = binaryData.charCodeAt(i);
                    }

                    // Create a Blob from the binary data with 'image/png' MIME type
                    const blob = new Blob([byteArray], { type: 'image/png' });

                    navigator.clipboard.write([
                            new ClipboardItem({
                                'image/png': this.b64toBlob(this.props.imageData.curr_page_img),
                            })
                    ])
                } catch(error) {
                    console.error(error)
                }
        }
        }>dummy test</button>)
        // return (<button onClick={() => {navigator.clipboard.writeText('thisasdfadf')}}>dummy test</button>)
    }
}

export default DummyComponent
