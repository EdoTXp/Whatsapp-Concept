# 💬 WhatsApp-Concept

![WhatsApp-Concept Logo](app/src/main/res/app-logo.png)

**A modern WhatsApp clone built with Android, following best practices and advanced architecture.**

---

## 📝 About

This project was originally created as part of a course by Prof. [Jamilton Damasceno](https://www.udemy.com/user/jamiltondamasceno/), [Udemy](https://www.udemy.com/course/desenvolvimento-android-completo/?couponCode=KEEPLEARNINGBR).  
I have extended and refactored the codebase by introducing:

- Hilt for dependency injection
- MVVM architecture
- Result wrapper for state management
- SOLID principles and clean code practices

---

## 📸 Screenshots

Below are the main screens of the app, shown in the order of user flow:

- **Login**  
  ![Login](screenshots/login.png)

- **Sign Up**  
  ![Sign Up](screenshots/sign-up.png)

- **Profile**  
  ![Profile](screenshots/profile.png)

- **Contacts**  
  ![Contacts](screenshots/contacts.png)

- **Chats 1**  
  ![Chats 1](screenshots/chats-1.png)

- **Chats 2**  
  ![Chats 2](screenshots/chats-2.png)

- **Chat Profile 1**  
  ![Chat Profile 1](screenshots/chat-profile-1.png)

- **Chat Profile 2**  
  ![Chat Profile 2](screenshots/chat-profile-2.png)

---

## 🏗️ Architecture

The project uses a layered, scalable architecture:

- **Views**  
  UI components (Activities, Fragments) that observe ViewModels and render the UI.

- **ViewModels**  
  Expose state and business logic to the UI, interact with Services and Repositories, and use
  LiveData for reactive updates.

- **Services**  
  Encapsulate integration with Firebase (Auth, Firestore, Storage) and other platform APIs.

- **Repositories**  
  Abstract data sources and business logic, providing a clean API for ViewModels.

- **Utils**  
  Utility classes and helpers used across the app.

**Dependency Injection:**  
All dependencies are managed with Hilt, ensuring testability and modularity.

**Design Patterns:**  
Repository, ViewModel, LiveData, and Result are used throughout, following SOLID and clean code
principles.

---

## 🙏 Credits

- Original course by Prof. Jamilton Damasceno
- Modifications and enhancements by Edoardo (EdoTXp)

---
