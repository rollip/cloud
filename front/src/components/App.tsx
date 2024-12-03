import { useCallback, useEffect, useState } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { InputTextarea } from 'primereact/inputtextarea';
import { FloatLabel } from 'primereact/floatlabel';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import cls from './App.module.css';
import {
  getLanguages,
  getTranslatedText,
  getTranslatedTexts,
} from '../api/gpt.api';

function App() {
  const [languages, setLanguages] =
    useState<{ code: string; name: string }[]>();
  const [selectedLanguage, setSelectedLanguage] = useState<{
    code: string;
    name: string;
  }>();
  const [text, setText] = useState<string>();
  const [translatedText, setTranslatedText] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [oldTranslatedText, setOldTranslatedText] = useState<
    {
      sourceText: string;
      translatedText: string;
      sourceLanguageCode: string;
      targetLanguageCode: string;
    }[]
  >();

  useEffect(() => {
    getLanguages().then((res) => {
      if (res?.data) {
        setLanguages(res.data);
      }
    });
    getTranslatedTexts().then((res) => {
      if (res?.data) {
        setOldTranslatedText(res.data);
      }
    });
  }, []);

  const translateText = useCallback(async () => {
    setLoading(true);
    if (text && selectedLanguage) {
      const res = await getTranslatedText(text, selectedLanguage.code);
      if (res?.data) {
        setTranslatedText(res.data.translatedText);
        getTranslatedTexts().then((res) => {
          if (res?.data) {
            setOldTranslatedText(res.data);
          }
        });
      }
      setLoading(false);
    } else {
      setLoading(false);
    }
  }, [text, selectedLanguage]);

  if (!languages) return null;

  return (
    <div className={cls.app}>
      <h3 className={cls.historyTitle}>Переводчик</h3>
      <div className={cls.translatePanel}>
        <div className={cls.translateForm}>
          <div className={cls.inputs}>
            <FloatLabel >
              <Dropdown
                id="language"
                value={selectedLanguage}
                options={languages}
                optionLabel="name"
                onChange={(e) => setSelectedLanguage(e.value)}
                className={cls.dropdowm}
              />
              <label htmlFor="language">Перевести на</label>
            </FloatLabel>
            <FloatLabel className={cls.textareaWrapper}>
              <InputTextarea
                id="search"
                value={text}
                onChange={(e) => setText(e.target.value)}
                className={cls.textarea}
              />
              <label htmlFor="search">Ваш запрос</label>
            </FloatLabel>
          </div>
          <Button className={cls.mybutton}
            label="Отправить"
            onClick={translateText}
            loading={loading}
          />
        </div>
        <Card className={cls.result}>{translatedText}</Card>
      </div>
      <h3 className={cls.historyTitle}>История</h3>
      <div className={cls.history}>
        {oldTranslatedText &&
          [...oldTranslatedText].reverse().map((item, index) => (
            <Card key={index} className={cls.mycard}>
              <div>Исходный текст: {item.sourceText}</div>
              <div>Перевод: {item.translatedText}</div>
              <div>Язык исходного текста: {item.sourceLanguageCode}</div>
              <div>Язык перевода: {item.targetLanguageCode}</div>
            </Card>
          ))}
      </div>
    </div>
  );
}

export default App;
