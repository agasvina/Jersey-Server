import axios from 'axios';
import { browserHistory } from 'react-router';
import {
  AUTH_USER,
  UNAUTH_USER,
  AUTH_ERROR,
  CREATE_POST,
  FETCH_POSTS,
} from './types';

const ROOT_URL = 'http://localhost:8080';

export function signinUser({ username, password }) {
  return function(dispatch) {
    // Submit email/password to the server
    axios.post(`${ROOT_URL}/user/signin`, { username, password })
      .then(response => {
        dispatch({ type: AUTH_USER });
        localStorage.setItem('token', response.data.token);
        browserHistory.push('/feature');
      })
      .catch((response) => {
        dispatch(authError(response.data.reason));
      });
  }
}

export function signupUser({ username, email, password }) {
  return function(dispatch) {
    axios.post(`${ROOT_URL}/user/signup`, { username, email, password })
      .then(response => {
        dispatch({ type: AUTH_USER });
        localStorage.setItem('token', response.data.token);
        browserHistory.push('/feature');
      })
      .catch(response => dispatch(authError(response.data.error)));
  }
}

export function authError(error) {
  return {
    type: AUTH_ERROR,
    payload: error
  };
}

export function signoutUser() {
  localStorage.removeItem('token');

  return { type: UNAUTH_USER };
}

export function createPost(post) {
  return function(dispatch) {
    axios.post(`${ROOT_URL}/article/create`, post, {
      headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
    }).then(response => {
      dispatch({
        type: CREATE_POST,
        payload: response
      });
      browserHistory.push('/');
    });
  }
}

export function fetchPosts(post) {
  return function(dispatch) {
    axios.get(`${ROOT_URL}/article`).then(response => {
      dispatch({
        type: FETCH_POSTS,
        payload: response
      });
    });
  }
}

