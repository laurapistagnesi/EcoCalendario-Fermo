package com.example.ecocalendariofermo;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;

public class MappaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mappa);
        getSupportActionBar().setTitle("Ecocentri Comunali");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mappa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MappaActivity.this, CalendarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng ecocentro = new LatLng(43.14658975621922, 13.708196637945887);
        LatLng humana1 = new LatLng(43.187971374986425, 13.660899362113252);
        LatLng humana2 = new LatLng(43.11666842439767, 13.597653726050984);
        LatLng isola1 = new LatLng(43.168919592276566, 13.729054882069285);
        LatLng isola2 = new LatLng(43.16576929167554, 13.733717960478433);
        LatLng isola3 = new LatLng(43.166992684576485, 13.7296305978707);
        LatLng isola4 = new LatLng(43.16397524993229, 13.723429989719907);
        LatLng isola5 = new LatLng(43.15703030808601, 13.723711813640833);
        LatLng isola6 = new LatLng(43.15641043733158, 13.734437453690909);
        LatLng isola7 = new LatLng(43.15638762050798, 13.727945203706808);
        LatLng isola8 = new LatLng(43.159504129577414, 13.708149728696204);
        MarkerOptions marker = new MarkerOptions().title("Isola Ecologica").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_isole)));
        map.addMarker(new MarkerOptions().position(humana1).title("Raccolta abiti HUMANA").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.tshirt))));
        map.addMarker(new MarkerOptions().position(humana2).title("Raccolta abiti HUMANA").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.tshirt))));
        map.addMarker(new MarkerOptions().position(ecocentro).title("Ecocentro Asite").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_recycle))));
        map.addMarker(marker.position(isola1));
        map.addMarker(marker.position(isola2));
        map.addMarker(marker.position(isola3));
        map.addMarker(marker.position(isola4));
        map.addMarker(marker.position(isola5));
        map.addMarker(marker.position(isola6));
        map.addMarker(marker.position(isola7));
        map.addMarker(marker.position(isola8));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ecocentro)          //Imposta il centro della mappa
                .zoom(12)                   //Imposta lo zoom
                .bearing(90)                //Imposta l'orientamento verso est
                .tilt(30)                   //Imposta l'inclinazione a 30 gradi
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {
        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

}