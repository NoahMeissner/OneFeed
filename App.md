# OneFeed

## Target Group

> Everyone who consumes content on the internet.

Neuropsychologists such as Lutz Jäncke report that the users brain is overwhelmed by the flood of information on the Internet, and thus loses the ability to distinguish between qualitative and quantitative content. Therefore, OneFeed should make it possible to consume all kinds of written media on the Internet through one channel and thus also to control it in order to prevent the brain from being overwhelmed.

## End result of the project

The app allows you to pre-select news portals on your own. You have the option of specifying your interests, and your consumption is tracked and blocked if you have activated the blocking function in the app.

The app does not offer the option of adding an RSS feed independently, as these XML files are always structured differently.

The app does not include its own reader, as we are unable to download the texts from news portals for legal reasons. Instead, an in-app browser window is being launched.

## Further Hints

- To test the app, you will need a Twitter account to log in with during the onboarding process. 
- To install the app, you need a mobile phone with at least Android Q.
- The app has been programmed for use on a smartphone

---

 # Showcase
 
## Onboarding Process

- This Process initial the whole Set Up of your Application
- You can select your interests and your favourite Sources (ABB.1)
- You can log in with your Twitter account too
- The app asks for a few permissions to clarify the app's authority(ABB.2)


![add_new_buttons_-_SD_480p_AdobeExpress (1)](https://user-images.githubusercontent.com/108337767/194114299-c5e8acb4-250f-42a6-bbcb-cb8f16802334.gif)
(ABB.1)

![Bildschirmfoto 2022-10-05 um 18 40 34](https://user-images.githubusercontent.com/108337767/194116830-8382e15e-bb4e-4fc9-9ed2-eea62e816903.png)
(ABB.2)

## Feed

The feed is the main component of the app. From all configured sources, a single feed is being created. In the first version of OneFeed, it contains news from Spiegel, FAZ and Twitter. Scrolling the feed causes the title bar to collapse.


<p float="left">
  <img src="docs/feed.png" width=25% height=25%>
  <img src="docs/feed-scrolling.png" width=25% height=25%>
</p>

Clicking on an article opens a reader or the Twitter app (if installed).

<img src="docs/reader-article.png" width=25% height=25%>

Pulling on the feed causes it to refresh.

<img src="docs/feed-refreshing.png" width=25% height=25%>

## Insight View

The insight can be enabled by the user. If it is disabled, the user will be asked to enable it upon opening the view.

<img src="docs/insight-opt-in-prompt.png" width=25% height=25%>

When enabled, OneFeed counts the articles and Tweets opened by the user in the reader. In the insight view a graph is displaying how many articles a user read each day to give the user transparency about their own news consumption.

<img src="docs/insight.png" width=25% height=25%>

Users can also limit the amount of articles they can open in the reader each day. If the user has reacher the daily limit, a toast notification is displayed in the feed and articles can't be opened in the reader.

<img src="docs/feed-limit-reached.png" width=25% height=25%>

The insight feature can be disabled by toggling the checkbox next to "Angezeigte Artikel pro Tag". This stops the counting and users have to opt in to the feature again.

<img src="docs/insight-opt-out.png" width=25% height=25%>

## Settings 

- you can edit your sources and interests in the settings part of the app
- The settings will be saved in a Room Database and in Shared Preferences
- The Notifcation Service will be updatet in the Settings Activity to get the right Sources for the Notifications
- The Delete Animation shows if you press long on one item that you are in the Delete Mode (ABB.3)
	- -> shows visbile the User that he can now delete Items!
- If you press on the Add Buttons you will get a menu with all sources, which are availabe (ABB.4)

![add_activity_-_SD_480p_AdobeExpress](https://user-images.githubusercontent.com/108337767/194116881-fcfe24a1-8fe0-4106-aed2-897c5637d50e.gif)
(ABB.3)

![addnew icons](https://user-images.githubusercontent.com/108337767/194116907-47ae0a2c-167a-48f5-8223-c1eff1984fda.png)
(ABB.4)

## Notification Service

- To remember the User about possible new News, we have implementet an Notification Service
- If the app is active, a time slot in which a message is to be sent is communicated to a broadcast
   receiver via a service.
- The slots are defined in the app so that the user can only be reminded at certain times.
- Once a notification has been sent, none is sent until the user opens the app again,
   so as not to disturb the user with many messages.
   
## Additional Features

- in order to be able to use the app with large displays, we use multi-gestures to facilitate navigation.(ABB.5)

![multigesures_AdobeExpress](https://user-images.githubusercontent.com/108337767/194118491-d3a5bbcc-a430-4bb3-8cce-a1791f141a98.gif)
(ABB.5)
