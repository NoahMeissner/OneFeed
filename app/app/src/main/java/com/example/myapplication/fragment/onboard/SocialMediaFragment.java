package com.example.myapplication.fragment.onboard;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data.addSource.Constants;
import com.example.myapplication.animation.onboard.SocialMediaAnimation;
import com.example.myapplication.api.twitter.TwitterApiHelper;
import com.example.myapplication.data.addSource.Category;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;

public class SocialMediaFragment extends Fragment {

    private final Point size = new Point();
    private final int xSpeed = 1;
    private final int ySpeed = 4;
    private final int spacing = 330;
    private final int startPoint = 0;
    private getSelectedSocialMedia getSelectedSocialMedia;

    private TwitterApiHelper twitterApiHelper;
    private ActivityResultLauncher<Intent> twitterAuthenticationLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getSelectedSocialMedia = (getSelectedSocialMedia) context;

        this.twitterApiHelper = new TwitterApiHelper(context);
        // Opens a browser window where users can link their twitter account
        this.twitterAuthenticationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        AuthorizationResponse resp = AuthorizationResponse.fromIntent(result.getData());
                        AuthorizationException ex = AuthorizationException.fromIntent(result.getData());
                        twitterApiHelper.handleAuthenticationResponse(resp, ex, () -> {
                                Log.d(TAG, "Twitter Authentication successful!");
                                twitterApiHelper.writeAuthState(getContext());
                            }
                        );
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_media_fragement, container, false);
        organizeAnimation(Constants.socialMedia.Reddit.name(),view,R.id.imageRedditButton,xSpeed,ySpeed);
        organizeAnimation(Constants.socialMedia.Twitter.name(), view,R.id.imageTwitterButton,-2*xSpeed,xSpeed-ySpeed);
        return view;
    }

    /*
    This class organises the animation of the social media buttons
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void organizeAnimation(String socialMediaName,View view, int id, int xSpeed, int ySpeed){
        WindowManager windowManager = requireActivity().getWindowManager();
        windowManager.getDefaultDisplay().getSize(size);
        SocialMediaAnimation socialMediaAnimation = view.findViewById(id);
        setBubbleAnimation(socialMediaName, socialMediaAnimation, xSpeed, ySpeed);
    }


    /*
    This method sets the animations for each individual object
     */
    private void setBubbleAnimation(String socialMediaName, SocialMediaAnimation socialMediaAnimation, int xSpeed, int ySpeed) {
        TypedArray a = requireContext().getTheme().obtainStyledAttributes(
                R.style.AppTheme, new int[]{androidx.appcompat.R.attr.colorPrimary}
        );
        int attributeResourceId = a.getResourceId(0, 0);
        DrawableCompat.setTint(socialMediaAnimation.getDrawable(), requireContext().getColor(attributeResourceId));
        socialMediaAnimation.setX(startPoint, size.x - spacing);
        socialMediaAnimation.setY(spacing, size.y - spacing - spacing);
        socialMediaAnimation.setXSpeed(xSpeed);
        socialMediaAnimation.setYSpeed(ySpeed);
        socialMediaAnimation.animateBubbles();
        stopAnimation(socialMediaAnimation, socialMediaName);
    }

    /*
    This method stops the animation, with an clickListener
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void stopAnimation(SocialMediaAnimation socialMediaAnimation, String socialMediaName) {
        socialMediaAnimation.setOnClickListener(view1 -> {
            // Launch the twitter authentication view
            twitterAuthenticationLauncher.launch(twitterApiHelper.createAuthorizationIntent());

            // Style the view accordincly
            // Todo: undo styling, if the authentication failed
            DrawableCompat.setTint(socialMediaAnimation.getDrawable(), ContextCompat.getColor(requireContext(), R.color.white));
            socialMediaAnimation.stopAnimation();
            socialMediaAnimation.setBackground(getResources().getDrawable(R.drawable.customyesbutton, requireActivity().getTheme()));
            saveData(socialMediaName);
        });
    }

    private void saveData(String socialMediaName) {
        getSelectedSocialMedia.getSelectedSocialMedia(Constants.socialMedia.valueOf(socialMediaName));
    }

    public interface getSelectedSocialMedia{
        void getSelectedSocialMedia(Constants.socialMedia socialMedia);
    }
}