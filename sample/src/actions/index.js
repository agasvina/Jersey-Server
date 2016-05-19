import axios from 'axios';
import { FETCH_USERS } from './types';

export function fetchUsers() {
  const request = axios.get('http://localhost:8080/user/retrieve/all');

  return {
    type: FETCH_USERS,
    payload: request
  };
}
