## SEEDS learning app
The SEEDS Android app is a cutting-edge educational platform crafted with Kotlin programming language. It's meticulously designed to revolutionize distance learning, offering students an intuitive and engaging learning experience. Formed through a dynamic collaboration between three distinguished institutions - Universität Siegen, University of Alicante in Spain, and Openlabs in Athens - SEEDS represents a powerful consortium. This collective effort was nurtured by the support of the Erasmus program of the European Union.

By seamlessly blending technology and education, SEEDS redefines the boundaries of remote learning, fostering a holistic approach that empowers students across diverse geographies.

## Table of contents
1. [Project Objectives](#ProjectObjectives)
2. [Getting Started](#getting-started)
3. [Features](#features)
4. [Usage](#usage)
5. [Screenshots](#screenshots)
6. [License](#license)
7. [Contact](#contact)
   
## Project Objectives
The primary objective of the SEEDS project is to transcend the challenges presented by post-pandemic education. Beyond this, the project aims to amplify the learning experience through meticulous design strategies. By integrating an interactive user interface (UI) and an extensive range of features, the application is meticulously crafted to foster an engaging and dynamic learning process. Rooted in a user-centric approach, the app actively encourages participation and exploration, effectively transforming education into an enriching journey.

Furthermore, the project caters to the diverse linguistic backgrounds of learners by offering support in four prominent languages: English, German, Spanish, and Greek. This multilingual capability ensures that students from various countries can comfortably engage with the app in their native language, promoting a sense of familiarity and ease while learning. Whether students are immersed in multimedia-rich lessons, participating in interactive quizzes, or interacting with the AI-driven Rasa chatbot, the project prioritizes the creation of meaningful engagements that enhance comprehension.

Through the seamless fusion of educational principles and technological advancements, the SEEDS project aspires to cultivate an environment where learners can excel, thereby shaping the trajectory of accessible and impactful global education.

## Getting Started
- **Download the App**: To access the SEEDS Android application, you can download the project package from the provided link. This ensures you're getting the official and up-to-date version of the app.

- **Choose Your Language**: Once you've downloaded the app, open it and select your preferred language from the available options: English, German, Spanish, or Greek. This selection ensures you engage with the content in a language that suits you best.

- **Explore the Interface**: With your language preference set, navigate through the user-friendly interface. Familiarize yourself with the layout and sections that offer a variety of interactive features to enhance your learning.

- **Access Learning Materials**: Depending on your internet connection, you can access the app's learning materials in different modes. When online, you can engage with the Rasa chatbot, delve into multimedia-enriched lessons, and explore interactive quizzes. In offline mode, you can still access previously downloaded learning materials and utilize the offline chatbot for a seamless experience.

- **Unlock Private Content**: If your teachers have shared private content, you can access it by using the Access Code they provide. This ensures you receive personalized educational content that aligns with your learning goals.

## Features
- **Tailored Onboarding Experience for New and Returning Users**: Upon launching the SEEDS app, users are met with a personalized experience that adapts to their status. New users are prompted with an enticing choice: "New User" Opting for this, they're treated to a captivating introduction enhanced by Json animations and an informative view that outlines the app's features. This immersive preview is followed by effortless navigation to the registration screen to establish their account. In contrast, returning users can select the option "Already a User" which swiftly guided them to the login screen, acknowledging their familiarity with the app. This dual approach optimizes user engagement, fostering an inclusive and efficient learning journey.

- **Tab Layout for Organized Navigation**: The app's intuitive tab layout streamlines your experience, dividing content into three distinct screens. The first tab hosts the chatbot interface, providing seamless interactions and delivering subject-specific activities. The second tab showcases a history of completed activities, offering insights into your learning progress. The final tab features your student dashboard, a hub of statistics and insights into your learning journey.

- **Interactive Chatbot**: Within the chatbot interface, you can engage in dynamic conversations. The chatbot recommends activities tailored to the subject and topic you've selected, ensuring a personalized learning experience that adapts to your academic needs.

- **Diverse Activity Types**: The app offers a range of engaging activity types to cater to various learning styles. Choose from multiple options, including multiple-choice questions (MCQs), true or false questions, matching activities, and open-ended questions. This diversity ensures a comprehensive and well-rounded learning process.

- **Student Dashboard and Activity Statistics**: The SEEDS app offers a comprehensive student dashboard that provides a personalized overview of your learning journey. Within the dashboard, you can access detailed statistics regarding completed activities, track your progress, and monitor your overall performance, empowering you to take ownership of your learning path.

- **Flashcards for Enhanced Learning**: Upon completing activities, the app presents a dynamic learning opportunity through flashcards. These flashcards reinforce your understanding of the material and provide a quick and interactive review of key concepts, ensuring a deeper grasp of the content.

- **Material Download for Offline Access**: The app facilitates offline learning by allowing you to download materials for later access. Whether you're in an area with limited connectivity or wish to study on the go, downloading materials ensures continuous learning without the need for a constant internet connection.

- **Customizable Notifications**: Tailoring the learning experience to your schedule, the app's notification settings enable you to receive timely updates. Set notifications based on specific times and days that best suit your learning preferences, ensuring you never miss out on important content.

- **Personalized Theme Selection**: Recognizing the significance of user comfort, the app introduces theme customization. Choose between light and dark modes to create a personalized visual experience that aligns with your preference and reduces eye strain during extended study sessions.


## Network calls

- Retrofit
path: networking\Retrofit\Seeds_api\api

## Room Database

- path: chat_bot\Room
- type converters: Room\Converters\ (datatype converters for room database)
- Database relations Room\Relations (Database joins and relations declaration)

## Resources
Res folder contains all resources such as font, images, strings, animations, layout (XML files)

## Pre-made responses
Utils folder contains all the utilities used in the system such as pre made responses in all languages (English, Spanish, Greek and German), Login and logout session managers e.t.c

