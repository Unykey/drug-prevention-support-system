// src/constants/config.js
export const API_BASE = 'http://localhost:8080/api'; // or your deployed backend URL
export const API_BASE = 'https://your-backend-url.vercel.app/api';
import { API_BASE } from '../constants/config';

const handleLogin = async () => {
    const response = await fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
    });

    const data = await response.json();
    console.log(data);
};
