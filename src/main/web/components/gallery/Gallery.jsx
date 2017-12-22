import React, { Component } from 'react';
import Image from '../image/Image';
import FileInput from '../file-input/FileInput';
import './gallery.scss';
import axios from 'axios';

class Gallery extends Component {
    constructor(props) {
        super(props);
        this.state = {
            files: [],
            imageURLs: []
        };
    }

    componentDidMount() {
        axios.get('/loadFileList') // Get the ids of files and then load the files one by one
            .then((res) => {
                res.data.forEach(el => {
                    axios.get(`loadImage/${el}`, { responseType: "blob" })
                        .then(result => {
                            this.setState({
                                imageURLs: [...this.state.imageURLs, URL.createObjectURL(result.data)]
                            });
                        })
                        .catch(err => console.log(err));
                })
            })
            .catch(err => console.log(err)) // Need to handle this properly
    }

    onBrowse = (event) => {
        const fileList = event.target.files;
        const files = Object.keys(fileList)
            .map(file => fileList[file])
            .filter(file => file.type.match(/^image\//)); // We only want to save image files

        this.setState({
            files,
            fileNames: files.map(file => file.name)
        })
    };

    onUpload = () => {
        const uploadedImages = this.state.files.map(file => URL.createObjectURL(file));

        this.setState({
            fileNames: [],
            imageURLs: [...this.state.imageURLs, ...uploadedImages]
        });

        let data = new FormData();
        data.append('file', this.state.files[0]);

        axios.post('/saveImage', data)
    };

    renderImages() {
        return this.state.imageURLs.map(url => <Image src={url} />)
    }

    render() {
        return (
            <div>
                <nav className="navbar navbar-expand-md navbar-dark nav-bg fixed-top">
                    <FileInput fileNames={this.state.fileNames} onBrowse={this.onBrowse} />
                    <button type="button" className="btn btn-primary btn-upload" onClick={this.onUpload}>Upload</button>
                </nav>
                <main className='container-fluid gallery-container'>
                    <div className='row'>
                        {this.renderImages()}
                    </div>
                </main>
            </div>
        );
    }
}

export default Gallery;
