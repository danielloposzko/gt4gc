# "Zielona Tesla za Zielony kod" - [Zadanie konkursowe](https://www.ing.pl/pionteching)

GT4GC to wielowątkowy serwer HTTP, który obsługuje kilka punktów końcowych POST. Serwer jest napisany w Javie i korzysta z biblioteki Jackson do mapowania danych JSON na obiekty.


## Punkty końcowe

Serwer obsługuje następujące punkty końcowe:

- `/atms/calculateOrder`: punkt końcowy do obliczania zamówień w bankomatach. Wykorzystuje `AtmService` do przetwarzania żądań.
- `/onlinegame/calculate`: punkt końcowy do obliczania wyników gier online. Wykorzystuje `OnlineGameService` do przetwarzania żądań.
- `/transactions/report`: punkt końcowy do generowania raportów transakcji. Wykorzystuje `TransactionsService` do przetwarzania żądań.


## Konfiguracja

Serwer nasłuchuje na porcie 8080 i jest skonfigurowany do obsługi do 15 wątków jednocześnie.

Możesz zmienić port oraz liczbę wątków, modyfikując stałe `PORT` i `THREADS` w klasie `Main`.


## Uruchamianie serwera

Aby uruchomić serwer, użyj `build.sh` a następnie `run.sh`.


## Statyczna analiza bezpieczeństwa kodu źródłowego (SAST)

W tym projekcie stosujemy statyczną analizę bezpieczeństwa kodu źródłowego (SAST) w celu zapewnienia bezpieczeństwa kodu. 

Do procesu SAST korzystamy z funkcji Skanowania kodu GitHub, która jest oparta na CodeQL. Pomaga nam to w identyfikacji potencjalnych luk bezpieczeństwa w kodzie źródłowym oraz w pisaniu wysokiej jakości, bezpiecznego kodu.

[Aktualny raport z analizy SAST](https://github.com/danielloposzko/gt4gc/security/code-scanning) 

![Status SAST](https://img.shields.io/badge/SAST-zaliczone-green)


## Autor

Projekt został stworzony przez [Daniel Loposzko](https://github.com/danielloposzko).
