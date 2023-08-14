## SEEDS Learning Application
**Project website**: [https://seeds.wineme.wiwi.uni-siegen.de/](https://seeds.wineme.wiwi.uni-siegen.de/)

![Seeds_Logo.png](https://user-images.githubusercontent.com/60668130/193766931-1fdf70f7-1e64-40c7-859a-af73d7c7bd8d.png)

<div align="justify"> The SEEDS Android app is a cutting-edge educational platform crafted with Kotlin programming language. It's meticulously designed to revolutionize distance learning, offering students an intuitive and engaging learning experience. By seamlessly blending technology and education, SEEDS redefines the boundaries of remote learning, fostering a holistic approach that empowers students across diverse geographies.</div>
<br>
<div align="justify">
 
Formed through a dynamic collaboration between three distinguished institutions -  [Universität Siegen](https://www.uni-siegen.de/start/index.html.en?lang=en), [University of Alicante](https://www.ua.es/en/) in Spain, and [Openlabs](https://olathens.gr/) in Athens, SEEDS represents a powerful consortium. This collective effort was nurtured by the support of the Erasmus program of the European Union.

</div>
<br>

[![Coded in Kotlin](https://img.shields.io/badge/Coded%20in-Kotlin-blueviolet)](https://kotlinlang.org/) [![Open Source](https://img.shields.io/badge/Open%20Source-Yes-green.svg)](https://opensource.org/) [![License](https://img.shields.io/badge/License-Educational%20Community%20License%202.0-blue.svg)](https://opensource.org/licenses/ECL-2.0)

## Table of contents
1. [Project Objectives](#project-objectives)
2. [Getting Started](#getting-started)
3. [Features](#features)
4. [App Architecture](#app-architecture)
5. [Libraries and Framework](#libraries-and-framework) 
6. [Comprehensive Data Storage Approach in the SEEDS Android App](#comprehensive-data-storage-approach-in-the-seeds-android-app)
7. [Resources](#resources)
8. [App Configuration](#app-configuration)
9. [Screenshots](#screenshots) 
10. [License](#license)

## Project Objectives
 <div align="justify">The primary objective of the SEEDS project is to transcend the challenges presented by post-pandemic education. Beyond this, the project aims to amplify the learning experience through meticulous design strategies. By integrating an interactive user interface (UI) and an extensive range of features, the application is meticulously crafted to foster an engaging and dynamic learning process. Rooted in a user-centric approach, the app actively encourages participation and exploration, effectively transforming education into an enriching journey. Furthermore, the project caters to the diverse linguistic backgrounds of learners by offering support in four prominent languages: English, German, Spanish, and Greek. This multilingual capability ensures that students from various countries can comfortably engage with the app in their native language, promoting a sense of familiarity and ease while learning. Whether students are immersed in multimedia-rich lessons, participating in interactive quizzes, or interacting with the AI-driven Rasa chatbot, the project prioritizes the creation of meaningful engagements that enhance comprehension.
<br>
<br>
  
Through the seamless fusion of educational principles and technological advancements, the SEEDS project aspires to cultivate an environment where learners can excel, thereby shaping the trajectory of accessible and impactful global education.

## Getting Started
Welcome to the SEEDS Android application repository! Whether you're a student eager to enhance your learning experience or a developer interested in contributing, this guide will help you get started. The SEEDS app is designed to revolutionize distance learning, making it more intuitive and interactive. Here's how you can begin your journey with us:

- **Download the App**: To access the SEEDS Android application, you can download the project package from the provided link. This ensures you're getting the official and up-to-date version of the app.

- **Choose Your Language**: Once you've downloaded the app, open it and select your preferred language from the available options: English, German, Spanish, or Greek. This selection ensures you engage with the content in a language that suits you best.

- **Explore the Interface**: With your language preference set, navigate through the user-friendly interface. Familiarize yourself with the layout and sections that offer a variety of interactive features to enhance your learning.

- **Access Learning Materials**: Depending on your internet connection, you can access the app's learning materials in different modes. When online, you can engage with the Rasa chatbot, delve into multimedia-enriched lessons, and explore interactive quizzes. In offline mode, you can still access previously downloaded learning materials and utilize the offline chatbot for a seamless experience.

- **Unlock Private Content**: If your teachers have shared private content, you can access it by using the Access Code they provide. This ensures you receive personalized educational content that aligns with your learning goals.

## Features
Discover the diverse and innovative features that define the SEEDS Android application:

- **Tailored Onboarding Experience for New and Returning Users**: Upon launching the SEEDS app, users are met with a personalized experience that adapts to their status. New users are prompted with an enticing choice: "New User" Opting for this, they're treated to a captivating introduction enhanced by Json animations and an informative view that outlines the app's features. This immersive preview is followed by effortless navigation to the registration screen to establish their account. In contrast, returning users can select the option "Already a User" which swiftly guided them to the login screen, acknowledging their familiarity with the app. This dual approach optimizes user engagement, fostering an inclusive and efficient learning journey.

- **Enhancing Accessibility with Multilingual Support in the SEEDS App**: SEEDS app empowers users with language flexibility. The language change feature lets users switch between English, German, Spanish, and Greek, ensuring a personalized learning experience in their preferred language. This adaptable feature exemplifies SEEDS' commitment to inclusivity and accessibility for learners worldwide.

- **Tab Layout for Organized Navigation**: The app's intuitive tab layout streamlines your experience, dividing content into three distinct screens. The first tab hosts the chatbot interface, providing seamless interactions and delivering subject-specific activities. The second tab showcases a history of completed activities, offering insights into your learning progress. The final tab features your student dashboard, a hub of statistics and insights into your learning journey.

- **Interactive Chatbot**: Within the chatbot interface, you can engage in dynamic conversations. The chatbot recommends activities tailored to the subject and topic you've selected, ensuring a personalized learning experience that adapts to your academic needs.

- **Diverse Activity Types**: The app offers a range of engaging activity types to cater to various learning styles. Choose from multiple options, including multiple-choice questions (MCQs), true or false questions, matching activities, and open-ended questions. This diversity ensures a comprehensive and well-rounded learning process.

- **Activity Progress Tracking and Scoring in SEEDS App's History Tab**: Embedded within the app's tab layout, the history tab meticulously displays completed activities in sequential order, detailing scores achieved for each. This feature allows users to track their progress and grasp proficiency across varied activity formats, reflecting the SEEDS project's commitment to fostering an insightful and impactful learning experience.

- **Student Dashboard and Activity Statistics**: The SEEDS app offers a comprehensive student dashboard that provides a personalized overview of your learning journey. Within the dashboard, you can access detailed statistics regarding completed activities, track your progress, and monitor your overall performance, empowering you to take ownership of your learning path.

- **Flashcards for Enhanced Learning**: Upon completing activities, the app presents a dynamic learning opportunity through flashcards. These flashcards reinforce your understanding of the material and provide a quick and interactive review of key concepts, ensuring a deeper grasp of the content.

- **Material Download for Offline Access**: The app facilitates offline learning by allowing you to download materials for later access. Whether you're in an area with limited connectivity or wish to study on the go, downloading materials ensures continuous learning without the need for a constant internet connection.

- **Customizable Notifications**: Tailoring the learning experience to your schedule, the app's notification settings enable you to receive timely updates. Set notifications based on specific times and days that best suit your learning preferences, ensuring you never miss out on important content.

- **Personalized Theme Selection**: Recognizing the significance of user comfort, the app introduces theme customization. Choose between light and dark modes to create a personalized visual experience that aligns with your preference and reduces eye strain during extended study sessions.

## App Architecture

The SEEDS Android app is meticulously designed using the Model-View-ViewModel (MVVM) architecture to ensure an organized, scalable, and maintainable codebase. This architecture promotes the separation of concerns, making the development, testing, and maintenance of the app more efficient.

### Key Architectural Components

- **Model-View-ViewModel (MVVM):** The app follows the MVVM architecture, separating the user interface (View), business logic (ViewModel), and data (Model). This enhances code organization and reusability.

- **ViewModel:** The ViewModel manages data presentation and business logic, keeping the UI independent of data operations. It communicates with repositories for data retrieval and manipulation.

- **Repository Pattern:** Repositories abstract data sources and provide a consistent API to ViewModel. They manage data from various sources, like networks and local databases, ensuring data integrity.

- **Room Database:** Local data storage is handled using the Room Persistence Library, which simplifies database management and interaction, maintaining data consistency.

- **Retrofit for Networking:** Retrofit facilitates network requests, enabling seamless communication with remote APIs and efficient data retrieval.

- **Dependency Injection:** Kodein is used for dependency injection, enhancing modularity and reducing tight coupling between components.

- **LiveData and Observables:** LiveData and observables establish data-driven connections between ViewModel and UI, ensuring real-time updates.

- **View Binding:** View Binding simplifies the interaction between UI components and code by generating binding classes for XML layouts.

- **Navigation Component:** The Navigation component streamlines app navigation, providing a structured way to move between screens and enhancing the user experience.

This architecture ensures code readability, maintainability, and extensibility, allowing for efficient feature development and easy integration of new functionalities as the app evolves.


## Libraries and Framework
Discover the powerful technologies that underpin the seamless functionality of the SEEDS Android application:

- **[Rasa](https://rasa.com/)**: Rasa libraries power a chatbot for natural language interactions, offering dynamic conversations and personalized learning recommendations.

- **[AndroidX Core KTX](https://developer.android.com/kotlin/ktx)**: Provides core functionality enhancements and utilities.

- **[Android Jetpack Libraries](https://developer.android.com/jetpack)**: Utilizing Navigation, LiveData, and ViewModel components, Jetpack enhances app navigation, data management, and UI design for efficiency.

- **[Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room)**: Room simplifies local data storage, optimizing the management of user progress and completed activities.

- **[Retrofit](https://square.github.io/retrofit/)**: This library streamlines network requests and API communication, ensuring effective interaction with backend services for accessing learning materials.

- **[Glide](https://bumptech.github.io/glide/)**: Glide is an image loading and caching library that efficiently handles the loading, caching, and displaying of images, reducing the complexity of managing images in your app while optimizing performance and memory usage.

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Jetpack Compose is a modern UI toolkit for building native Android user interfaces using a declarative and functional approach. It simplifies UI development by enabling you to describe the UI in a more intuitive and concise manner, enhancing productivity and flexibility in creating interactive and dynamic UI components.

- **[ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2)**: ViewPager2 simplifies the creation of swipeable layouts, enabling you to display fragments or views that users can navigate by swiping left or right.

- **[Lottie](https://airbnb.io/lottie/)**: By integrating Json animations, Lottie adds captivating visuals to the app's introduction and elevates user engagement.

- **[AnyChart](https://github.com/AnyChart/AnyChart-Android)**: AnyChart is a JavaScript-based charting library that provides a wide range of interactive and customizable charts, graphs, and visualizations for web and mobile applications.

- **[Lingver](https://github.com/YarikSOffice/Lingver)**: Lingver is a library facilitating language localization in Android apps, enabling seamless language switching and enhancing user accessibility by allowing users to interact with app content in their preferred language.

- **[KodeinAware (Kodein Dependency Injection)](https://github.com/Kodein-Framework/Kodein-DI)**: KodeinAware is a component of Kodein, a dependency injection framework. It enables Android developers to manage and inject dependencies efficiently, enhancing modularization and maintainability in app architecture.

## Comprehensive Data Storage Approach in the SEEDS Android App
The SEEDS Android app employs a multifaceted data storage strategy to ensure efficient user experiences and seamless access to learning materials. These storage options work in harmony to enhance user engagement and provide a personalized learning journey:

- **Room Persistence Library**: SEEDS utilizes the Room Persistence Library as a robust solution for managing local data storage. This library facilitates the storage of user progress, completed activities, and personalized settings within a structured SQLite database. The Room library streamlines CRUD operations, ensuring efficient interaction with stored data and seamless continuity in the user's learning journey.

- **Shared Preferences**: The app leverages Shared Preferences to store lightweight key-value pairs. This approach efficiently manages user preferences, language selections, notification settings, and other individual choices. Shared Preferences enable quick access to these configurations across app sessions, delivering a tailored experience without unnecessary repetition.

- **Session Manager**: The Session Manager optimizes user authentication and session handling. By maintaining the user's login state, it enhances security and simplifies user access. This feature minimizes the need for repeated logins and ensures secure entry into the app, providing consistent access to personalized content and features.

## Resources

The `res` folder is a crucial directory in the SEEDS Android app project, housing a variety of essential resources that contribute to the app's appearance, functionality, and user experience. It contains a diverse range of assets, including:

- **Fonts:** Typography choices are stored here to maintain consistent text styling throughout the app.

- **Images:** Visual elements, icons, and images utilized in the app's interface are stored in this directory.

- **Strings:** Text strings used within the app's UI, like labels, prompts, and messages, are organized here for easy localization and management.

- **Animations:** Animated elements, including Lottie animations, are stored in this folder to enhance the app's visual dynamism.

- **Layouts (XML files):** These XML files define the structure and arrangement of UI components, ensuring a cohesive and user-friendly interface.

The resources stored in the `res` folder play a pivotal role in shaping the app's visual identity, interactivity, and overall user experience.</div>


## App Configuration

Here's an overview of the app configuration in the `build.gradle` file:

```groovy
android {
    compileSdk 33
    buildFeatures {
        dataBinding true
        viewBinding true
    }

    defaultConfig {
        applicationId "com.seeds.chat_bot"
        minSdk 23
        targetSdk 29
        versionCode 5
        versionName "1.4"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = '17'
    }

    lintOptions {
        checkReleaseBuilds false
    }

    packagingOptions {
        packagingOptions {
            resources.excludes.add("META-INF/*")
        }
    }
    
    namespace 'com.example.chat_bot'
}

```
## Screenshots

### Welcome Screen 
<div align="justify">The Welcome Screen serves as an entry point that distinguishes between new and returning users, providing a tailored introduction for newcomers and a streamlined path to login for those already familiar with the app. This approach ensures a user-centric experience that accommodates both audiences effectively. Additional functionalities include language change options and a clickable text view for accessing SEEDS' information
<br>
<br>
 
<p align="center">
  <img src="https://user-images.githubusercontent.com/113074664/260270114-8ba312a9-3c57-42a0-b70f-0689ec0e5c48.png" width="300"height="550">
</p>

### Introduction Screen
The Introduction Activity exclusively caters to new users, presenting a captivating four-screen sequence. The first three screens feature dynamic Lottie animations, providing insights into the app's features. The fourth screen showcases an informative introduction video. A "Let's Go" button guides users to registration, while a "Skip" button on the first three screens accelerates access to the video, offering a tailored and engaging onboarding experience. Additionally, the Register Screen features a text view that seamlessly navigates users to the Login Screen, and users can conveniently change the interface language within the Register Screen.
<table>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/113074664/260270120-e3f545d7-41db-47e1-b938-64bc2163be62.png" width="200"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260270124-0cdc4b38-c7ec-4144-bb8c-62b12a890abf.png" width="200"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260270133-afbb3b94-dbf7-4ba7-a499-d8268b0584e4.png" width="200"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260270138-5ff06d24-bd8f-45f4-b4f7-9cc149bc8de2.png" width="200"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260270180-cf3d1554-737a-4eeb-b773-7efffcb99c85.png" width="200"></td>
  </tr>
</table>

### Login Screen
The Login Screen serves as the gateway for returning users, allowing them to access their accounts with ease. By entering their credentials, users can seamlessly connect to their personalized profiles and continue their educational journey. The user-friendly interface prioritizes simplicity and security, ensuring a smooth and efficient login process. The Login Screen features a text view that seamlessly navigates users to the Register Screen if required.
<br>
<br>

<p align="center">
  <img src="https://user-images.githubusercontent.com/113074664/260271619-cc5f84b8-2201-46a7-9abe-31ffbae63129.png" width="300"height="550">
</p>

### Main Activity
The Main Activity stands as the foundation of the SEEDS app, featuring an intuitively organized tab layout that forms the core of the user experience. Comprising three pivotal screens, this layout ensures a comprehensive learning journey. The first screen houses an interactive chatbot interface, providing dynamic conversations and tailored subject-based activities. Transitioning to the second tab, users delve into the History Tab, a repository of completed activities, offering valuable insights into their learning progress. The third tab reveals the Student Dashboard, a central hub that not only showcases statistics and achievements but also offers access to downloaded materials, flashcards, help resources, settings customization, access code input, profile card view, and a convenient sign-out option. This cohesive design maximizes engagement, fosters deeper comprehension, and elevates the overall user experience.

<div align="center">
<table>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/113074664/260272242-187badb6-cd2f-43ad-af5a-6adfef5d88fc.png" width="300"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260272250-c1fcd180-6cc1-4a98-92a8-84816e1a4a92.png" width="300"></td>
    <td><img src="https://user-images.githubusercontent.com/113074664/260272243-c255fc02-b5a7-4aa9-b368-801c87425f2a.png" width="300"></td>
  </tr>
</table>
</div>

### Activities
The SEEDS app empowers users to actively engage in subject-specific activities that promote learning and comprehension. Through multiple-choice questions (MCQs), true or false questions, matching activities, and open-ended questions, users can explore various topics and test their knowledge. Each activity is thoughtfully designed to align with the chosen subject and topic, fostering a personalized learning experience. This diverse range of activities accommodates different learning styles, enhances critical thinking, and encourages deeper understanding, contributing to a holistic educational journey.

<div align="center">
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272942-853eb84f-4728-4dd7-99bc-437d50bb922c.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272946-430cd390-5687-4679-ab56-4dba2c0aa110.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272956-c98a1fe2-6122-4a33-b2e4-de7302fee65f.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272969-97de5a52-4403-492c-8244-b73503b5bbd8.png" width="400"></td>
    </tr>
  </table>
</div>

### Learning Statistics
The learning progress feature tracks completed activities, quizzes, and lessons, offering insights into users' educational journey. Visual representations and statistics highlight strengths and areas for improvement, empowering learners to customize their approach and stay motivated.
<p align="center">
  <img src="https://user-images.githubusercontent.com/113074664/260273592-d2925206-9ec5-441c-b705-413f3602493d.png" width="300"height="550">
</p>

### Flashcards
Flashcards are intelligently generated based on the user's completed activities, focusing on specific subjects and topics. flashcards offer flexible navigation, allowing users to either manually swipe through them or use the Next button for easy movement. Additionally, users have the option to retrieve previously viewed flashcards using the previous button. The flashcards display a range of activity information, enhancing learning through efficient recall and review.
<div align="center">
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260273599-17831414-1b6f-4a7b-8b73-3b5ad9223a85.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260273602-a0a99be4-f9e1-49ef-984a-8de3103908c7.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260273605-4696249e-b3cf-48da-afae-0ba2706b3214.png" width="400"></td>
    </tr>
  </table>
</div>

### Settings and Help Menus
The Settings and Help menus in the app provide users with additional control and support. The Settings menu offers a range of customizable options to tailor the app experience to individual preferences. Users can adjust the material and interface language, ensuring content is presented in their preferred language. Notification settings allow users to control how they receive updates and alerts. Additionally, the option to change the app's theme enhances personalization, contributing to a more comfortable and engaging interaction with the app. The Help menu serves as a resource for users seeking assistance or information
<div align="center">
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260274080-d3a984cf-9096-4f3f-806f-6a8f09df689d.png" width="400"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260274087-049c3975-2c9f-4204-a189-d9bbe8d936d1.png" width="400"></td>
    </tr>
  </table>
</div>

### Theme change
The theme change feature, accessible from the settings menu, empowers users to effortlessly switch between light and dark modes. This modification affects the entire app, dynamically altering the visual appearance of every screen to align with the chosen theme. By offering this seamless transition, users can adapt the app's aesthetics to match their preferences and create a cohesive user experience across all interactions. The images below are some examples of how the screens look in different themes.
<div align="center">
  <!-- Top Row of Images -->
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272242-187badb6-cd2f-43ad-af5a-6adfef5d88fc.png" width="250"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260274523-9c07ff6a-81ed-4b7f-b542-1099227c1274.png" width="250"></td>
    </tr>
  </table>
  
  <!-- Gap between Rows -->
  <div style="height: 20px;"></div>
  
  <!-- Bottom Row of Images -->
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260272243-c255fc02-b5a7-4aa9-b368-801c87425f2a.png" width="250"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260274089-7fdf3382-5384-49ac-b893-c33b7c28364f.png" width="250"></td>
    </tr>
  </table>
</div>

### Responsive Design for Various Screen Sizes

Indeed, in the SEEDS app, all resources such as images, fonts, and buttons are designed to be responsive and adapt to different screen sizes. This responsive design approach ensures that the app's visual elements maintain their proportions and legibility across a variety of devices, from smaller mobile screens to larger tablet displays. Whether you're using a compact mobile phone or a spacious tablet, the app's resources are programmed to dynamically adjust their sizes and layouts, providing users with an optimized and consistent experience no matter the device they choose to engage with. This attention to screen adaptability contributes to a seamless and enjoyable user interaction regardless of the screen's dimensions.</div>
<div align="center">
  <!-- Top Row of Images -->
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260321078-08e41dee-641f-4698-bf13-c3f700f6c1a4.png" width="250"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260321081-caa3fbf6-274b-4830-8be7-e2882a40360e.png" width="450"></td>
    </tr>
  </table>
  
  <!-- Gap between Rows -->
  <div style="height: 30px;"></div>
  
  <!-- Bottom Row of Images -->
  <table>
    <tr>
      <td><img src="https://user-images.githubusercontent.com/113074664/260321089-e9af7e32-c1bc-4944-a44f-06bc2f53a9b6.png" width="250"></td>
      <td><img src="https://user-images.githubusercontent.com/113074664/260321100-a21a6fcc-2ae8-4b2f-bc46-8b8e89499db7.png" width="450"></td>
    </tr>
  </table>
</div>
<br>
<br>

## License

This project is licensed under the [Educational Community License, Version 2.0 (ECL-2.0)](https://opensource.org/licenses/ECL-2.0).

You can find the full text of the license in the [LICENSE](LICENSE) file of this repository.

