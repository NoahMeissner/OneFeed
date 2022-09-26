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

    // This enum contains all interests Categories
    public enum interests{Corona, Politik, Sport, Technik, Gaming, Wirtschaft}
    // This enum contains all News Sources
    public enum news{FAZ,Spiegel}
    // This enum contains all Social Media Sources
    public enum socialMedia{Twitter}
    // This Enum includes all Variable names for the Shared Preferences
    public enum initial{Notification, Process, ConsumptionAnalyse, InterestsAreInitialised}
}
