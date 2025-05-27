<pre>
<div align="center">
Movie Player App
2Coders' test task



In order to complete this task and to understand the proper "required" tech stack I designed the 
simple <a href="https://www.figma.com/design/CjVBZff1syCrr95vpWsbaL/Media-Player-TA?node-id=0-1&t=s3Rq9gdTYHkWQiZP-1"><b>Figma file</b></a> that contains all the screens and explains the navigation of an application.

I'd like also to add here that the design is based on the TA description and my
imagination of the task in general, so when I fetched the TMDB's api and realized they
don't provide any streaming or video links I decided to proceed with the design I created
anyway but with just "placeholders" instead of the real video links
</div>

  
Tech stack:

1. Architecture - MVVM
2. UI - Jetpack Compose
3. Navigation - Navigation Compose
4. Data storage - Preferences DataStore
5. Networking - Retrofit
6. DI - Hilt
7. Unit Tests - JUnit
8. Overall Media Handling - Foreground Service
9. Media Playback Handling - ExoPlayer
10. Media Control (Notification) - MediaSession 
11. Image Loading - Coil

Additionally I want to highlight resources usage - resources are in an SVG format to reduce app size
and to have a flexibility with usage (colors, shapes etc)

TODO - Fix search, fix UI blicks, to do entire media part, to do downloads
</pre>
