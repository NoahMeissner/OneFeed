package com.example.myapplication.api.rss;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class CustomImageRequest {

        private final String iconurl;
        private final Context context;

        public CustomImageRequest(String iconurl, Context context) {
            this.iconurl = iconurl;
            this.context = context;
        }

        public void run(Response.Listener<Bitmap> listener) {
            RequestQueue queue = Volley.newRequestQueue(context);
            ImageRequest imageRequest = new ImageRequest(iconurl, listener, 0, 0, ImageView.ScaleType.CENTER, null, error -> {
                CharSequence sentence = "Bild konnte nicht geladen werden";
                Toast.makeText(context.getApplicationContext(), sentence, Toast.LENGTH_LONG).show();
            });
            queue.add(imageRequest);
        }

        public interface RequestListener {
            void onResult(Bitmap icon);
        }
}
