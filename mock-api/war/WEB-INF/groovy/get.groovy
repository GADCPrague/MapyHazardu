import java.util.logging.Logger;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.datastore.*
import static com.google.appengine.api.datastore.FetchOptions.Builder.*

import groovy.json.*

log.info("Request params: ${params}")

/* simple proxyfing of mapyhazardu api */
def lng = params["lng"] 
def lat = params["lat"]

def mapyHazarduApiUrl = new URL("http://api.mapyhazardu.cz/v0/hells/nearest/?lng=${lng}&lat=${lat}")

def HTTPResponse httpResponse = mapyHazarduApiUrl.get()
def mapyHazarduNearestCasinosJsonResponse = httpResponse.text;

/* merge our data with oficial api data */
def parse = new JsonSlurper()
def apiResult = parse.parseText(mapyHazarduNearestCasinosJsonResponse);

/* obtain data */

def query = new Query("Casino")
def preparedQuery = datastore.prepare(query)
def entities = preparedQuery.asList(withLimit(1000))


entities.each { entity ->
	
	/* is in geocoordinate bounds */
	def lngRange = (((lng as double) - 0.004d)..((lng as double) + 0.004d))
	def latRange = (((lat as double)- 0.004d)..((lat as double) + 0.004d))
	
	
	if (entity.latitude != null && entity.longitude != null) {
		
		def latitude = entity.latitude as double
		def longitude = entity.longitude as double
		if (latRange.containsWithinBounds(latitude)
				&& lngRange.containsWithinBounds(longitude)) {
		
				log.info("entity added to results as it is in boundary")
				apiResult << [title: entity.nazev ?: "", position: [longitude, latitude]]
		}
				
	}
	
	
}

def jsonOutput = new JsonOutput()
def mergedJsonResult = new JsonOutput().toJson(apiResult)

/* write it back to client */
response.contentType = "application/json"
response.writer.write(jsonOutput.prettyPrint(mergedJsonResult))
