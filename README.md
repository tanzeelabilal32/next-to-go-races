#  Racing App

A modern **Jetpack Compose** app that fetches and displays a time-ordered list of upcoming races using **MVVM, Clean Architecture, Koin, Coroutines, and Flows**.

## 📌 Features
✅ Fetch and display a **time-ordered list** of upcoming races.  
✅ **Filter races** by category (Horse, Greyhound, Harness Racing).  
✅ **Deselect filters** to show the **next 5 races** across all categories.  
✅ **Auto-refresh** every 5 minutes to update race data.  
✅ Display **race details** (Meeting Name, Race Number, Countdown Timer).  
✅ **Countdown Timer** for each race, removing races **1 minute after they start**.  
✅ **Jetpack Compose UI** with a **responsive design** (Dark Mode + Accessibility).  
✅ **Unit Tests** for ViewModel and UI tests using Jetpack Compose.  

---

## 🏗 Architecture
This app follows the **MVVM (Model-View-ViewModel)** pattern with **Clean Architecture**:

```
com.neds.raceapp
│── data              # Data layer (API, Repository, Models)
│── domain            # Use Cases (Business Logic)
│── presentation      # UI Layer (ViewModel, UI Composables)
│── di               # Dependency Injection (Koin)
```

### 📌 **Flow of Data**
1. **UI (Jetpack Compose)** → Calls **ViewModel**
2. **ViewModel** → Calls **UseCase**
3. **UseCase** → Calls **Repository**
4. **Repository** → Fetches data from API

---

## 🛠 Tech Stack & Dependencies
- **Kotlin** - Modern programming language
- **Jetpack Compose** - UI Toolkit
- **Coroutines + Flow** - Asynchronous programming
- **Retrofit + OkHttp** - API Networking
- **Koin** - Dependency Injection
- **Gson** - JSON Parsing
- **Coil** - Image Loading
- **JUnit + Mockk + Compose Test Rule** - Unit Testing

---

## ⚡ API Integration
The app fetches race data from the following **REST API**:
```bash
GET https://api.neds.com.au/rest/v1/racing/?method=nextraces&count=10
```
Race categories are defined as:
- **Horse Racing:** `4a2788f8-e825-4d36-9894-efd4baf1cfae`
- **Harness Racing:** `161d9be2-e909-4326-8c2c-35ed71fb460b`
- **Greyhound Racing:** `9daef0d7-bf3c-4f50-921d-8e818c60fe61`

---

## 🏃 Setup & Installation
1️⃣ **Clone the Repository:**
```bash
git clone https://github.com/your-username/racing-app.git
```

2️⃣ **Open in Android Studio**
- Open **Android Studio** (latest stable version)
- Select **File > Open** and choose the project directory

3️⃣ **Sync Dependencies:**
- Click **"Sync Now"** to fetch dependencies

4️⃣ **Run the App:**
- Click **Run ▶️** or use `Shift + F10`

---

## 🧪 Testing
### 📌 **Unit Testing (ViewModel)**
Run ViewModel unit tests using:
```bash
./gradlew test
```
### 📌 **UI Testing (Jetpack Compose)**
Run UI tests using:
```bash
./gradlew connectedAndroidTest
```
### **✅ Test Coverage**
✔ `fetchRaces()` - Ensures API data is fetched and updated correctly.  
✔ `removeExpiredRace()` - Removes races **1 minute after they start**.  
✔ `toggleCategory()` - Verifies filtering logic works correctly.  
✔ `clearFilters()` - Ensures all filters reset properly.  
✔ UI tests for **filter toggle, list updates, and countdown timer**.  

---

### **🏃 Next Steps
Following steps can be taken 
- Use Shared Preferences to store the response and run operations on this for better performance
- Pagination can be introduced to see more races
- UI can be enhanced as per neds design



