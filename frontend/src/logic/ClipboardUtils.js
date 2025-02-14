 /**
 * src: https://stackoverflow.com/questions/16245767/creating-a-blob-from-a-base64-string-in-javascript
 * @param {*} b64Data 
 * @param {*} contentType 
 * @param {*} sliceSize 
 * @returns 
 */
function b64toBlob (b64Data, contentType='image/png', sliceSize=512) {
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

export function setSystemClipboard (image) {
    if (!image) {
        return;
    }
    try {
    navigator.clipboard.write([
            new ClipboardItem({
                'image/png': b64toBlob(image),
            })
    ])
    } catch(error) {
        console.log(error)
    }
}