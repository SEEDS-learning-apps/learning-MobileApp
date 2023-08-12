## SEEDS Learning Application
![Seeds_Logo.png](https://github.com/SEEDS-learning-apps/learning-MobileApp/blob/masters-thesis-sakthidaran/app/src/main/res/drawable-xxxhdpi/seeds_logo.png)
The SEEDS Android app is a cutting-edge educational platform crafted with Kotlin programming language. It's meticulously designed to revolutionize distance learning, offering students an intuitive and engaging learning experience. Formed through a dynamic collaboration between three distinguished institutions - Universität Siegen, University of Alicante in Spain, and Openlabs in Athens - SEEDS represents a powerful consortium. This collective effort was nurtured by the support of the Erasmus program of the European Union.

By seamlessly blending technology and education, SEEDS redefines the boundaries of remote learning, fostering a holistic approach that empowers students across diverse geographies.

[![Coded in Kotlin](https://img.shields.io/badge/Coded%20in-Kotlin-blueviolet)](https://kotlinlang.org/) [![Open Source](https://img.shields.io/badge/Open%20Source-Yes-green.svg)](https://opensource.org/) [![License](https://img.shields.io/badge/License-Educational%20Community%20License%202.0-blue.svg)](https://opensource.org/licenses/ECL-2.0)

## Table of contents
1. [Project Objectives](#projectobjectives)
2. [Getting Started](#getting-started)
3. [Features](#features)
4. [App Architecture](#app-architecture)
5. [Libraries and Framework](#libraries-and-framework) 
6. [Comprehensive Data Storage Approach in the SEEDS Android App](#comprehensive-data-storage-approach-in-the-seeds-android-app)
7. [Resources](#resources)
8. [App Configuration](#app-configuration)
9. [Screenshots](#screenshots) 
10. [License](#license)
11. [Contact](#contact)
   
## Project Objectives
The primary objective of the SEEDS project is to transcend the challenges presented by post-pandemic education. Beyond this, the project aims to amplify the learning experience through meticulous design strategies. By integrating an interactive user interface (UI) and an extensive range of features, the application is meticulously crafted to foster an engaging and dynamic learning process. Rooted in a user-centric approach, the app actively encourages participation and exploration, effectively transforming education into an enriching journey.

Furthermore, the project caters to the diverse linguistic backgrounds of learners by offering support in four prominent languages: English, German, Spanish, and Greek. This multilingual capability ensures that students from various countries can comfortably engage with the app in their native language, promoting a sense of familiarity and ease while learning. Whether students are immersed in multimedia-rich lessons, participating in interactive quizzes, or interacting with the AI-driven Rasa chatbot, the project prioritizes the creation of meaningful engagements that enhance comprehension.

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

- **[ViewPager2](https://developer.android.com/guide/navigation/gesturenav)**: ViewPager2 simplifies the creation of swipeable layouts, enabling you to display fragments or views that users can navigate by swiping left or right.

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

The resources stored in the `res` folder play a pivotal role in shaping the app's visual identity, interactivity, and overall user experience.


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

## License

This project is licensed under the [Educational Community License, Version 2.0 (ECL-2.0)](https://opensource.org/licenses/ECL-2.0).

You can find the full text of the license in the [LICENSE](LICENSE) file of this repository.

