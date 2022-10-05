# OneFeed

## Target Group: anyone who consumes content on the internet

Neuropsychologists such as Lutz Jäncke report that the user's brain is overwhelmed by the flood of information on the Internet and thus loses the ability to distinguish between qualitative and quantitative content. Therefore, One Feed should make it possible to consume all media consumption on the Internet through one channel and thus also to control it in order to prevent the brain from being overwhelmed.

## Features und Szenarien

_Beschreiben Sie die wichtigsten Funktionen Ihrer Anwendung. Was können die Nutzer\*innen mit Ihrer Anwendung tun? Verwenden Sie zur Beschreibung Beschreiben dieser zentralen Features auch Screenshots oder animierte Gifs aus Ihrer Anwendung. Dokumentieren Sie im Anschluss anhand ausgewählter Szenarien einige ausgesuchte, wichtige Arten der Nutzung der App durch die Zielgruppe. Beschreiben Sie dazu anhand eines gedachten Walkthroughs durch die Anwendung, in welcher Reihenfolge die Nutzer\*innen die genannten Funktionen der Anwendung verwenden, um wichtige Ziele zu erreichen._

## Erreichter Stand

The app allows you to pre-select news portals on your own. You have the option of specifying your interests and your consumption is tracked and blocked if you have set your daily Limit in the app.

- The app does not offer the option of adding an RSS feed independently, as these xml files are always structured differently.
- The app does not include its own reader, as we are unable to download the texts from news portals for legal reasons. 

## Further Hints
- To test the app, you will need a Twitter account to log in with during the onboarding process. 
- To install the app, you need a mobile phone with at least Android Q. The app is designed for smartphones.
- The app has been programmed for use on a smartphone


---

@Todo Max

---

@Todo Noah

### Onboarding Process

- This Process initial the whole Set Up of your Application
- You can select your interests and your favourite Sources (ABB.1)
- You can log in with your Twitter account too
- The app asks for a few permissions to clarify the app's authority(ABB.2)


![add_new_buttons_-_SD_480p_AdobeExpress (1)](https://user-images.githubusercontent.com/108337767/194114299-c5e8acb4-250f-42a6-bbcb-cb8f16802334.gif)
(ABB.1)

![Bildschirmfoto 2022-10-05 um 18 40 34](https://user-images.githubusercontent.com/108337767/194115218-fd2ba1b1-f253-4a71-a517-bba8935c60c0.png)
(ABB.2)

### Settings 

- you can edit your sources and interests in the settings part of the app
- The settings will be saved in a Room Database and in Shared Preferences
- The Notifcation Service will be updatet in the Settings Activity to get the right Sources for the Notifications
- The Delete Animation shows if you press long on one item that you are in the Delete Mode (ABB.3)
	- -> shows visbile the User that he can now delete Items!
- If you press on the Add Buttons you will get a menu with all sources, which are availabe (ABB.4)

https://user-images.githubusercontent.com/108337767/194114669-673d4fa9-7677-4808-a4c1-b8d41af9e9d8.mov
(ABB.3)

![addnew icons](https://user-images.githubusercontent.com/108337767/194114382-19fd27ff-380c-4904-a83b-c6e678530f96.PNG)
(ABB.4)


### Notification Service

- To remember the User about possible new News, we have implementet an Notification Service
- If the app is active, a time slot in which a message is to be sent is communicated to a broadcast
   receiver via a service.
- The slots are defined in the app so that the user can only be reminded at certain times.
- Once a notification has been sent, none is sent until the user opens the app again,
   so as not to disturb the user with many messages.
