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
   
#ProjectObjectives
The primary objective of the SEEDS project is to transcend the challenges presented by post-pandemic education. Beyond this, the project aims to amplify the learning experience through meticulous design strategies. By integrating an interactive user interface (UI) and an extensive range of features, the application is meticulously crafted to foster an engaging and dynamic learning process. Rooted in a user-centric approach, the app actively encourages participation and exploration, effectively transforming education into an enriching journey.

Furthermore, the project caters to the diverse linguistic backgrounds of learners by offering support in four prominent languages: English, German, Spanish, and Greek. This multilingual capability ensures that students from various countries can comfortably engage with the app in their native language, promoting a sense of familiarity and ease while learning. Whether students are immersed in multimedia-rich lessons, participating in interactive quizzes, or interacting with the AI-driven Rasa chatbot, the project prioritizes the creation of meaningful engagements that enhance comprehension.

Through the seamless fusion of educational principles and technological advancements, the SEEDS project aspires to cultivate an environment where learners can excel, thereby shaping the trajectory of accessible and impactful global education.

## Translation files
Path: res\values\strings

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

