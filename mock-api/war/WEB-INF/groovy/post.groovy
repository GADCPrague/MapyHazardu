import groovy.json.JsonSlurper;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity

def dataParam = params.get("data")
log.info("Param: " + dataParam)

def parser = new JsonSlurper()
def parsedData = parser.parseText(dataParam)

def newCasino = new Entity("Casino")
newCasino << parsedData
newCasino.save()

response.status = HttpServletResponse.SC_CREATED