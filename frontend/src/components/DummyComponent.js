import React, { Component } from 'react';
import { fetchFileHistory } from '../logic/ApiFetcher';

class DummyComponent extends Component {
    constructor(props) {
        super(props)

        const data = fetchFileHistory()
        console.log('--------- in dummy: ', data)

    }


    render() {
        return (<div>dummy test</div>)
    }
}

export default DummyComponent
