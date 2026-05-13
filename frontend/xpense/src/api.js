import axios from 'axios';

const API = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    withCredentials: true
});

export const signup = (data) => API.post('/auth/signup', data);
export const signin = (data) => API.post('/auth/signin', data);
export const addPayment = (data) => API.post('/payment/add', data);
export const getTotal = () => API.get('/payment/total');
export const updateWebhook = (data) => API.put('/auth/update-webhook', data);
export const getMe = () => API.get('/auth/me');