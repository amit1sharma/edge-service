# edge-service
edge service with spring boot using  spring cloud/netflix technologies

This is a spring boot application which is used as a reverse proxy service and is communicating with API service with the help of Fiegn http client.
It is resilient in nature with the help of Hytrix circuit breaker. ZUUL used in this application makes it an API gateway with all basic features of API gateway. 
It also acts as service discovery client.
