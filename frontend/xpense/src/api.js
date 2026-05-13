import axios from 'axios';

const API = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true
});

API.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const signup = (data) => API.post('/auth/signup', data);
export const signin = (data) => API.post('/auth/signin', data);
export const addPayment = (data) => API.post('/payment/add', data);
export const getTotal = () => API.get('/payment/total');
export const updateWebhook = (data) => API.put('/auth/update-webhook', data);