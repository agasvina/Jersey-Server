import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as actions from '../actions';
import {
  convertFromRaw,
  convertToRaw,
  Editor,
  EditorState,
  Entity
} from 'draft-js';

class PostList extends Component {
  componentWillMount() {
    this.props.fetchPosts();
  }

  renderPost(article) {
    console.log(article);
    const blocks = convertFromRaw(article.body);
    var editorState = EditorState.createWithContent(blocks);

    return (
      <div className="card card-block" key={article.id}>
        <h4 className="card-title">{article.title}</h4>
        <Editor
          blockStyleFn={getBlockStyle}
          customStyleMap={styleMap}
          editorState={editorState}
          spellCheck={true}
          readOnly ={true}
        />
      </div>
    );
  }

  render() {
    return (
      <div className="user-list">
        {this.props.posts.map(this.renderPost)}
      </div>
    );
  }
}


const styleMap = {
  CODE: {
    backgroundColor: 'rgba(0, 0, 0, 0.1)',
    fontFamily: '"Inconsolata", "Menlo", "Consolas", monospace',
    fontSize: 16,
    padding: 2,
  },
};


function getBlockStyle(block) {
  switch (block.getType()) {
    case 'blockquote': return 'RichEditor-blockquote';
    default: return null;
  }
}

function mapStateToProps(state) {
  return { posts: state.posts };
}

export default connect(mapStateToProps, actions)(PostList);
