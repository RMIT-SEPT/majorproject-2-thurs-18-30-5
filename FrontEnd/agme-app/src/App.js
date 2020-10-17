import React from 'react';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import LandingPage from './components/General/js/LandingPage';
import {Provider} from "react-redux";
import store from './store';

function App() {
  return (
    <Provider store={store}>
    <div>
      <LandingPage/>
    </div>
    </Provider>
    );
  }
  
  export default App;