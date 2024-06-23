<h1 align="center">Logging http requests spring boot starter</h1>

<h3 align="center">Spring boot starter для логирования http запросов</h3>

В проекте разработан стартер, позволяющий логировать http запросы и ответы.
Логирование включает в себя: метод запроса, URL запроса, заголовки, код ответа, время обработки запроса. 
Механизм перехвата http запросов реализован с помощью интерцепторов.

Для подключения стартера в свой проект необходимо добавить зависимость:

<groupId>dev.sosnovsky</groupId>
<artifactId>logging-http-requests-spring-boot-starter</artifactId>
<version>0.0.1-SNAPSHOT</version>

Для настройки стартера в properties необходимо указать два параметра:
logging.http.enable и logging.http.level.

Для включения стартера необходимо задать параметру logging.http.enable значение true.
При отсутствии параметра logging.http.enable или при значении false логирование отключается.

Для выбора уровня логирования необходимо задать параметру logging.http.level одно из значений:
ERROR, WARN, INFO, DEBUG, TRACE.
По умолчанию установлен уровень логирования INFO.

Для тестирования приложения можно воспользоваться написанными тестами.