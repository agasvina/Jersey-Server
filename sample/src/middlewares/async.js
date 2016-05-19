export default function({ dispatch }) {
  return next => action => {
    // If action payload is a promise
    if (!action.payload || !action.payload.then) {
      return next(action);
    }

    // Make sure the action's promise resolves
    action.payload
      .then(function(response) {
        // create a new action with the old type, but
        // replace the promise with the reponse data
        const newAction = { ...action, payload: response };
        dispatch(newAction);
      });
  }
}
