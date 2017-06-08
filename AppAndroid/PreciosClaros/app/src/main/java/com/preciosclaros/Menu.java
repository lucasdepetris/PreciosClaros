package com.preciosclaros;

import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by lucas on 8/6/2017.
 */

public class Menu extends HomeActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

    public Toolbar crearToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        return  toolbar;
    }
    public DrawerLayout crearDrawable(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        return  drawer;
    }
    public ActionBarDrawerToggle crearToogle(){
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        return  toggle;
    }
    public NavigationView crearNavigationView(){
        return this.navigationView;
    }
}
