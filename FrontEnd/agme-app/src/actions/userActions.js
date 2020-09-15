import axios from "axios";
import { GET_ERRORS } from "./types";

export const createUser = (user, history) => async dispatch => {
  try {
    const res = await axios.post("http://localhost:8080/api/customer", user);
    history.push("/sign-up");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
