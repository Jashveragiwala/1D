//package com.example.a1d;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//
//public class Add_locations_activity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private SupportMapFragment mMapFragment;
//    private SearchView mSearchView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_locations);
//
//        mMapFragment = SupportMapFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.mapContainer, mMapFragment)
//                .commit();
//        mMapFragment.getMapAsync(this);
//
//        mSearchView = findViewById(R.id.searchView);
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform the search
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // update the search results
//                return false;
//            }
//        });
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//    }
//}