package cz.mapyhazardu.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.actionbarcompat.R;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import cz.mapyhazardu.android.ActionBarActivity;

public class EditActivity extends ActionBarActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		MapController mapController = mapView.getController();
		mapController.setZoom(19);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_ok:
			Intent intent = new Intent("cz.mapyhazardu.android.activity.LOCATION");
			startActivity(intent);
			break;
		case R.id.menu_cancel:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
