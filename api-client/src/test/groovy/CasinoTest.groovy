import org.junit.Test;

import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.OpeningHours;
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;

class CasinoTest extends GroovyTestCase {

	def void test1() {
		
		def casino = new Casino();
		casino.setRunner(new Runner("12218989"))
		casino.setOwner(new Owner("1234444"))
		casino.setOpeningHours(new OpeningHours(6, 10))
		casino.setName("casino 1337")
		
	}
	
}