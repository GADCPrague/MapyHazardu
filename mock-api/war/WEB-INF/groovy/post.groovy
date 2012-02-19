import groovy.json.JsonSlurper;
import javax.servlet.http.HttpServletResponse;


def dataParam = params.get("data")
log.info("Param: " + dataParam)

def parser = new JsonSlurper()
def parsedData = parser.parseText(dataParam)

def newCasino = new Entity("Casino")
newCasino << parsedData
newCasino.save()

response.status = HttpServletResponse.SC_CREATED