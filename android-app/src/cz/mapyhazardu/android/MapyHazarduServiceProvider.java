/**
 * 
 */
package cz.mapyhazardu.android;

import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.impl.MapyHazarduImpl;

/**
 * @author vlasta
 *
 */
public class MapyHazarduServiceProvider {
	public static MapyHazardu getService() {
//		return new MapyHazarduMock();
		return new MapyHazarduImpl("http://stophazardu.appspot.com/api");
	}
}
