
# Joke App 😂

An Android application built with **Jetpack Compose** that fetches and displays jokes from various categories using the [JokeAPI](https://sv443.net/jokeapi/v2/). Users can view jokes, filter by category, and report inappropriate ones. This app was developed as part of the Android coursework.

## ✨ Features

- Displays jokes from multiple categories: Programming, Christmas, Pun, Dark, Spooky, Miscellaneous, and Any.
- Two types of jokes:  
  - **Single**: a one-liner joke.  
  - **Twopart**: includes a setup and a delivery.
- **Click to reveal** punchlines for twopart jokes.
- **Toast** messages for single jokes.
- **Report option** for any joke, with a confirmation screen.
- Reported jokes are visually marked.
- **Loading animation** shown before jokes are displayed.
- Uses **Jetpack Compose** for a modern UI.

## 🧠 Tech Stack

- Jetpack Compose
- Kotlin
- Retrofit (for API calls)
- ViewModel + State management
- Material Design Components

## 📸 Screenshots

Main Screen
![Captură de ecran 2025-03-28 113413](https://github.com/user-attachments/assets/f4900519-737f-476a-9621-f151f39bfae8)
Dropdown Menu
![Captură de ecran 2025-03-28 113435](https://github.com/user-attachments/assets/aa20fb2f-ad52-4fd5-9bcf-986278e94c56)
Delivery Dialog
![Captură de ecran 2025-03-28 113518](https://github.com/user-attachments/assets/95f80bfa-d9cd-434f-a0e7-685ada978ff9)
Reported Joke
![Captură de ecran 2025-03-28 113547](https://github.com/user-attachments/assets/5d26557e-f9cc-4526-86c4-fbcab68bb25c)


## 📥 API Reference

[JokeAPI - https://sv443.net/jokeapi/v2/](https://sv443.net/jokeapi/v2/)  
Example endpoint used:  
```
https://sv443.net/jokeapi/v2/joke/Any?amount=10
```

## 🚀 How to Run

1. Clone the repo:  
   `git clone https://github.com/florina2002/joke-app-compose`
2. Open in Android Studio.
3. Run the app on a device or emulator with internet access.
