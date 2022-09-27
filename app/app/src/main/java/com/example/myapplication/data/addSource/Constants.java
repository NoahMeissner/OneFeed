package com.example.myapplication.data.addSource;

public enum Constants {
    /*
    This Enum ist important to set the Categories and other
     */
    // Categories
    SocialMedia,
    Newspaper,
    Interests,
    ADDButton;

    // Todo: split into seperate files
    // This enum contains all interests Categories
    public enum interests{Corona, Politik, Sport, Technik, Gaming, Wirtschaft}
    // This enum contains all News Sources
    public enum news{FAZ,Spiegel}
    // This enum contains all Social Media Sources
    public enum socialMedia{Twitter,Reddit}
    // This Enum includes all Variable names for the Shared Preferences
    // Todo: rename to sharedprefrences?
    public enum initial{Notification, Process, ConsumptionAnalyse, InterestsAreInitialised}
    // Settings keys for insight (consumption analysis)
    // Todo: choose one name: insight / consumption analysis
    public enum insightSettings { articlesPerDay, limitationIsEnabled }
}
