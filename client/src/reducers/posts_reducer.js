import {
  FETCH_POSTS,
  CREATE_POST
} from '../actions/types';

export default function(state = [], action) {
  switch (action.type) {
    case FETCH_POSTS:
      return [ ...state, ...action.payload.data ];
    case CREATE_POST:
      return state.splice(0, 0, action.payload.data);
  }

  return state;
}