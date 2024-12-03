import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import 'primereact/resources/themes/md-dark-indigo/theme.css';
import App from './components/App.tsx';
import { CaptchaGuard } from './components/CaptchaGuard.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <CaptchaGuard>
      <App />
    </CaptchaGuard>
  </StrictMode>
);
