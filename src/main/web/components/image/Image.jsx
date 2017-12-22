import React, { Component } from 'react';
import './image.scss'

class Image extends Component {
    render() {
        return(
            <div className='col-sm-6 col-md-3 col-xl-2'>
                <div className='panel'>
                    <img className='image' src={this.props.src} />
                </div>
            </div>
        );
    }
}

export default Image;