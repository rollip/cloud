import { SmartCaptcha } from '@yandex/smart-captcha';
import { useState } from 'react';
import { Dialog } from 'primereact/dialog';
interface CaptchaGuardProps {
  children?: React.ReactNode;
}
export const CaptchaGuard = ({ children }: CaptchaGuardProps) => {
  const [acces, setAcces] = useState(false);
  const [visible, setVisible] = useState(true);

  if (!acces)
    return (
      <Dialog
        visible={visible}
        header="Captcha"
        onHide={() => {
          setVisible(!acces);
        }}
      >
        <SmartCaptcha
          sitekey={import.meta.env.VITE_CLIENT_KEY}
          onSuccess={() => {
            setAcces(true);
            setVisible(false);
          }}
        />
      </Dialog>
    );

  return children;
};

