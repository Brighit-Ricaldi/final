package com.fortaleza.appfortaleza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fortaleza.appfortaleza.model.Marcador;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback  {



    private final String TAG = getClass().getSimpleName();

    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    //private ArrayList<Marker>tmpRealTimemarker = new ArrayList<>();
    //private ArrayList<Marker> realTimeMarker = new ArrayList<>();

    private ArrayList<Marcador> data = new ArrayList<>();
    private Map<String, Integer> mapaMarcadorData = new HashMap<>();


    // Objeto que instancia el BottomSheet
    private BottomSheetBehavior bottomSheetBehavior;
    // Esta será la vista del BottomSheet, puede ser cualquier tipo de View
    private LinearLayout lnlBottomSheet;


    // Los valores
    private TextView txtRazonSocial;
    private TextView txtNombres_Apellidos;
    private TextView txtTelefono;
    private TextView txtCelular;
    private TextView txtCorreo;
    private TextView txtRepresentante_Cargo;
    private TextView txtRuc;

    Toolbar mToolbar;



    //Geolocalizacion
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //-------------------------------------------------------------------------------------------------------------
        lnlBottomSheet = findViewById(R.id.lnlBottomSheet);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtNombres_Apellidos = findViewById(R.id.txtNombres_Apellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCelular = findViewById(R.id.txtCelular);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtRepresentante_Cargo = findViewById(R.id.txtRepresentante_Cargo);
        txtRuc = findViewById(R.id.txtRuc);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mapa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Se asocia el BottomSheet a partir de la vista contenedora deseada
        bottomSheetBehavior = BottomSheetBehavior.from(lnlBottomSheet);
        // Inicia con el estado HIDDEN
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        //Asignar Variable
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //Ici
        client = LocationServices.getFusedLocationProviderClient(this);

        //Checar permisos
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission grated
            //call method
            getCurrentLocation();
        } else {
            //When permissed denied
            //Request permission
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


    }

    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //when success
                if (location!=null){
                    //sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Initialize lat lng
                            LatLng latLng = new LatLng(location.getLatitude()
                                    ,location.getLongitude());
                            //create marker options
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("Mi Ubicación");
                            //Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                            //Add marker on map
                            googleMap.addMarker(options);

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //When permission grated
                //Call method
                getCurrentLocation();
            }
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //vv
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5));



        //aqui ponemos los marcadores
        databaseReference.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Se limpia el mapa por completo
                googleMap.clear();
                // Se limpia la lista que contiene la información de cada marcador
                data.clear();
                // Se limpia el mapa que relaciona los ids de los marcadores con la posición de la data en la lista
                mapaMarcadorData.clear();

                /*for (Marker marker:realTimeMarker){
                    marker.remove();
                }*/
                int posicion = 0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Marcador mk = snapshot.getValue(Marcador.class);

                    // Insertar la data el marcador en Marcadores
                    data.add(mk);

                    Double latitud = mk.getLatitud();
                    Double longitud = mk.getLongitud();
                    String razon_social = mk.getRazon_social();
                    String telefono = mk.getTelefono();
                    String celular = mk.getCelular();
                    String email = mk.getEmail();
                    String nombre_apellidos = mk.getNombre_apellidos();
                    String representante_cargo = mk.getRepresentante_cargo();
                    String ruc = mk.getRuc();

                    //Personalizar el tamaño del marcador
                    int height = 150;
                    int width = 100;

                    //cambiar o definir la imagen del  marcador
                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_distribuidores);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).title(razon_social);
                    markerOptions.position(new LatLng(latitud,longitud));
                    //tmpRealTimemarker.add(mMap.addMarker(markerOptions));
                    Marker marker = mMap.addMarker(markerOptions);

                    Log.d(TAG, "Se inserta en el mapaMarcadorObjeto el id de marcador: " + marker.getId());
                    Log.d(TAG, "Se asocia con la key del registro de Firebase" + snapshot.getKey());
                    mapaMarcadorData.put(marker.getId(), posicion);
                    posicion++;

                    //cambiamos el icono del marcador
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    LatLng coordinate = new LatLng(latitud,longitud);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 150);

                    ////zoom al mapa}
                    //mMap.animateCamera(location);
                }
                /*realTimeMarker.clear();
                realTimeMarker.addAll(tmpRealTimemarker);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Aquí se debe identificar qué establecimiento ha sido seleccionado
                Marcador distribuidor = data.get(mapaMarcadorData.get(marker.getId()));
                txtRazonSocial.setText(distribuidor.getRazon_social());
                txtNombres_Apellidos.setText(distribuidor.getNombre_apellidos());
                txtTelefono.setText(distribuidor.getTelefono());
                txtCelular.setText(distribuidor.getCelular());
                txtCorreo.setText(distribuidor.getEmail());
                txtRepresentante_Cargo.setText(distribuidor.getRepresentante_cargo());
                txtRuc.setText(distribuidor.getRuc());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return false;
            }
        });
    }
}