import React, { Component } from 'react';
import './file-input.scss'

class FileInput extends Component {
    render() {
        return (
            <label className="custom-file">
                <input type="file" accept="image/*" id="file" className="custom-file-input" onChange={this.props.onBrowse} />
                <span className="custom-file-control">{this.props.fileNames}</span>
            </label>
        )
    }
}

export default FileInput;
