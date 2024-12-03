import axios, { AxiosResponse } from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
});

export const getLanguages = async () => {
  try {
    const response = await axiosInstance.get<
      void,
      AxiosResponse<{ code: string; name: string }[]>
    >('/languages');
    return response;
  } catch {
    return undefined;
  }
};

export const getTranslatedText = async (search: string, role: string) => {
  try {
    const response = await axiosInstance.get<
      void,
      AxiosResponse<{ translatedText: string }>
    >(`/translate?text=${search}&targetLanguageCode=${role}`);
    return response;
  } catch {
    return undefined;
  }
};

export const getTranslatedTexts = async () => {
  try {
    const response = await axiosInstance.get<
      void,
      AxiosResponse<
        {
          sourceText: string;
          translatedText: string;
          sourceLanguageCode: string;
          targetLanguageCode: string;
        }[]
      >
    >('/requests');
    return response;
  } catch {
    return undefined;
  }
};
