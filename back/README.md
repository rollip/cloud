Для запуска нажать ран в
классе [ServComeBackApplication.java](src/main/java/ru/matveyakulov/servcomeback/ServComeBackApplication.java)
В проекте юзаются Object Storage(S3) и Yandex Translate + капча на фронте https://yandex.cloud/ru/docs/smartcaptcha/?from=int-console-empty-state 
Дока яндекса на 3 метода https://yandex.cloud/ru/docs/translate/api-ref/Translation/translate
Урлы:
1. localhost:8080/languages - Получить все языки возможные
   Логика под капотом:
   Вызывается метод https://translate.api.cloud.yandex.net/translate/v2/languages
   c хедером Authorization: Api-Key <api-key>
   с телом
   {
   "folderId": "folderId"
   }
2. localhost:8080/requests - получить все ранее запрошенные реквесты
   Логика под капотом: идем в с3 за файлом с помощью AmazonS3Client.getObject() и вычитываем построчно
3. localhost:8080/translate?text=tranate&targetLanguageCode=ru - получить перевод текста {text} с языка
   {targetLanguageCode}
   Логика под капотом:
   1.1 Вызывается метод https://translate.api.cloud.yandex.net/translate/v2/detect
   c хедером Authorization: Api-Key <api-key>
   с телом
   {
   "text": "текст для перевода",
   "folderId": "folderId"
   }
   В ответ получаем
   {
   "languageCode": "en" - код языка, который дальше юзаем в sourceLanguageCode
   }
   1.2 Вызывается метод https://translate.api.cloud.yandex.net/translate/v2/translate
   c хедером Authorization: Api-Key <api-key>
   с телом
   {
   "sourceLanguageCode": "en", - язык, с которого переводим
   "targetLanguageCode": "ru", - язык, на который переводим
   "format": "PLAIN_TEXT", - по умолчанию без разметки
   "texts": ["speech"], - текст для перевода, у нас всегда 1 слово
   "folderId": "b1g704fj4mlmacedi8n8"
   }