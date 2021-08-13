# League test automation pet project

## О проекте
Проект для тестирования открытой api https://api.thecatapi.com/v1/
Сайт https://docs.thecatapi.com/

## Необходимые инструменты
Gradle 7.1.1
allure 2.14.0

## Запуск
В командной строке запустить
gradle clean test --project-prop api_key=852edcf0-7ad3-4c42-b3b1-feab715e5fd5

## Отчет
В командной строке запустить
allure serve ./build/allure-results/  
