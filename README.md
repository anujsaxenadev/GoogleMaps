<h1 align="center">GoogleMaps</h1><br>

<p align="center">
Uses Google Maps API cache API Data best for in Low Energy Environment. This README provides a comprehensive guide to understanding and using Fidelio effectively.
</p><br>

<p align="center">
  <img alt="Static Badge" src="https://img.shields.io/badge/OS-Android-lightgreen?style=for-the-badge&logo=android&logoColor=lightgreen">
  <img alt="Static Badge" src="https://img.shields.io/badge/Language-Kotlin-1DA1F2?style=for-the-badge&logo=kotlin&logoColor=lightgreen">
  <img alt="Static Badge" src="https://img.shields.io/badge/Min%20API%20Level-24-lightgreen?style=for-the-badge&logo=androidstudio&logoColor=lightgreen">
  <img alt="Static Badge" src="https://img.shields.io/badge/License-MIT-1DA1F2?style=for-the-badge&logo=readme&logoColor=1DA1F2">
</p>

<p align="center">
  <img alt="Static Badge" src="https://img.shields.io/badge/Version%20Control-Git-f18e33?style=for-the-badge&logo=github&logoColor=white">
  <img alt="Static Badge" src="https://img.shields.io/badge/Contribution-Welcome-lightgreen?style=for-the-badge&logo=githubactions&logoColor=lightgreen">
</p>

## Table of Contents
- [Features](#features)
- [License](#license)
- [Roadmap](#roadmap)

## Features

### 1. Google Maps API
Uses Google Maps Javascript APIs to fetch Data:
- Assets Dir Containing Html Code
- Token Used is Temporary. (Use With Care)

### 2. Intercepting API Calls
API Intercepting Api Calls happening inside webview (check - WebViewInterceptor):
- To alter response of web request.
- Sending Cached Data in Case if data is already present.

### 3. Saving Resources
To make sure same resource is not fetched multiple times:
- Saving resource in cacheDir (App Storage).
- To make Sure each Source is Unique. A UniqueIdentifier is been created for each WebResourceRequest.
- Saving the Mapping of saved resources in Room DB.

### 3. MultiModule Architecture
To make sure the scalablity of the application, multiple modules are created:
- Every seperate functionality has been kept in seperate module.
- A Custom Gradle plugin is applied to Application and library to make modules dependency handling easier.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Roadmap
Our future plans include:
- Adding Cache Clearing Stratergy to make sure cache have some validity.
- Encrypting the Resources before storing to cacheDir.
- Using NDK to store the Google Maps API Key.
- Using Jetpack Compose Instead of XML Layouts.
- Using Some other Libraries like Coil for caching images.
- To check Another approach of Intercepting API Calls in Android Webiew (Through Javascript)
