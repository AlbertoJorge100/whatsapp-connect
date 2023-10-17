package com.example.whatsapp_connect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp_connect.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button BtnAceptar;
    private EditText TxbNumber;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.BtnAceptar = findViewById(R.id.btnAceptar);
        this.TxbNumber = (EditText)findViewById(R.id.txbNumber);
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.title_app), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        this.TxbNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        this.TxbNumber.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        TextView txbText = findViewById(R.id.txbText);
        TextView txbSign = findViewById(R.id.txbSign);
        txbSign.setText(getString(R.string.title_app));
        txbText.setText("Ingrese el código de área y # de telefono, luego presione el boton \"Aceptar\".");
        this.BtnAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String number = TxbNumber.getText().toString();
                int length = number.length();
                if(length == 0 || length < 8){
                    TxbNumber.setError("Debe ingresar un # de telefono");
                    Snackbar.make(view, "No deje campos vacíos!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                Intent intn = new Intent();
                intn.setAction(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone="+number+"&text=";
                intn.setData(Uri.parse(uri));
                startActivity(intn);
                //Toast.makeText(getApplicationContext(), "numero: "+number, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}