package com.sprogel.musicalSorting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SortDrawer extends Activity {
  Thread sortThread;
  View sortView;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
//    sortView = getIntent().
    
    //TODO put at the end and make it the sort view
    //setContentView(sortView);
  }
}
