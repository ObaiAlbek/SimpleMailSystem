# 📧 Simple Mail System

Dies ist ein **Java-basiertes Projekt**, das ein einfaches **Mail-Verwaltungssystem** simuliert.  
Es wurde im Rahmen meines Studiums entwickelt, um **Objektorientierte Programmierung (OOP)** und die **Trennung von Fachlogik und Benutzeroberfläche (Domain vs. GUI)** zu üben.

---

## ✨ Funktionen
- Benutzer erstellen und verwalten (mit eindeutiger ID)  
- Nachrichten zwischen Benutzern senden und empfangen  
- Posteingang anzeigen und gespeicherte Nachrichten verwalten  
- Fehlermeldungen für unbekannte Empfänger  
- **Grafische Benutzeroberfläche (GUI)** zur einfachen Interaktion mit dem System  

---

## 🛠️ Technologien
- Java (OOP, Klassen, Methoden, Objekte)  
- Java Swing/JavaFX (GUI)  

---

## 🚀 Ausführen
1. Repository klonen oder Dateien herunterladen  
2. Java-Dateien kompilieren:  
   ```bash
   javac *.java

## 📐 Architektur & Klassendiagramm

```mermaid
classDiagram
    %% --- GUI PACKAGE ---
    class Main {
        +main()
    }

    class LoginWindow {
        +show()
        +loginButton()
    }
    class RegisterWindow {
        +registerUser()
    }
    class EasyMailWindow {
        +showInbox()
        +openCompose()
    }
    class ComposeEmailWindow {
        +sendEmail()
    }

    %% --- DOMAIN / LOGIC ---
    class UserManager {
        +HashMap users
        +register(User)
        +login(String, String)
        +getCurrentUser() User
    }

    class User {
        +String username
        +String password
        +getEmails()
    }

    class Email {
        +String sender
        +String recipient
        +String subject
        +String content
        +LocalDateTime date
    }

    %% --- FOLDER STRUCTURE ---
    class EmailFolder {
        <<Abstract>>
        +ArrayList~Email~ emails
        +addEmail(Email)
        +removeEmail(Email)
    }

    class Inbox
    class SentFolder
    class TrashFolder

    %% --- BEZIEHUNGEN (RELATIONSHIPS) ---
    
    %% Startpunkt
    Main ..> LoginWindow : startet

    %% GUI nutzt Logic
    LoginWindow ..> UserManager : nutzt
    RegisterWindow ..> UserManager : nutzt
    EasyMailWindow ..> User : zeigt Daten von
    EasyMailWindow ..> ComposeEmailWindow : öffnet

    %% User Struktur
    UserManager "1" o-- "*" User : verwaltet
    User "1" *-- "1" Inbox : hat
    User "1" *-- "1" SentFolder : hat
    User "1" *-- "1" TrashFolder : hat

    %% Ordner Vererbung (Inheritance)
    EmailFolder <|-- Inbox
    EmailFolder <|-- SentFolder
    EmailFolder <|-- TrashFolder

    %% Ordner Inhalt
    EmailFolder o-- "*" Email : enthält
```
