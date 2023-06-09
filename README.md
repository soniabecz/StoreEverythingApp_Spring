# StoreEverythingApp Spring

## Opis
Aplikacja do zapisywania i udostępniania wartych uwagi
informacji, np. strony www, listy zakupów, zaproszenia
na spotkanie.
Główną funkcjonalnością jest umożliwienie zapisania na
swoim koncie ciekawych informacji, udostępniania ich
innym oraz wygodnego przeglądania i przeszukiwania w
dogodnym czasie.
## Elementy techniczne
- [x] Kontrolery
- [x] Baza danych (co najmniej 2 tabele z relacją)
- [x] Widoki: formularze z walidacją (3 różne elementy), 5 różnych znaczników Thymeleafa
- [x] Sesja
- [x] Ciasteczka
- [x] Usługa REST (do wyszukiwania kategorii w słowniku)
- [x] Klient REST
- [x] Spring Security (z bazą danych)

### Admin
zarządza użytkownikami
- [x] wyświetlanie listy użytkowników
- [x] zarządzanie rolami
### Limited user
Jest zarejestrowany, może przegladać udostępnione linki, ale nie może tworzyć informacji.
### Full user
Może tworzyć informacje i udostępniać je innym, przeglądać w oddzielnym widoku informacje udostępnione dla niego.
- [x] dodanie/edycja/usunięcie przez siebie zebranych informacji
- [x] walidacja formularza
- [x] edycja na danych bieżących
- [x] dodanie nowej kategorii
- [x] wyświetlenie udostępnionych przez innych informacji
- [x] udostępnienie: ze wskazaniem na konkretnego użytkownika lub w linku
- [x] wyświetlanie “swoich” informacji: sortowanie w obu kierunkach (data, kategoria, alfabetycznie)
- [x] zapamiętanie kierunków i kryteriów sortowania
- [x] filtrowanie według daty (od aktualnej) i kategorii (od najbardziej popularnej)
- [x] logowanie
- [x] zapis do bazy danych dopiero przy wylogowaniu/wygaśnięciu sesji
### Użytkownik niezalogowany
Ma dostęp tylko do strony początkowej i strony rejestracji
- [x] rejestracja
- [x] walidacja formularza
- [x] strona powitalna
- [ ] wyświetlenie informacji z udostępnionego linku
