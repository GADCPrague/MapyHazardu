

def writer = response.writer
response.contentType = "application/json"
writer.write("{test: 'data'}")

