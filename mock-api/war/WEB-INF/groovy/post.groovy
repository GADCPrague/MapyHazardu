import groovy.json.JsonSlurper;

import javax.servlet.http.HttpServletResponse;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point
import com.google.appengine.api.datastore.Entity

def dataParam = params.get("data")
log.info("Param: " + dataParam)

def parser = new JsonSlurper()
def parsedData = parser.parseText(dataParam)

def position = new Point(parsedData.latitude, parsedData.longtitude)
def cells = GeocellManager.generateGeoCell(position)
parsedData.cells = cells

def newCasino = new Entity("Casino")
newCasino << parsedData
newCasino.save()

response.status = HttpServletResponse.SC_CREATED