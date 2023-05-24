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
- [ ] Kontrolery
- [ ] Baza danych (co najmniej 2 tabele z relacją)
- [ ] Widoki: formularze z walidacją (3 różne elementy), 5 różnych znaczników Thymeleafa
- [ ] Sesja
- [ ] Ciasteczka
- [ ] Usługa REST (do uwierzytelniania użytkowników)
- [ ] Klient REST
- [ ] Spring Security (z bazą danych)

### Admin
zarządza użytkownikami
- [ ] wyświetlanie listy użytkowników
- [ ] zarządzanie rolami
### Limited user
Jest zarejestrowany, może przegladać udostępnione linki, ale nie może tworzyć informacji.
### Full user
Może tworzyć informacje i udostępniać je innym, przeglądać w oddzielnym widoku informacje udostępnione dla niego.
- [x] dodanie/edycja/usunięcie przez siebie zebranych informacji
- [x] walidacja formularza
- [x] edycja na danych bieżących
- [ ] dodanie nowej kategorii
- [ ] wyświetlenie udostępnionych przez innych informacji
- [ ] udostępnienie: ze wskazaniem na konkretnego użytkownika lub w linku
- [ ] wyświetlanie “swoich” informacji: sortowanie w obu kierunkach (data, kategoria, alfabetycznie)
- [ ] zapamiętanie kierunków i kryteriów sortowania
- [ ] filtrowanie według daty (od aktualnej) i kategorii (od najbardziej popularnej)
- [ ] logowanie
- [ ] zapis do bazy danych dopiero przy wylogowaniu/wygaśnięciu sesji
### Użytkownik niezalogowany
Ma dostęp tylko do strony początkowej i strony rejestracji
- [ ] rejestracja
- [ ] walidacja formularza
- [ ] strona powitalna
- [ ] wyświetlenie informacji z udostępnionego linku
