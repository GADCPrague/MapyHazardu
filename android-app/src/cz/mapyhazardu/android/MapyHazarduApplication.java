/**
 * 
 */
package cz.mapyhazardu.android;

import java.util.HashSet;
import java.util.Set;

import android.app.Application;
import cz.mapyhazardu.api.domain.Casino;

/**
 * @author vlasta
 *
 */
public class MapyHazarduApplication extends Application {

	private final Set<Casino> casinosCache = new HashSet<Casino>();

	public Set<Casino> getCasinosCache() {
		return casinosCache;
	}
	
}
