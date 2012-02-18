import com.google.appengine.api.urlfetch.HTTPResponse;

log.info("Request params: ${params}")

/* simple proxyfing of mapyhazardu api */
def lng = params["lng"] 
def lat = params["lat"]

def mapyHazarduApiUrl = new URL("http://api.mapyhazardu.cz/v0/hells/nearest/?lng=${lng}&lat=${lat}")

def HTTPResponse httpResponse = mapyHazarduApiUrl.get()
def mapyHazarduNearestCasinosJsonResponse = httpResponse.text;

/* write it back to client */
response.contentType = "application/json"
response.writer.write(mapyHazarduNearestCasinosJsonResponse)
